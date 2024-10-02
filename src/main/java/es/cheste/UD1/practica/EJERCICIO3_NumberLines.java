package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * EJERCICIO3: Escribe un programa en Java que dado un  documento de texto con números de línea al principio de cada
 * línea, elimine estos números de nuevo.
 * Autor: Alejandro Castilla
 * Modificado sobre el origen por Beatriz Uroz
 */
public class EJERCICIO3_NumberLines {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static String RUTA_FICHERO_ORIGINAL = "src/main/resources/UD1/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre de su fichero con su extension: ");

        String nombre = sc.nextLine();
        File fichero = new File(RUTA_FICHERO_ORIGINAL + nombre);

        eliminarNumerosLinea(fichero, nombre);
    }

    public static void eliminarNumerosLinea(File fichero, String nombre) {

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        boolean error = Boolean.FALSE;

        try {
            bufferedReader = new BufferedReader(new FileReader(fichero));
            bufferedWriter = new BufferedWriter(new FileWriter(RUTA_FICHERO_ORIGINAL + "sinNumerosLinea.txt"));

            String line;
            Pattern pattern = Pattern.compile("^\\d+\\.\\s*");

            while ((line = bufferedReader.readLine()) != null) {

                String newLine = pattern.matcher(line).replaceFirst("");

                bufferedWriter.write(newLine);
                bufferedWriter.newLine();
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("ERROR: El fichero no ha sido encontrado: " + e.getMessage());
            error = Boolean.TRUE;
        } catch (IOException ex) {
            LOGGER.error("ERROR: Ocurrió un error de I/O: " + ex.getMessage());
            error = Boolean.TRUE;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.error("Error al cerrar el reader: {}", e.getMessage());
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    LOGGER.error("Error al cerrar el writer: {}", e.getMessage());
                }
            }
        }
        if (!error) {
            System.out.println("Archivo copiado y modificado correctamente.");
            System.out.println("El archivo sin números de línea ha sido creado.");
        } else {
            System.out.println("No ha sido posible copiar el archivo.");
        }
    }
    // Este ejercicio sería más legible si utilizaramos el try-with-resources y gestionasemos el mensaje sin
    // necesidad del booleano error.
}
