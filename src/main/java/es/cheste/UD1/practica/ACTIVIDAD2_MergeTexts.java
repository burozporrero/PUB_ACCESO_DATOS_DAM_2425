package es.cheste.UD1.practica;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/*
ACTIVIDAD 2: Crea un programa en Java que una el contenido de todos los archivos de texto de un directorio en un único archivo.
 */
public class ACTIVIDAD2_MergeTexts {

    /*
   Sintax: MergeTexts DirectoriOrigen FitxerDestí
   */
    public static void main(String[] args) throws Exception {

        final String dirName =  "src/main/resources/UD1/MergeTexts";
        final String mergedName =  "src/main/resources/UD1/mergeado.txt";

        File dir; // Source dir
        // Collection of files from that dir
        File[] files;

        // readed characters
        int characters;

        // Input and Output Streams
        FileReader fin = null;
        FileWriter fout = null;

        try {

            // We get the list of Files
            dir = new File(dirName);
            files = dir.listFiles();


            // Open and close output stream (in order to create the file)
            fout = new FileWriter(mergedName);
            fout.close();

            // Re-open it
            fout = new FileWriter(mergedName, true);
            // Iterate among the list
            for (int i = 0; i < files.length; i++) {
                // open input stream
                fin = new FileReader(dirName + "/" + files[i].getName());
                System.out.println("Merging " + dirName + "/" + files[i].getName());
                // and merge to the output one
                do {
                    characters = fin.read();
                    if (characters != -1)
                        fout.write(characters);
                } while (characters != -1);
                fin.close(); //close the file merged

            }
            fout.close(); //close the output file

        } catch (Exception exc) {
            // Catch all the exception (we coud improve it)
            System.out.println("Input/Output error: " + exc);
        }
    }
}
