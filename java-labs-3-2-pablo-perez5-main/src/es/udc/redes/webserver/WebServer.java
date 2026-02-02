package es.udc.redes.webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class WebServer {
    
    public static void main(String[] args) {

        if (args.length!= 2){
            System.err.println("Uso: java es.udc.redes.webserver.WebServer <puerto> <1=permitir listado de directorios, 0=no>");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        boolean allowDirectoryListing = args[1].equals("1");

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor web escuchando en el puerto " + port);

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión desde " + clientSocket.getInetAddress());
                new ServerThread(clientSocket, allowDirectoryListing).start();
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
    
}
