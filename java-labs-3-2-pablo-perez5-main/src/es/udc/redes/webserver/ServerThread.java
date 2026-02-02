package es.udc.redes.webserver;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;


public class ServerThread extends Thread {

    private Socket socket;
    private boolean allowDirectoryListing;
    private static final String ROOT_DIRECTORY = "p1-files";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME;


    public ServerThread(Socket s, boolean allowDirectoryListing) {
        // Store the socket s
        this.socket = s;
        this.allowDirectoryListing = allowDirectoryListing;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            OutputStream dataOut = socket.getOutputStream()
        ){
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()){
                sendErrorResponse(out, "400 Bad Request");
                return;
            }

            logRequest(requestLine, socket.getInetAddress().toString());

            StringTokenizer tokens = new StringTokenizer(requestLine);
            String method = tokens.nextToken();
            String requestedFile = tokens.nextToken();

            if (!method.equals("GET") && !method.equals("HEAD")){
                sendErrorResponse(out, "400 Bad Request");
                return;
            }

            File file = new File(ROOT_DIRECTORY, requestedFile);
            if (file.isDirectory()){
                file = new File(file, "index.html");
                if (!file.exists() && allowDirectoryListing){
                    sendDirectoryListing(out, new File(ROOT_DIRECTORY + requestedFile));
                    return;
                } else if (!file.exists()) {
                    sendErrorResponse(out, "403 Forbidden");
                    return;
                }
            }

            if (!file.exists()){
                sendErrorResponse(out, "404 Not Found");
                return;
            }

            String contentType = Files.probeContentType(file.toPath());
            if (contentType==null){
                contentType = "application/octet-stream";
            }

            //Manejar If-Modified-Since
            String ifModifiedSince = in.readLine();
            if (ifModifiedSince!= null && ifModifiedSince.startsWith("If-Modified-Since:")){
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                ZonedDateTime fileLastModified = ZonedDateTime.parse(attr.lastModifiedTime().toString());
                ZonedDateTime requestedDate = ZonedDateTime.parse(ifModifiedSince.split(": ", 2)[1]);

                if (!fileLastModified.isAfter(requestedDate)){
                    sendResponse(out, "304 Not Modified", contentType, 0);
                    return;
                }
            }
            sendResponse(out, "200 OK", contentType, (int)file.length());
            if (method.equals("GET")){
                Files.copy(file.toPath(), dataOut);
            }

          // This code processes HTTP requests and generates 
          // HTTP responses
          // Uncomment next catch clause after implementing the logic
          //
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close the client socket
            try {
                socket.close();
            }catch (IOException e){
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    private void sendResponse(PrintWriter out, String status, String contentType, int length){
        System.out.println("HTTP/1.0 " + status);
        System.out.println("Date: " + ZonedDateTime.now().format(DATE_FORMAT));
        System.out.println("Server: MyJavaWebServer/1.0");
        System.out.println("Content-Length: " + length);
        System.out.println("Content-Type: " + contentType);
        System.out.println();
    }

    private void sendErrorResponse(PrintWriter out, String status){
        sendResponse(out, status, "text/html", 0);
    }

    private void sendDirectoryListing(PrintWriter out, File directory){
        File[] files = directory.listFiles();
        StringBuilder content = new StringBuilder("<html><body><h1>Listado de archivos</h1><ul>");
        for (File file:files){
            content.append("<li><a href=\"").append(file.getName()).append("\">").append(file.getName()).append("</a></li>");
        }
        content.append("</ul><body></html>");
        sendResponse(out, "200 OK", "text/html", content.length());
        System.out.println(content);
    }

    private void logRequest(String request, String clientIP){
        try (FileWriter logWriter = new FileWriter("server_log.txt", true)){
            logWriter.write("Request: " + request + " | Client: " + clientIP + " | Date: " + ZonedDateTime.now().format(DATE_FORMAT) + "\n");
        }catch (IOException e){
            System.err.println("Error escribiendo en el log");
        }
    }
}
