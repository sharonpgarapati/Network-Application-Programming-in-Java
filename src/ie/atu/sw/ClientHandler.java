package ie.atu.sw;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ClientHandler implements Callable<Void> {

    private final Socket clientSocket;
    private final Set<ClientHandler> clientHandlers;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket clientSocket, Set<ClientHandler> clientHandlers) {
        this.clientSocket = clientSocket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public Void call() {
        try {
              in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("\\q")) {
                    System.out.println("Client disconnected: " + clientSocket.getInetAddress());
                    break;
                }
                System.out.println("Message from client: " + message);
                broadcastMessage("Client " + clientSocket.getInetAddress() + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }

    private void broadcastMessage(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler handler : clientHandlers) {
                if (handler != this) {
                    handler.out.println(message);
                }
            }
        }
    }

    private void closeConnection() {
        try {
            clientHandlers.remove(this);
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
