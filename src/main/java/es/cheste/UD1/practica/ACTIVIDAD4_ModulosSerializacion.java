package es.cheste.UD1.practica;

import java.io.*;

/*
Partiendo de la misma base de la clase Modulos del ejercicio anterior, vamos a crear una clase Modulo, para almacenar un único módulo. Este tipo de clases se denominan POJO’s (Plain Old Java Objects),
Una vez creada la clase Modulo, se escribe un programa para guardar en un archivo los objetos serializados directamente. A continuación, se escribe la función complementaria para leer todos los objetos almacenados en ese archivo.
 */
public class ACTIVIDAD4_ModulosSerializacion {

    // Arrays with source data
    private static final String[] moduls = {"Accés a Dades", "Programació de serveis i processos", "Desenvolupament d'interfícies", "Programació Multimédia i dispositiud mòbils", "Sistemes de Gestió Empresarial", "Empresa i iniciativa emprenedora"};
    private static final int[] hores = {6, 3, 6, 5, 5, 3};
    private static final double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

    private static void escriuObjecte(String nom) throws IOException {
        //destination file
        ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream(nom));
        Modulo m; // Single object

        // loop along the arrays
        for (int i = 0; i < moduls.length; i++) {
            m = new Modulo(moduls[i], hores[i], notes[i]);
            f.writeObject(m);
        }
        // close the file
        f.close();
    }

    private static void lligObjecte(String nom) throws IOException, ClassNotFoundException {
        // input file
        ObjectInputStream f = new ObjectInputStream(new FileInputStream(nom));
        Modulo m;
        // we don't know how many objects exists in the file.
        try {
            while (true) { // forever

                m = (Modulo) f.readObject();

                // show the module
                System.out.println("Modul: " + m.getNom());
                System.out.println("Hores: " + m.getHores());
                System.out.println("Nota: " + m.getNota());
                System.out.println();
            }
        } catch (EOFException ex) {
            f.close();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        escriuObjecte("src/main/resources/UD1/ModulosDAM/modulos_serial.dat");
        lligObjecte("src/main/resources/UD1/ModulosDAM/modulos_serial.dat");
    }
}
