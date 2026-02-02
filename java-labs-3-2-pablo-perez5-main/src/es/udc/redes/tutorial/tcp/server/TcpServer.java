package es.udc.redes.tutorial.tcp.server;
import java.io.IOException;
import java.net.*;

/** Multithread TCP echo server. */

public class TcpServer {

  public static void main(String argv[]) {
    if (argv.length != 1) {
      System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
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
        System.out.println("SERVER: New client connected from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        // Create a ServerThread object, with the new connection as parameter
        ServerThread clientThread = new ServerThread(clientSocket);
        // Initiate thread using the start() method
        clientThread.start();
      }
    // Uncomment next catch clause after implementing the logic
    } catch (SocketTimeoutException e) {
      System.err.println("Nothing received in 300 secs");
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
     } finally{
	    //Close the socket
        try {
          if (serverSocket != null){
            serverSocket.close();
            System.out.println("SERVER: Socket closed.");
          }
        }catch (IOException e){
          e.printStackTrace();
        }
    }
  }
}
