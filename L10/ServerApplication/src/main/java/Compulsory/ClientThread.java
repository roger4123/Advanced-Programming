package Compulsory;

import Homework.GameManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket socket;
    private GameManager gameManager;

    public ClientThread(Socket socket, GameManager gameManager) {
        this.socket = socket;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Server received: " + request); // Print request in server console
                String response = gameManager.processCommand(request);
                out.println(response);

                if ("stop".equalsIgnoreCase(request)) {
                    out.println("Server stopped");
                    GameServer.stopServer();
                    break;
                }
//                else {
//                    out.println("Server received the request: " + request);
//                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error" + e);
            }
        }
    }
}
