package es.udc.redes.tutorial.udp.server;

import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }

        DatagramSocket serverSocket = null;

        try {
            // Create a server socket
            int severPort = Integer.parseInt(argv[0]);

            serverSocket = new DatagramSocket(severPort);
            System.out.println("SERVER: Listening on port " + severPort);

            // Set maximum timeout to 300 secs
            serverSocket.setSoTimeout(300000);

            while (true) {
                // Prepare datagram for reception
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivecPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Receive the message
                serverSocket.receive(receivecPacket);

                //Extraer informacion del paquete r
                String receivedMessage = new String(receivecPacket.getData(), 0, receivecPacket.getLength());
                InetAddress clientAddress = receivecPacket.getAddress();
                int clientPort = receivecPacket.getPort();

                System.out.println("SERVER: Received \"" + receivedMessage + "\"from" + clientAddress + ":" + clientPort);
                
                // Prepare datagram to send response
                DatagramPacket responsePacket = new DatagramPacket(receivedMessage.getBytes(), receivedMessage.length(), clientAddress, clientPort);

                // Send response
                serverSocket.send(responsePacket);
                System.out.println("SERVER: Sending \"" + receivedMessage + "\" to " + clientAddress + ":" + clientPort);
                
            }
          
        // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        // Close the socket
            if (serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
                System.out.println("SERVER: Socket closed.");
            }
        }
    }
}
