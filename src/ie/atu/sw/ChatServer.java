package ie.atu.sw;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 3030;
    private static final Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args)throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        System.out.println("Chat server is running on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                    clientHandlers.add(clientHandler);
                    pool.submit(clientHandler);
                } catch (IOException e) {
                    System.err.println("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
