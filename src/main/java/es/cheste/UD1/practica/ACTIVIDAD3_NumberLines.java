package es.cheste.UD1.practica;

import java.io.*;

/*
ACTIVIDAD3: Crea un programa en Java que copie un fichero de texto en otro, añadiendo al principio de cada línea de
text, el número de línea. Utiliza las clases Java que consideres más conveniente y que te faciliten la programación dentro de los Decorators de Java Reader y Writer.
 */
public class ACTIVIDAD3_NumberLines {
    public static void main(String[] args) throws Exception {

        final String directory = "src/main/resources/UD1/";
        final String filePath = "src/main/resources/UD1/FileCopy.txt";

// Input and Output
        BufferedReader fin;
        PrintWriter fout;

// line counter
        int num_linia;
        // readed line
        String linia;

        // Creare decorators
        File fileIn = new File(filePath);
        fin = new BufferedReader(new FileReader(fileIn));

        File fileOut = new File(directory + "ACTIVIDAD3_copia." + getExtension(filePath));
        if(fileOut.exists()) {
            System.out.println("El fichero destino ya existe. Se sobreescribirá.");
        }
        System.out.println("Nombre del fichero destino: " + fileOut.getName());

        fout = new PrintWriter(fileOut);

        num_linia = 1;
        do {
            // Read the line
            linia = fin.readLine();
            if (linia != null) {
                fout.println(num_linia + ". " + linia);
            }
            num_linia++;
        } while (linia != null); // until we can't read

        // close all
        fin.close();
        fout.close();

    }

    private static String getExtension(String name) {
        String[] trozos = name.split("\\.");
        return trozos[trozos.length - 1];
    }
}
