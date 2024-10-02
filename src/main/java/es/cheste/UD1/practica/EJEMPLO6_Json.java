package es.cheste.UD1.practica;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class EJEMPLO6_Json {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String RUTA_FICHERO_READ = "src/main/resources/UD1/json/concesionario.json";
    private static final String RUTA_FICHERO_WRITE = "src/main/resources/UD1/json/concesionario_generado_gson.json";

    public static void main(String[] args) throws Exception {
        leerJson();
        escribirJson();
    }

    private static void leerJson() {
        try {
            // Crear una instancia de Gson
            Gson gson = new Gson();
            // Leer el JSON desde un archivo
            FileReader reader = new FileReader(RUTA_FICHERO_READ);
            // Deserializar el JSON a ConcesionarioWrapper
            ConcesionarioWrapper wrapper = gson.fromJson(reader, ConcesionarioWrapper.class);

            // Cerrar el lector
            reader.close();

            // Obtener la lista de coches
            List<Coche> coches = wrapper.getConcesionario().getCoches();

            // Recorrer y mostrar detalles de cada coche
                for (Coche coche : coches) {
                System.out.println(coche);
            }

        } catch (Exception e) {
            LOGGER.error("Error al leer el JSON", e);
        }
    }

    private static void escribirJson() {
        try {
            // Crear instancias de Coche
            Coche coche1 = new Coche("1111AAA", "AUDI", 30000);
            Coche coche2 = new Coche("2222BBB", "SEAT", 10000);
            Coche coche3 = new Coche("3333CCC", "BMW", 20000);
            Coche coche4 = new Coche("4444DDD", "TOYOTA", 10000);

            // Crear una lista de coches
            List<Coche> listaCoches = Arrays.asList(coche1, coche2, coche3, coche4);

            // Crear una instancia de Concesionario
            Concesionario concesionario = new Concesionario(listaCoches);

            // Crear una instancia de ConcesionarioWrapper
            ConcesionarioWrapper wrapper = new ConcesionarioWrapper(concesionario);

            // Crear una instancia de Gson con formato de impresión
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Convertir el objeto wrapper a una cadena JSON con formato
            String jsonString = gson.toJson(wrapper);

            // Imprimir el JSON resultante
            System.out.println(jsonString);

            // Opcional: Guardar el JSON en un archivo
            FileWriter writer = new FileWriter(RUTA_FICHERO_WRITE);
            gson.toJson(wrapper, writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
