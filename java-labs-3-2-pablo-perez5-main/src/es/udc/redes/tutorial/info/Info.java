package es.udc.redes.tutorial.info;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Info {

    public static void main(String[] args) {

        if (args.length != 1){
            System.err.println("Uso: java es.udc.redes.tutorial.info.Info <ruta relativa>");
            System.exit(1);
        }

        File archivo = new File(args[0]);

        if (!archivo.exists()){
            System.err.println("Error: el archivo no existe.");
            System.exit(1);
        }

        System.out.println("Nombre: " + archivo.getName());
        System.out.println("Extensión: " + obtenerExtension(archivo));
        System.out.println("Tipo " + determinarTipoArchivo(archivo));
        System.out.println("Tamaño: " + archivo.length() + "bytes");
        System.out.println("Ultima modificacion: " + obtenerFechaModificacion(archivo));
        System.out.println("Ruta absoluta: " + archivo.getAbsolutePath());
    }

    private static String obtenerExtension(File archivo){
        String nombre = archivo.getName();
        int index = nombre.lastIndexOf(".");
        return (index == -1) ? "Sin extensión" : nombre.substring(index + 1);
    }

    private static String determinarTipoArchivo(File archivo){
        if (archivo.isDirectory()){
            return "directory";
        }

        String extension = obtenerExtension(archivo).toLowerCase();
        if (extension.matches("jpg|jpeg|png|gif|bmp")) {
            return "image";
        } else if (extension.matches("txt|csv|md|json|xml|html|java|log")) {
            return "text";
        } else {
            return "unknown";
        }
    }

    private static String obtenerFechaModificacion(File archivo){
        long ultimaModificacion = archivo.lastModified();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(ultimaModificacion));
    }
    
}
