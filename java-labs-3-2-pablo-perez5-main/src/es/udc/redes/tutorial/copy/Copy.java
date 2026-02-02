package es.udc.redes.tutorial.copy;

import java.io.*;

public class Copy {

    public static void main(String[] args) {

        if(args.length != 2){
            System.err.println("Uso: java es.udc.redes.tutorial.copy.Copy <fichero origen> <fichero destino>");
            System.exit(1);
        }

        File origen = new File(args[0]);
        File destino = new File(args[1]);

        if (!origen.exists() || !origen.isFile()){
            System.err.println("Error: el archivo de origen no existe o no es un archivo válido.");
            System.exit(1);
        }

        try (InputStream in = new FileInputStream(origen);
             OutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1){
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
                System.err.println("Error al copiar el archivo:" + e.getMessage());
                System.exit(1);
        }
    }
}
