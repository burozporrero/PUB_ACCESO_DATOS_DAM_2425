package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class EJEMPLO2_PropertiesMysql {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) throws Exception {
        String filename = "src/main/resources/UD1/properties/my.cnf";
        loadAndShowProperties(filename);
        writeProperties();
    }

    private static void loadAndShowProperties(String filename) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File(filename)));

            // Mostramos todas
            System.out.println("Whole set: " + properties);
            System.out.println("\n");

            // Mostramos una a una
            properties.list(System.out);

            //Recorremos una a una y las mostramos
            Set<Object> keys = properties.keySet();
            System.out.println("\nMy listing: ");
            for (Object key : keys) {
                System.out.println(key + " - " + properties.getProperty((String) key));
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
        } catch (IOException e) {
            LOGGER.error("Error reading file", e);
        }
    }

    /**
     * Create a properties object, populated with samples and store into a
     * text file and a XML file
     *
     * @throws IOException
     */
    private static void writeProperties() throws IOException {
        Properties props = new Properties();

        props.put("Color", "Green");
        props.put("Range", "123");
        props.put("Visible", "false");
        props.put("Size", "Big");
        props.put("Status", "functional");
        props.put("Value", "345.24");

        props.store(new FileWriter(new File("src/main/resources/UD1/properties/wp.properties")), "Sample props " +
                "file");

        props.storeToXML(new FileOutputStream(new File("src/main/resources/UD1/properties/wp.xml")),"Sample " +
                "XML " +
                "Props");
    }
}
