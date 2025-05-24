package Compulsory;

import Homework.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static final int PORT = 1234;
    private static boolean running = true;
    private static ServerSocket serverSocket;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static GameManager gameManager = new GameManager();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    pool.execute(new ClientThread(clientSocket, gameManager));
                } catch (IOException e) {
                    if (!running) {
                        System.out.println("Server shutting down...");
                    } else {
                        System.out.println("Server shut down...");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            stopServer();
        }
    }

    public static void stopServer() {
        running = false;
        pool.shutdown();
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        System.out.println("Server stopped.");
    }
}