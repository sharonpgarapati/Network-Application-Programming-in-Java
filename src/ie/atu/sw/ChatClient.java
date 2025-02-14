package ie.atu.sw;

import java.io.*;
import java.net.*;

public class ChatClient {

    private static final String SERVER_ADDRESS = "127.0.0.1"; // Localhost
    private static final int SERVER_PORT = 3030;

    public static void main(String[] args)throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server!");


            // Separate thread for reading server messages
            new Thread(new ServerMessageReader(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
                if (input.equalsIgnoreCase("\\q")) {
                    System.out.println("You have left the chat.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static class ServerMessageReader implements Runnable {
        private final Socket socket;

        public ServerMessageReader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println("Error reading messages: " + e.getMessage());
            }
        }
    }
}
