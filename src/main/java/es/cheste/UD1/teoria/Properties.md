# Ficheros de propiedades

En el desarrollo de aplicaciones Java, un fichero `.properties` es un archivo de texto sencillo utilizado para 
almacenar configuraciones y parámetros de la aplicación de manera estructurada. Su formato se basa en pares 
clave-valor, donde cada línea del archivo representa una propiedad específica de la aplicación. Este formato 
facilita la gestión de configuraciones, permitiendo separar la lógica del código de los valores configurables, lo 
que a su vez simplifica la personalización y el mantenimiento de la aplicación sin necesidad de recompilar el código fuente.

Las propiedades en un fichero `.properties` se definen mediante una clave única seguida de un signo igual (`=`) o 
dos puntos (`:`), y luego el valor correspondiente. Además, se pueden incluir comentarios precedidos por el símbolo 
de numeral (`#`) o punto y coma (`;`), lo que permite documentar o deshabilitar temporalmente ciertas propiedades. 
Es importante que el archivo esté codificado en ISO 8859-1, aunque se pueden utilizar secuencias de escape Unicode 
para representar caracteres especiales que no están en este conjunto.

Estos archivos son ampliamente utilizados para la internacionalización (i18n) de aplicaciones Java, permitiendo 
almacenar cadenas de texto en diferentes idiomas y cargar dinámicamente las que correspondan según la configuración 
regional del usuario. Además, sirven para definir configuraciones de la aplicación, como parámetros de conexión a 
bases de datos, ajustes de comportamiento, rutas de archivos, entre otros.

Por ejemplo, un fichero `config.properties` podría contener lo siguiente:

```properties
# Configuración de la base de datos
db.url=jdbc:mysql://localhost:3306/mi_base_de_datos
db.user=usuario
db.password=contraseña123

# Configuración de la aplicación
app.nombre=MiAplicación
app.version=1.0.0
app.debug=true

# Mensajes para internacionalización
mensaje.bienvenida=¡Bienvenido a MiAplicación!
mensaje.error=Ha ocurrido un error inesperado.
```

Al trabajar con ficheros `.properties` en Java, existen varias clases fundamentales que facilitan la carga, manipulación y gestión de estos archivos. A continuación, se detallan las clases más importantes:

### 1. `java.util.Properties`

La clase `Properties` está diseñada específicamente para manejar pares clave-valor donde tanto las claves como los 
valores son cadenas de texto.

Permite cargar propiedades desde un `InputStream` o `Reader` y guardar propiedades en un `OutputStream` o `Writer`.

#### Ejemplo de uso: [configuraciones de mysql](../practica/EJEMPLO2_PropertiesMysql.java)

### 2. `java.util.ResourceBundle`

La clase `ResourceBundle` es esencial para la internacionalización (i18n) de aplicaciones Java. Permite gestionar 
múltiples conjuntos de propiedades según diferentes locales, facilitando la traducción y adaptación de la aplicación a distintos idiomas y regiones.

Carga automáticamente el conjunto de propiedades adecuado según el `Locale` especificado.

**Ejemplo de uso:**

Supongamos que tenemos dos archivos de propiedades para soportar inglés y español:

- `messages_en.properties`
  ```properties
  greeting=Hello
  farewell=Goodbye
  ```

- `messages_es.properties`
  ```properties
  greeting=¡Hola
  farewell=Adiós
  ```

Código para cargar y usar los recursos:

```java
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleExample {
    public static void main(String[] args) {
        // Establecer el locale a español
        Locale locale = new Locale("es", "ES");
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        
        // Obtener mensajes
        String greeting = messages.getString("greeting");
        String farewell = messages.getString("farewell");
        
        System.out.println(greeting); // Output: ¡Hola
        System.out.println(farewell); // Output: Adiós
    }
}
```
#### Ejemplo de uso: [labels](../practica/EJEMPLO3_PropertiesLanguages.java)
