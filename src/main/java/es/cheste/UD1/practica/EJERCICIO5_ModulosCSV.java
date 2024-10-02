package es.cheste.UD1.practica;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EJERCICIO5_ModulosCSV {

    private static final String[] moduls = {"Accés a Dades", "Programació de serveis i processos", "Desenvolupament d'interfícies", "Programació Multimédia i dispositiud mòbils", "Sistemes de Gestió Empresarial", "Empresa i iniciativa emprenedora"};
    private static final int[] hores = {6, 3, 6, 5, 5, 3};
    private static final double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

    private static List<Modulo> recuperaObjetos() throws IOException {

        List<Modulo> modulosList = new ArrayList<>();
        Modulo m; // Single object

        // loop along the arrays
        for (int i = 0; i < moduls.length; i++) {
            m = new Modulo(moduls[i], hores[i], notes[i]);
            modulosList.add(m);
        }
        return modulosList;
    }

    private static void showCSV(String filename, boolean hasHeader, String separator) throws IOException {

        // Read one file by one Line
        List<String> records = Files.readAllLines(Paths.get(filename));

        //print headers, only if exists
        if (hasHeader) {
            String header = records.get(0);
            records.remove(0);
            String[] headers = header.split(separator);
            int i = 0;
            for (String h : headers) {
                System.out.printf("%15s", h.toUpperCase());
                i += 15;
            }
            System.out.println("");
        }

        // print values
        for (String record : records) {
            String[] values = record.split(separator);
            for (String value : values) {
                System.out.printf("%20s",value);
            }
            System.out.println("");
        }
    }

    private static void addLineToCSV(String filename) throws IOException {

        FileWriter output = new FileWriter(filename, false);
        output.write("Modulo;Horas;Nota\n");
        List<Modulo> modulos = recuperaObjetos();
        for (Modulo m : modulos) {
            output.write(m.toString() + "\n");
        }
        output.close();
    }

    public static void main(String[] args) throws IOException {

        String filename = "src/main/resources/UD1/ModulosDAM/modulos.csv";
        addLineToCSV(filename);
        showCSV(filename, true, ";");
    }
}
