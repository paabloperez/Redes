package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.MonoThreadTcpServer <port>");
            System.exit(-1);
        }

        ServerSocket serverSocket = null;

        try {
            int serverPort = Integer.parseInt(argv[0]);
            // Create a server socket
            serverSocket = new ServerSocket(serverPort);
            System.out.println("SERVER: Listening on port " + serverPort);
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);
            
            while (true) {
                // Wait for connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("SERVER: Connection established with " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                // Set the input channel
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // Set the output channel
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                // Receive the client message
                String receivedMessage = input.readLine();
                System.out.println("SERVER: Received \"" + receivedMessage + "\"from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                // Send response to the client
                output.println(receivedMessage);
                System.out.println("SERVER: Sending \"" + receivedMessage + "\" to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                // Close the streams
                input.close();
                output.close();
                clientSocket.close();
            }
        // Uncomment next catch clause after implementing the logic            
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
	        try {
                if (serverSocket!= null){
                    serverSocket.close();
                    System.out.println("SERVER: Socket closed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
