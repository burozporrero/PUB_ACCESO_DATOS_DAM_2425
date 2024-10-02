# JSON

## **¿Qué es un archivo JSON?**

**JSON** (JavaScript Object Notation) es un formato ligero de intercambio de datos que es fácil de leer y escribir para los humanos, y fácil de parsear y generar para las máquinas. Aunque está basado en una sintaxis de JavaScript, es independiente del lenguaje y se utiliza ampliamente en diversas tecnologías y plataformas.

### **Características de JSON:**
- **Estructurado:** Organiza los datos en pares clave-valor.
- **Ligero:** Consume menos ancho de banda en comparación con otros formatos como XML.
- **Legible:** Fácil de leer y entender por los humanos.
- **Compatible:** Soporta estructuras de datos complejas como objetos y matrices.

### **Ejemplo de JSON:**
```json
{
  "nombre": "Juan Pérez",
  "edad": 30,
  "correo": "juan.perez@example.com",
  "direcciones": [
    {
      "tipo": "casa",
      "direccion": "Calle Falsa 123"
    },
    {
      "tipo": "trabajo",
      "direccion": "Avenida Siempre Viva 456"
    }
  ]
}
```

---

## **¿Para qué se utiliza normalmente JSON?**

1. **Intercambio de Datos entre Cliente y Servidor:**
    - En aplicaciones web y móviles, JSON es comúnmente utilizado para enviar y recibir datos entre el front-end y el back-end.

2. **Configuración de Aplicaciones:**
    - Muchos frameworks y herramientas usan archivos JSON para almacenar configuraciones (por ejemplo, `package.json` en Node.js).

3. **Almacenamiento de Datos:**
    - Bases de datos NoSQL como MongoDB almacenan datos en formato JSON o similar.

4. **APIs Públicas:**
    - Muchas APIs RESTful utilizan JSON para estructurar las respuestas y solicitudes.

---

## **Clases y Bibliotecas en Java para Manejar JSON**

En Java, existen varias bibliotecas populares que facilitan la lectura y escritura de JSON. Las más utilizadas son:

1. **Jackson**
2. **Gson (de Google)**
3. **org.json**

A continuación, se detallan las dos más populares: **Jackson** y **Gson**.

### **1. Jackson**

**Jackson** es una de las bibliotecas más potentes y flexibles para trabajar con JSON en Java. Permite convertir objetos Java a JSON y viceversa de manera sencilla.

#### **Clases Principales de Jackson:**
- **`ObjectMapper`**: Es la clase principal para leer y escribir JSON. Proporciona métodos para convertir entre JSON y objetos Java.



### **2. Gson**

**Gson** es una biblioteca desarrollada por Google que permite la conversión entre objetos Java y JSON de forma sencilla.

#### **Clases Principales de Gson:**
- **`Gson`**: Clase principal para la serialización y deserialización de JSON.
- **`JsonElement`, `JsonObject`, `JsonArray`**: Clases para manejar estructuras JSON de manera más granular.

---

## **Ejemplos Prácticos**

A continuación, se muestra cómo utilizar **Jackson** y **Gson** para leer y escribir JSON en Java.

### **1. Usando Jackson**

#### **Agregar Dependencia (Maven):**
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.14.0</version>
</dependency>
```

#### **Ejemplo de Clase Java:**
```java
public class Persona {
    private String nombre;
    private int edad;
    private String correo;
    private List<Direccion> direcciones;

    // Getters y Setters
}

public class Direccion {
    private String tipo;
    private String direccion;

    // Getters y Setters
}
```

#### **Serializar Objeto Java a JSON:**
```java
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;

public class JacksonSerializeExample {
    public static void main(String[] args) {
        try {
            Persona persona = new Persona();
            persona.setNombre("Juan Pérez");
            persona.setEdad(30);
            persona.setCorreo("juan.perez@example.com");
            persona.setDirecciones(Arrays.asList(
                new Direccion("casa", "Calle Falsa 123"),
                new Direccion("trabajo", "Avenida Siempre Viva 456")
            ));

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persona);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**Salida:**
```json
{
  "nombre" : "Juan Pérez",
  "edad" : 30,
  "correo" : "juan.perez@example.com",
  "direcciones" : [ {
    "tipo" : "casa",
    "direccion" : "Calle Falsa 123"
  }, {
    "tipo" : "trabajo",
    "direccion" : "Avenida Siempre Viva 456"
  } ]
}
```

#### **Deserializar JSON a Objeto Java:**
```java
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JacksonDeserializeExample {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Persona persona = mapper.readValue(new File("persona.json"), Persona.class);
            
            System.out.println("Nombre: " + persona.getNombre());
            System.out.println("Edad: " + persona.getEdad());
            System.out.println("Correo: " + persona.getCorreo());
            for (Direccion dir : persona.getDirecciones()) {
                System.out.println("Direccion (" + dir.getTipo() + "): " + dir.getDireccion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### **2. Usando Gson**

#### **Agregar Dependencia (Maven):**
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

#### **Serializar Objeto Java a JSON:**
```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;

public class GsonSerializeExample {
    public static void main(String[] args) {
        Persona persona = new Persona();
        persona.setNombre("Juan Pérez");
        persona.setEdad(30);
        persona.setCorreo("juan.perez@example.com");
        persona.setDirecciones(Arrays.asList(
            new Direccion("casa", "Calle Falsa 123"),
            new Direccion("trabajo", "Avenida Siempre Viva 456")
        ));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(persona);
        System.out.println(jsonString);
    }
}
```

**Salida:**
```json
{
  "nombre": "Juan Pérez",
  "edad": 30,
  "correo": "juan.perez@example.com",
  "direcciones": [
    {
      "tipo": "casa",
      "direccion": "Calle Falsa 123"
    },
    {
      "tipo": "trabajo",
      "direccion": "Avenida Siempre Viva 456"
    }
  ]
}
```

#### **Deserializar JSON a Objeto Java:**
```java
import com.google.gson.Gson;
import java.io.FileReader;

public class GsonDeserializeExample {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("persona.json");
            Persona persona = gson.fromJson(reader, Persona.class);
            reader.close();

            System.out.println("Nombre: " + persona.getNombre());
            System.out.println("Edad: " + persona.getEdad());
            System.out.println("Correo: " + persona.getCorreo());
            for (Direccion dir : persona.getDirecciones()) {
                System.out.println("Direccion (" + dir.getTipo() + "): " + dir.getDireccion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
