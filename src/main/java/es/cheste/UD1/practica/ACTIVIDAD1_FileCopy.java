package es.cheste.UD1.practica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
ACTIVIDAD 1: Crea un programa en Java que copie byte a byte de un archivo a otro archivo
 */
class ACTIVIDAD1_FileCopy {
    /*
     Class to test FileInputStream and FileOutputStream. Copy byte to byte of files
     Sintaxi: FileCopy sourceFile destinationFile.
    */
    public static void main(String[] args) throws Exception {

        final String filePath = "src/main/resources/UD1/FileCopy.jpg";

        // Byte readed from source
        int bytes;
        // Bytes (effectively) writen to dest
        long bytesCopied = 0;
        // Streams
        FileInputStream fis = null;
        FileOutputStream fos = null;

        // To provide information about source
        File fIn;
        File fOut;

        try {
            // show source size
            fIn = new File(filePath);
            System.out.println("Total: " + fIn.length() + " bytes");

            fOut = new File(fIn.getAbsolutePath() + "copia." + getExtension(filePath));
            if(fOut.exists()) {
                System.out.println("El fichero destino ya existe. Se sobreescribirá.");
            }
            System.out.println("Nombre del fichero destino: " + fOut.getName());

            // Create streams
            fis = new FileInputStream(fIn);
            fos = new FileOutputStream(fOut);

            do {
                // read one byte from source
                bytes = fis.read();
                // write in destination
                if (bytes != -1)
                    fos.write(bytes);
                // Update number of bytes
                bytesCopied++;

                // Show progress
                System.out.print("\rCopiats " + (bytesCopied - 1) + " bytes...");
            } while (bytes != -1);
            System.out.println("Done it!");

        } catch (IOException exc) {
            System.out.println("Error d'entrada i eixida: " + exc);
        } finally {
            // At the end, we have to close the files, either an error exists or not.
            try {
                if (fis != null) fis.close();
            } catch (IOException exc) {
                System.out.println("Error en tancar el fitxer d'origen. ");
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException exc) {
                System.out.println("Error en tancar el fitxer destí.");
            }
        }
    }

    private static String getExtension(String name) {
        String[] trozos = name.split("\\.");
        return trozos[trozos.length - 1];
    }
}
