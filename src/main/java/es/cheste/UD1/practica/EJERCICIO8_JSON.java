package es.cheste.UD1.practica;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

/*
Descarga el archivo llamado SW.json.
En él se recupera información sobre los personajes de la famosa saga.
Escribe un programa para obtener:
Personajes que no condujeron ningún vehículo.
Lista de personajes, ordenados por número de películas en las que aparecen.
Crea un archivo XML como este, con una breve información resumida:
* Autor: Hugo Almodovar y Matti Aloha (clase Personaje)
* Modificado sobre el origen por Beatriz Uroz
*/
public class EJERCICIO8_JSON {

    private final static String RUTA_JSON = "src/main/resources/UD1/json/SW.json";
    private final static String RUTA_XML = "src/main/resources/UD1/SW.xml";
    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {

        PersonajeWrapper wrapper = obtenerPersonajesJSON();

        List<Personaje> personajes = wrapper.getPersonajes();

        if (personajes != null) {
            System.out.println(personajesNoConducido(personajes));
            System.out.println(ordenarPersonajesPeliculas(personajes));
            System.out.println(insertarDatosXML(wrapper) ? "Se ha completado el xml" : "Hubo un error inesperado");
        } else System.err.println("Ocurrio un error inesperado");
    }

    public static PersonajeWrapper obtenerPersonajesJSON() {

        PersonajeWrapper wrapper = new PersonajeWrapper();

        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(RUTA_JSON);

            wrapper = gson.fromJson(fileReader, PersonajeWrapper.class);
            fileReader.close();

        } catch (Exception e) {
            LOGGER.error("Ha ocurrido un error en la lectura {}", e.getMessage());
        }
        return wrapper;
    }

    public static String personajesNoConducido(List<Personaje> personajes) {

        StringBuilder sb = new StringBuilder("--Personajes que no han conducido--\n");

        for (Personaje personaje : personajes) {
            if (personaje.noHaConducidoVehiculo()) {
                sb.append(personaje.getName()).append("\n");
            }
        }

        return String.valueOf(sb);
    }

    public static String ordenarPersonajesPeliculas(List<Personaje> personajes) {
        StringBuilder sb = new StringBuilder("--Listado de los persoanjes que aparecen de más a menos--\n");

        Collections.sort(personajes);

        for (Personaje personaje : personajes) {

            sb.append("Nombre: ").append(personaje.getName()).append(" - Peliculas: ").append(personaje.cantidadPeliculas());
            sb.append("\n");
        }

        return String.valueOf(sb);
    }

    private static Document crearXML() {

        Document doc = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.newDocument();

        } catch (ParserConfigurationException e) {
            LOGGER.error("Hubo un erro a crear el DocumentBuilder {}", e.getMessage());
            return doc;
        }
        return doc;
    }

    public static Boolean insertarDatosXML(PersonajeWrapper wrapper) {

        Document doc = crearXML();

        if (doc != null) {
            Element eRaiz = doc.createElement("personajes");
            doc.appendChild(eRaiz);

            for (Personaje personaje : wrapper.getPersonajes()) {
                Element ePersonaje = doc.createElement("personaje");
                String[] atributos = personaje.devolverAtributos();
                agregarAtributos(atributos, ePersonaje, doc);

                String[] elementos = personaje.devolverElementos();
                agregarElementosPersonaje(elementos, ePersonaje, doc);
                eRaiz.appendChild(ePersonaje);
            }
            return finalizarDocumento(doc);

        }
        return Boolean.FALSE;

    }

    private static void agregarAtributos(String[] atributos, Element ePersonaje, Document doc) {

        for (int i = 0; i < atributos.length; i++) {

            Attr attr = doc.createAttribute(i == 0 ? "peliculas" : "vehiculos");
            attr.setValue(atributos[i]);
            ePersonaje.setAttributeNode(attr);
        }
    }

    private static void agregarElementosPersonaje(String[] elementos, Element ePersonaje, Document doc) {

        for (int i = 0; i < elementos.length; i++) {

            Element eElementoPersonaje = doc.createElement(i == 0 ? "nombre" : i == 1 ? "peso" : "url");

            eElementoPersonaje.appendChild(doc.createTextNode(elementos[i]));
            ePersonaje.appendChild(eElementoPersonaje);
        }

    }

    private static Boolean finalizarDocumento(Document doc){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("omit-xml-declaration", "no");
            DOMSource source = new DOMSource(doc);
            StreamResult resultado = new StreamResult(new File(RUTA_XML));

            transformer.transform(source, resultado);

        } catch (TransformerConfigurationException e) {
            LOGGER.error("Hubo un error en la instanciacion del transformer {}", e.getMessage());
            return Boolean.FALSE;

        } catch (TransformerException ex) {
            LOGGER.error("Hubo un error en la transformacion {}", ex.getMessage());
            return Boolean.FALSE;

        }
        return Boolean.TRUE;
    }
}
