package es.cheste.UD1.practica;

import java.io.*;

/*
 * EJERCICIO4: Crea un programa en Java que dados 3 arrays paralelos, que almacenan la información de los diferentes módulos de un alumno, primero  escriba la información en un fichero y luego la lea y la imprima por pantalla de forma ordenada.
El ejemplo de arrays está en aules  Resources.
 */
public class EJERCICIO4_Modulos {

    // several arrays with modules data
    private static final String[] moduls = {"Accés a Dades", "Programació de serveis i processos", "Desenvolupament " +
            "d'interfícies",
            "Programació Multimèdia i dispositiud mòbils", "Sistemes de Gestió Empresarial", "Empresa i iniciativa emprenedora"};
    private static final int[] hores = {6, 3, 6, 5, 5, 3};
    private static final double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

    private static void readFiLe(String name) throws IOException {
        // Per llegin el fitxer binari, creem un DataInputStream
        // a partir del FileInputStream creat a partir del nom
        DataInputStream f = new DataInputStream(new FileInputStream(name));

        // Mentre el DataInputStream tinga dades disponibles
        while (f.available() > 0) {
            // Llegirem del fitxer cada dada, amb l'ordre corresponent
            // en funció del tipus
            // (per tant, hem de saber l'ordre en què guardem!)
            System.out.println("Mòdul: " + f.readUTF());
            System.out.println("Hores: " + f.readInt());
            System.out.println("Notes: " + f.readDouble());
            System.out.println();
        }
        f.close();
    }

    private static void writeFile(String nom) throws IOException {
        // Per escriure el fitxer, fem ús de DataOutputStream
        DataOutputStream f = new DataOutputStream(new FileOutputStream(nom));

        // Recorrerem qualsevol dels vectors (tots haurien de tindre)
        // la mateixa longitud
        for (int i = 0; i < moduls.length; i++) {
            // I per a cada posició, escriurem en funció del tipus de dada
            f.writeUTF(moduls[i]);
            f.writeInt(hores[i]);
            f.writeDouble(notes[i]);

        }
        f.close();
    }

    public static void main(String[] args) throws IOException {

        writeFile("src/main/resources/UD1/ModulosDAM/modulos.dat");
        readFiLe("src/main/resources/UD1/ModulosDAM/modulos.dat");

    }
}
