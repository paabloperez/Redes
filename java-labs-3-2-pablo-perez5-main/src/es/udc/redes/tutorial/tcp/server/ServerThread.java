package es.udc.redes.tutorial.tcp.server;
import java.net.*;
import java.io.*;

/** Thread that processes an echo server connection. */

public class ServerThread extends Thread {

  private Socket clientSocket;

  public ServerThread(Socket s) {
    // Store the socket s (socket del cliente)
    this.clientSocket = s;
  }

  public void run() {
    try {
      // Set the input channel
      BufferedReader input = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
      // Set the output channel
      PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
      // Receive the message from the client
      String receivedMessage = input.readLine();
      System.out.println("THREAD[" + Thread.currentThread().getId() + "]: Received \"" + receivedMessage + "\" from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
      // Sent the echo message to the client
      output.println(receivedMessage);
      System.out.println("THREAD[" + Thread.currentThread().getId() + "]: Sending \"" + receivedMessage + "\" to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
      // Close the streams
      input.close();
      output.close();
      clientSocket.close();
    // Uncomment next catch clause after implementing the logic
    } catch (SocketTimeoutException e) {
      System.err.println("Nothing received in 300 secs");
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      } finally {
	// Close the socket
      try {
        if (clientSocket!=null){
          clientSocket.close();
        }
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
