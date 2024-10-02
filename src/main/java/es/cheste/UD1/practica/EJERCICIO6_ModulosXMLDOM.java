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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * EJERCICO2: Crea un programa en Java que escriba el contenido de los arrays de módulos en un archivo XML. Utiliza
 * la gestión del DOM para ello. El contenido del fichero debe ser  como se muestra a continuación. Una vez tengamos
 * generado nuestro “modulos.xml” lo leeremos e imprimiremos su contenido por pantalla formateado a gusto del alumno.
 * Autor: Ángel Marco
 * Modificado sobre el origen por Beatriz Uroz
 */
public class EJERCICIO6_ModulosXMLDOM {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static String RUTA_FICHERO = "src/main/resources/UD1/";

    private static final String[] moduls = {"Accés a Dades", "Programació de serveis i processos", "Desenvolupament d'interfícies", "Programació Multimédia i dispositiud mòbils", "Sistemes de Gestió Empresarial", "Empresa i iniciativa emprenedora"};
    private static final int[] hores = {6, 3, 6, 5, 5, 3};
    private static final double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

    public static void main(String[] args) {

        List<Modulo> modulos = new ArrayList<>();

        for (int i = 0; i < moduls.length; i++) {
            modulos.add(new Modulo(moduls[i], hores[i], notes[i]));
        }

        File fichero_xml = new File(RUTA_FICHERO, "Modulos_Angel.xml");
        crearXML(fichero_xml, modulos);
        leerXML(fichero_xml);
    }

    public static void crearXML(File ficheroXML, List<Modulo> modulos) {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.newDocument();
            Element curso = documento.createElement("Curso");
            documento.appendChild(curso);

            for (Modulo moduloActual : modulos) {
                Element modulo = documento.createElement("Modulo");
                curso.appendChild(modulo);

                modulo.appendChild(crearElemento(documento, "Nombre", moduloActual.getNom()));
                modulo.appendChild(crearElemento(documento, "Horas", String.valueOf(moduloActual.getHores())));
                modulo.appendChild(crearElemento(documento, "Notas", String.valueOf(moduloActual.getNota())));
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("omit-xml-declaration", "no");
            DOMSource origen = new DOMSource(documento);

            StreamResult destino = new StreamResult(ficheroXML);
            transformer.transform(origen, destino);

        } catch (ParserConfigurationException | TransformerException e) {
            LOGGER.error(e);
            System.out.println("Error al crear el fichero XML");
        }
        System.out.println("Fichero XML creado correctamente");
    }

    private static Element crearElemento(Document documento, String nombreElemento, String contenido) {
        Element elemento = documento.createElement(nombreElemento);
        elemento.appendChild(documento.createTextNode(contenido));
        return elemento;
    }

    public static void leerXML(File ficheroXML) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(ficheroXML);
            NodeList nl = documento.getElementsByTagName("Modulo");

            for (int i = 0; i < nl.getLength(); i++) {
                Element modulo = (Element) nl.item(i);
                System.out.println(generarTextoXML(modulo));
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error(e);
            System.out.println("Error al leer el fichero XML");
        }
    }

    public static String generarTextoXML(Element elemento) {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ");
        sb.append(elemento.getElementsByTagName("Nombre").item(0).getTextContent()).append("\n");
        sb.append("Horas: ");
        sb.append(elemento.getElementsByTagName("Horas").item(0).getTextContent()).append("\n");
        sb.append("Nota: ");
        sb.append(elemento.getElementsByTagName("Notas").item(0).getTextContent()).append("\n");
        sb.append("--------------------");

        return sb.toString();
    }
}
