package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/*
 * EJERCICO2: Crea un programa en Java que una el contenido de todos los archivos de texto de un directorio en un único
 * archivo. Si no lo has hecho ya, mejora el código del ejercicio anterior para que la función principal de mergeo se realice como método independiente.
 * Autor: Hugo Almodóvar
 * Modificado sobre el origen por Beatriz Uroz
 */
public class EJERCICIO2_MergeTexts {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final String RUTA_DIRECTORIO = "src/main/resources/UD1/MergeTexts";
    private static final File FICHERO_UNION = new File("src/main/resources/UD1/Ficheros_unidos.txt");

    public static void main(String[] args) {
        File directorio = new File(RUTA_DIRECTORIO);
        System.out.println(unirFicheros(directorio) ? "Se han unido los ficheros correctamente" : "Hubo un error al unir los datos");
    }

    private static boolean unirFicheros(File directorio) {

        if (!directorio.isDirectory()) {
            LOGGER.error("El directorio no existe");
            return Boolean.FALSE;
        }
        File[] ficheros = directorio.listFiles();
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        if (ficheros == null) {
            LOGGER.error("No se han encontrado ficheros en el directorio");
            return Boolean.FALSE;
        }

        try {
            for (File file : ficheros) {

                fileReader = new FileReader(file);
                fileWriter = new FileWriter(FICHERO_UNION);
                int aux;

                while ((aux = fileReader.read()) != -1) {
                    fileWriter.write(aux);
                }
                fileReader.close();
                fileWriter.close();

            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Ha ocurrido un error, no se ha encontrado el fichero: {}", e.getMessage());
            return Boolean.FALSE;
        } catch (IOException ex) {
            LOGGER.error("Ha ocurrido un error inesperado en lso ficheros: {}", ex.getMessage());
            return Boolean.FALSE;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    LOGGER.error("Error al cerrar el fileReader: {}", e.getMessage());
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    LOGGER.error("Error al cerrar el fileWriter: {}", e.getMessage());
                }
            }
        }
        return Boolean.TRUE;
    }
}
