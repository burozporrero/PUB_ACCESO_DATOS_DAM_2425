# Java API for XML Processing (JAXP) 

JAXP es un conjunto de APIs dentro de Java Standard Edition (SE) que facilita la lectura, escritura, y manipulación de 
XML. Permite la utilización de distintos métodos y modelos de procesamiento de XML como DOM, SAX y StAX. Además, también soporta transformaciones de XSLT.

A continuación, detallo en profundidad las partes clave de JAXP, destacando las clases que pueden ser utilizadas en **Java 17** y su función:

### 1. **DOM (Document Object Model)**
DOM permite el procesamiento de XML en un modelo basado en árbol. El documento entero es cargado en memoria, y cada nodo puede ser accedido y manipulado de manera directa.

#### Clases importantes:
- **`org.w3c.dom.Document`**:
    - Representa todo el documento XML. Es el nodo raíz del árbol de documentos.

- **`org.w3c.dom.Element`**:
    - Representa un elemento XML en el documento. Se utiliza para manipular elementos específicos y sus atributos.

- **`org.w3c.dom.Node`**:
    - Es la clase base para todos los nodos del árbol XML, incluidos elementos, atributos, y texto.

- **`javax.xml.parsers.DocumentBuilder`**:
    - Se utiliza para construir una representación DOM de un documento XML.

- **`javax.xml.parsers.DocumentBuilderFactory`**:
    - Crea instancias de `DocumentBuilder`, personalizando su configuración como la validación y la implementación de namespace.

#### Ventajas:
- Permite modificaciones directas al XML.
- Ideal para documentos más pequeños donde se necesita acceder a diferentes partes del documento varias veces.

#### Ejemplo de uso: [Concesionario](DOMExample.java)

### 2. **SAX (Simple API for XML)**
SAX es un modelo de procesamiento de flujo basado en eventos. No carga todo el documento en memoria, lo que lo hace 
más eficiente para documentos grandes. 
Este modelo, también llamado modelo **"Push"**, el parser (analizador) **empuja** los datos al programa conforme los 
lee. A medida que el parser va recorriendo el documento XML, va generando eventos (como el inicio de un elemento o el fin de un elemento), y esos eventos son enviados al programa mediante callbacks (llamadas de vuelta). El programa tiene que reaccionar a estos eventos en tiempo real. El flujo está completamente controlado por el parser, y el programa responde a los eventos que van ocurriendo.

#### Clases importantes:
- **`org.xml.sax.XMLReader`**:
    - Se utiliza para leer y procesar XML de forma secuencial.
    - Lanza eventos a medida que se encuentra con etiquetas XML.

- **`org.xml.sax.helpers.DefaultHandler`**:
    - Proporciona implementaciones predeterminadas para los métodos de manejo de eventos. Puedes sobrescribir estos métodos para gestionar eventos como inicio de un elemento, fin de un elemento, y errores.

- **`org.xml.sax.Attributes`**:
    - Contiene los atributos de un elemento XML cuando se detecta su inicio. Permite acceder a ellos por nombre o índice.
      Aquí te dejo las clases más usadas de **SAX**, con sus descripciones:

- **`javax.xml.parsers.SAXParserFactory`**:
  - Se utiliza para crear nuevas instancias de **`SAXParser`**. Permite configurar el parser, como la habilitación de validación o el soporte para espacios de nombres. Es la fábrica que produce el parser SAX.
- 
- **`javax.xml.parsers.SAXParser`**:
  - Es el parser SAX real que analiza el archivo XML y lanza eventos a los manejadores de eventos SAX. Utiliza un objeto **`DefaultHandler`** para manejar los eventos que se generan durante el análisis del documento XML, como el inicio y fin de elementos, caracteres de texto, etc.

#### Ventajas:
- Bajo consumo de memoria.
- Adecuado para procesar grandes volúmenes de XML secuencialmente.
 
#### Ejemplo de uso: [Libros](src/practica/EJEMPLO5_XmlSax.java)

### 3. **StAX (Streaming API for XML)**
StAX es similar a SAX, ya que procesa el XML secuencialmente, pero proporciona un mayor control ya que es el 
programa el que controla lo que procesa en lugar de estar basado en eventos como SAX.

Este modelo, también llamado modelo **"pull"**, es el **programa el que controla** cuándo y qué partes del documento se procesan, **extrayendo** (o tirando, de ahí el término "pull") los datos del parser cuando lo necesite.
El programa avanza sobre el documento **bajo demanda**. Esto significa que el programa puede pedir al parser que le proporcione el siguiente elemento, atributo o evento, y continuar según lo necesite.

Por ejemplo, usando StAX, el programa definirá un `XMLStreamReader` o un `XMLEventReader` y realizando llamadas al 
método `next()` obtendrá el siguiente evento del documento.

#### Clases importantes:
- **`javax.xml.stream.XMLStreamReader`**:
    - Proporciona un modelo de cursor para leer XML. El cursor se mueve sobre los diferentes elementos del documento y se pueden realizar acciones basadas en su posición.

- **`javax.xml.stream.XMLStreamWriter`**:
    - Se utiliza para escribir documentos XML de manera secuencial, elemento por elemento.

- **`javax.xml.stream.XMLEventReader`**:
    - Proporciona un enfoque basado en eventos como SAX, pero con la capacidad de controlar explícitamente el flujo de los eventos.

- **`javax.xml.stream.events.XMLEvent`**:
    - Representa los eventos que son leídos desde un `XMLEventReader`. Los eventos incluyen el inicio o final de un elemento, comentarios, y texto.

#### Ventajas:
- Control más fino en la lectura/escritura.
- Mayor flexibilidad que SAX para flujos de datos complejos.

#### Más detalles para trabajar con [SAX y StAX](https://mkyong.com/java/how-to-read-xml-file-in-java-stax-parser/)

### 5. **Validación con XML Schema y DTD**
JAXP también ofrece soporte para la validación de documentos XML contra un esquema o un DTD (Document Type Definition).

#### Clases importantes:
- **`javax.xml.validation.Schema`**:
    - Representa un esquema XML (por ejemplo, un archivo XSD). Se utiliza para validar documentos XML.

- **`javax.xml.validation.SchemaFactory`**:
    - Crea instancias de `Schema` basadas en XSD o DTD.

- **`javax.xml.validation.Validator`**:
    - Aplica la validación a un documento XML utilizando un esquema. Puede validar un documento XML contra un XSD específico.

- **`javax.xml.parsers.SAXParser`** y **`javax.xml.parsers.SAXParserFactory`**:
    - Estas clases se utilizan para configurar y crear instancias de SAXParser, que pueden estar configuradas para validar un XML contra un DTD o XSD.

#### Ventajas:
- Garantiza la conformidad del XML con las reglas del esquema.
- Esencial para la integridad de los datos en sistemas que dependen de XML estructurado.

### 6. **Soporte para Namespaces**
JAXP permite la manipulación y validación de documentos que usan namespaces XML.

#### Clases importantes:
- **`javax.xml.namespace.QName`**:
    - Representa el nombre completamente calificado de un elemento o atributo en un espacio de nombres XML.

- **`javax.xml.XMLConstants`**:
    - Proporciona constantes útiles para manejar espacios de nombres y otras configuraciones relacionadas con XML.
