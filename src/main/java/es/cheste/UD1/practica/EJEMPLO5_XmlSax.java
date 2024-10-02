package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/*
Ejemplo extraido de https://github.com/DiscoDurodeRoer/ejemplo-sax-java
Modificado por Beatriz Uroz
 */

public class EJEMPLO5_XmlSax {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String RUTA_FICHERO = "src/main/resources/UD1/xml/libros.xml";

    public static void main(String[] args) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            LibroHandler handler = new LibroHandler();
            parser.parse(RUTA_FICHERO, handler);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOGGER.error("Error al parsear el fichero XML", ex);
        }
    }
}
