## ¿Qué es JDBC?

**JDBC (Java Database Connectivity)** es una API estándar de Java que permite a las aplicaciones Java interactuar con bases de datos relacionales. Proporciona un conjunto de interfaces y clases para realizar operaciones como conectar a una base de datos, ejecutar consultas SQL, actualizar datos y gestionar transacciones.

### Componentes Clave de JDBC

1. **JDBC Driver:**
    - Es un conjunto de clases que implementan las interfaces de JDBC para una base de datos específica.

2. **Connection:**
    - Representa una conexión activa con la base de datos.
    - Proporciona métodos para crear `Statement` y gestionar transacciones.

3. **Statement, PreparedStatement y CallableStatement:**
    - **Statement:** Ejecuta consultas SQL estáticas.
    - **PreparedStatement:** Ejecuta consultas SQL precompiladas con parámetros.
    - **CallableStatement:** Ejecuta procedimientos almacenados en la base de datos.

4. **ResultSet:**
    - Representa el conjunto de resultados de una consulta SQL.
    - Permite iterar sobre las filas obtenidas.

5. **SQLException:**
    - Maneja errores y excepciones relacionadas con la base de datos.

## Configuración de un Proyecto Maven para Usar JDBC con MySQL

### 1. Crear un Proyecto Maven

Puedes crear un nuevo proyecto Maven utilizando tu IDE favorito (como IntelliJ IDEA, Eclipse o NetBeans) o mediante la línea de comandos.

**Estructura del Proyecto:**

```
mi-proyecto-jdbc
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ejemplo
│   │   │           ├── Main.java
│   │   │           └── dao
│   │   │               ├── Conexion.java
│   │   │               └── VehiculoDAO.java
│   │   └── resources
│   └── test
│       └── java
│
└── pom.xml
```

### 2. Configurar el `pom.xml`

El archivo `pom.xml` es esencial para gestionar las dependencias y la configuración del proyecto Maven. Para conectar con MySQL usando JDBC, necesitas agregar la dependencia del conector MySQL.

```xml
<!-- pom.xml -->
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
                             
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.ejemplo</groupId>
    <artifactId>mi-proyecto-jdbc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>Mi Proyecto JDBC</name>

    <dependencies>
        <!-- Conector MySQL JDBC -->
        <dependency>
           <groupId>org.mariadb.jdbc</groupId>
           <artifactId>mariadb-java-client</artifactId>
           <version>3.4.1</version>
        </dependency>
        
        <!-- (Opcional) SLF4J para desactivar errores logging de mariadb-->
       <dependency>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-nop</artifactId>
          <version>2.0.7</version>
       </dependency>
    </dependencies>
</project>
```

**Notas:**
- Asegúrate de que la versión del conector MySQL sea compatible con tu versión de MySQL.
- Puedes ajustar la versión de Java (`<source>` y `<target>`) según tus necesidades.

### 3. Crear la Clase de Conexión

Es una buena práctica encapsular la lógica de conexión en una clase separada. Aquí, crearemos una clase `Conexion` 
que maneje la obtención de conexiones a la base de datos y los datos de conexión se recogerán de un archivo de propiedades.

```java
// src/main/java/com/ejemplo/dao/Conexion.java
package com.ejemplo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "tu_usuario";
    private static final String CONTRASENA = "tu_contraseña";

    static {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL.");
            e.printStackTrace();
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
```

### 4. Crear una Clase de Entidad

Supongamos que tienes una tabla `Vehiculo` en tu base de datos:

```sql
CREATE TABLE Vehiculo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    num_puertas INT
);
```

Creamos la clase Java correspondiente:

```java
// src/main/java/com/ejemplo/dao/Vehiculo.java
package com.ejemplo.dao;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private int numPuertas;

    // Constructores
    public Vehiculo() {}

    public Vehiculo(String marca, String modelo, int numPuertas) {
        this.marca = marca;
        this.modelo = modelo;
        this.numPuertas = numPuertas;
    }

    public Vehiculo(int id, String marca, String modelo, int numPuertas) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.numPuertas = numPuertas;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }

    @Override
    public String toString() {
        return "Vehiculo [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", numPuertas=" + numPuertas + "]";
    }
}
```

### 5. Crear la Clase DAO

Implementaremos una clase `VehiculoDAO` para manejar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad `Vehiculo`.

```java
// src/main/java/com/ejemplo/dao/VehiculoDAO.java
package com.ejemplo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // Consulta para insertar un nuevo vehículo
    private static final String INSERTAR = "INSERT INTO Vehiculo (marca, modelo, num_puertas) VALUES (?, ?, ?)";

    // Consulta para obtener un vehículo por ID
    private static final String OBTENER_POR_ID = "SELECT * FROM Vehiculo WHERE id = ?";

    // Consulta para obtener todos los vehículos
    private static final String OBTENER_TODOS = "SELECT * FROM Vehiculo";

    // Consulta para actualizar un vehículo
    private static final String ACTUALIZAR = "UPDATE Vehiculo SET marca = ?, modelo = ?, num_puertas = ? WHERE id = ?";

    // Consulta para eliminar un vehículo
    private static final String ELIMINAR = "DELETE FROM Vehiculo WHERE id = ?";

    // Método para insertar un nuevo vehículo
    public void insertar(Vehiculo vehiculo) {
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setInt(3, vehiculo.getNumPuertas());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        vehiculo.setId(rs.getInt(1));
                        System.out.println("Vehículo insertado con ID: " + vehiculo.getId());
                    }
                }
            } else {
                System.out.println("Inserción fallida.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un vehículo por ID
    public Vehiculo obtenerPorId(int id) {
        Vehiculo vehiculo = null;

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = mapearVehiculo(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculo;
    }

    // Método para obtener todos los vehículos
    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo vehiculo = mapearVehiculo(rs);
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculos;
    }

    // Método para actualizar un vehículo
    public void actualizar(Vehiculo vehiculo) {
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(ACTUALIZAR)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setInt(3, vehiculo.getNumPuertas());
            ps.setInt(4, vehiculo.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Vehículo actualizado: " + vehiculo);
            } else {
                System.out.println("Actualización fallida para ID: " + vehiculo.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un vehículo por ID
    public void eliminar(int id) {
        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(ELIMINAR)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Vehículo eliminado con ID: " + id);
            } else {
                System.out.println("Eliminación fallida para ID: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para mapear ResultSet a Vehiculo
    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int numPuertas = rs.getInt("num_puertas");

        return new Vehiculo(id, marca, modelo, numPuertas);
    }
}
```

**Explicación:**
- **PreparedStatement:** Se utiliza para evitar inyecciones SQL y mejorar el rendimiento al reutilizar consultas precompiladas.
- **try-with-resources:** Asegura que los recursos (`Connection`, `PreparedStatement`, `ResultSet`) se cierren automáticamente, evitando fugas de recursos.
- **mapearVehiculo:** Convierte una fila del `ResultSet` en un objeto `Vehiculo`.

### 6. Crear la Clase Principal para Probar la Conexión

Implementaremos una clase `Main` para probar las operaciones CRUD.

```java
// src/main/java/com/ejemplo/Main.java
package com.ejemplo;

import com.ejemplo.dao.Vehiculo;
import com.ejemplo.dao.VehiculoDAO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        // 1. Insertar un nuevo vehículo
        Vehiculo nuevoVehiculo = new Vehiculo("Toyota", "Corolla", 4);
        vehiculoDAO.insertar(nuevoVehiculo);

        // 2. Obtener un vehículo por ID
        Vehiculo vehiculoObtenido = vehiculoDAO.obtenerPorId(nuevoVehiculo.getId());
        System.out.println("Vehículo obtenido: " + vehiculoObtenido);

        // 3. Actualizar el vehículo
        vehiculoObtenido.setMarca("Honda");
        vehiculoObtenido.setModelo("Civic");
        vehiculoDAO.actualizar(vehiculoObtenido);

        // 4. Obtener todos los vehículos
        List<Vehiculo> vehiculos = vehiculoDAO.obtenerTodos();
        System.out.println("Lista de vehículos:");
        vehiculos.forEach(System.out::println);

        // 5. Eliminar el vehículo
        vehiculoDAO.eliminar(vehiculoObtenido.getId());
    }
}
```

## Buenas Prácticas

- **Manejo de Excepciones:**
    - Implementa un manejo adecuado de excepciones, posiblemente creando excepciones personalizadas para diferenciar entre distintos tipos de errores.

- **Separación de Concerns:**
    - Mantén separadas las capas de acceso a datos, lógica de negocio y presentación para una arquitectura más limpia y mantenible.

- **Seguridad:**
    - Nunca expongas las credenciales de la base de datos en el código fuente. Considera usar variables de entorno o archivos de configuración seguros.

- **Validación de Datos:**
    - Siempre valida y sanitiza los datos antes de realizar operaciones en la base de datos para evitar inyecciones SQL y otros tipos de ataques.
