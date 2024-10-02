package es.cheste.UD1.practica;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EJEMPLO4_XmlDomWrite {

    public Document inicializarDocumento() throws ParserConfigurationException {
        Document documento;
        // Creamos los objectos de creacion de Documentos XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor = docFactory.newDocumentBuilder();

        documento = constructor.newDocument();

        return documento;
    }

    public String convertirString(Document documento) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Creamos el escritor a cadena de texto
        StringWriter writer = new StringWriter();
        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual se almacenara en el objecto writer
        StreamResult result = new StreamResult(writer);
        // Efectuamos la transformacion a lo que indica el objecto resultado, writer apuntara a el resultado
        transformer.transform(source, result);
        // Convertimos el buffer de writer en cadena de texto
        String output = writer.getBuffer().toString();

        return output;
    }

    public void escribirArchivo(Document documento, String fileName) throws TransformerConfigurationException, TransformerException {
        // Creamos el objecto transformador
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Indicamos que queremos que idente el XML con 2 espacios
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Archivo donde almacenaremos el XML
        File archivo = new File(fileName);

        // Fuente de datos, en este caso el documento XML
        DOMSource source = new DOMSource(documento);
        // Resultado, el cual almacena en el archivo indicado
        StreamResult result = new StreamResult(archivo);
        // Transformamos de la fuente DOM a el resultado, lo que almacena todo en el archivo
        transformer.transform(source, result);
    }

    public Document crearDocumento() throws ParserConfigurationException {
        Document documento = this.inicializarDocumento();

        // Creamos el elemento principal
        Element entrada = documento.createElement("coches");
        // Hacemos el elemento entrada descender directo del nodo XML principal
        documento.appendChild(entrada);

        // Creamos un elemento coche
        Element coche = documento.createElement("coche");
        entrada.appendChild(coche);

        // Creamos el Elemento de matricula
        Element matricula = documento.createElement("matricula");
        // Establecemos el contenido de la matricula
        matricula.setTextContent("1111AAA");
        // Indicamos que el elemento titulo desciende de entrada
        coche.appendChild(matricula);

        //Creamos mas elementos
        Element marca = documento.createElement("marca");
        marca.setTextContent("AUDI");
        coche.appendChild(marca);

        //Elemento fecha
        Element adquisicion = documento.createElement("adquisicion");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendario = Calendar.getInstance();
        Date date = new Date(calendario.getTimeInMillis());

        adquisicion.setTextContent(formato.format(date));
        coche.appendChild(adquisicion);

        return documento;
    }
}
