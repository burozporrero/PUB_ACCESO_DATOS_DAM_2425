package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/*
 * Crea un programa en Java que copie de bloques de 12 bytes en bloques de 12 bytes un archivo a otro archivo
 * Autor: Ignacio Peña
 * Modificado sobre el origen por Beatriz Uroz
 */
public class EJERCICIO1_FileCopy {

    private static final String RUTA_FICHERO = "src/main/resources/UD1/FileCopy.jpg";
    private static final int NUMERO_BYTES = 32;

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {

        File FICHERO = new File(RUTA_FICHERO);
        Boolean ficheroCopiado = copiaFichero(FICHERO);

        if (ficheroCopiado) {
            System.out.println("✔\uFE0FEl fichero se copio correctamente✔\uFE0F");
        } else {
            System.out.println("❌Se produjo un error durante el proceso de copiado❌");
        }

    }

    public static File crearFicheroCopia(File ficheroOriginal) {

        String[] nombreYExtensionFichero = ficheroOriginal.getName().split("\\.");

        return new File(ficheroOriginal.getParent(), nombreYExtensionFichero[0] + "Copia" + nombreYExtensionFichero[1]);
    }

    public static Boolean copiaFichero(File ficheroOriginal) {

        File ficheroCopia = crearFicheroCopia(ficheroOriginal);

        //Hago un try-with-resources con el FileOutputStream y FileInputStream
        try (FileOutputStream fOS = new FileOutputStream(ficheroCopia); FileInputStream fIS = new FileInputStream(ficheroOriginal)) {

            byte[] bytes = new byte[NUMERO_BYTES];
            int bytesLeidos;

            //Guardo el grupo de bytes para a continuación escribirlos
            while ((bytesLeidos = fIS.read(bytes)) != -1) {
                fOS.write(bytes, 0, bytesLeidos);
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("❌Error al encontrar el fichero❌: " + e.getMessage());
            return Boolean.FALSE;
        } catch (IOException e) {
            LOGGER.error("❌Error con el fichero❌: " + e.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
