package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class EJEMPLO4_XmlDom {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String RUTA_FICHERO_LECTURA = "src/main/resources/UD1/xml/coches.xml";
    private static final String RUTA_FICHERO_ESCRITURA = "src/main/resources/UD1/xml/ejemploEscribe.xml";

    public static void main(String[] args) {
        leerXML();
        escribirXML();
    }

    public static void leerXML() {
        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Obtengo el documento, a partir del XML
            Document documento = builder.parse(new File(RUTA_FICHERO_LECTURA));

            // Cojo todas las etiquetas coche del documento
            NodeList listaCoches = documento.getElementsByTagName("coche");

            // Recorro las etiquetas
            for (int i = 0; i < listaCoches.getLength(); i++) {
                // Cojo el nodo actual
                Node nodo = listaCoches.item(i);

                // Compruebo si el nodo es un elemento
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    // Lo transformo a Element
                    Element e = (Element) nodo;
                    // Obtengo sus hijos
                    NodeList hijos = e.getChildNodes();
                    // Recorro sus hijos
                    for (int j = 0; j < hijos.getLength(); j++) {
                        // Obtengo al hijo actual
                        Node hijo = hijos.item(j);
                        // Compruebo si es un nodo
                        if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                            // Muestro el contenido
                            System.out.println("Propiedad: " + hijo.getNodeName()
                                    + ", Valor: " + hijo.getTextContent());
                        }
                    }
                    System.out.println("");
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOGGER.error("Ha ocurrido un error parseando el documento", ex.getMessage());
        }
    }

    public static void escribirXML() {

        try {
            EJEMPLO4_XmlDomWrite ejemploXML = new EJEMPLO4_XmlDomWrite();
            Document documento = ejemploXML.crearDocumento();

            System.out.println(ejemploXML.convertirString(documento));

            ejemploXML.escribirArchivo(documento, RUTA_FICHERO_ESCRITURA);

        } catch (ParserConfigurationException ex) {
            LOGGER.error("Error de configuracion");
        } catch (TransformerException ex) {
            LOGGER.error("Error de transformacion XML a String");
        }
    }
}
