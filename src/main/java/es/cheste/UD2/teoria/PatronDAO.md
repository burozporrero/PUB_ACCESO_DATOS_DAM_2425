## Índice

1. [¿Qué es el Patrón DAO?](#qué-es-el-patrón-dao)
2. [Beneficios del Patrón DAO](#beneficios-del-patrón-dao)
3. [Estructura del Patrón DAO](#estructura-del-patrón-dao)
4. [Implementación Paso a Paso](#implementación-paso-a-paso)
    - [1. Configuración del Proyecto](#1-configuración-del-proyecto)
    - [2. Creación de la Clase de Entidad](#2-creación-de-la-clase-de-entidad)
    - [3. Definición de la Interfaz DAO](#3-definición-de-la-interfaz-dao)
    - [4. Implementación de la Interfaz DAO](#4-implementación-de-la-interfaz-dao)
    - [5. Gestión de Conexiones a la Base de Datos](#5-gestión-de-conexiones-a-la-base-de-datos)
    - [6. Uso del DAO en la Lógica de Negocio](#6-uso-del-dao-en-la-lógica-de-negocio)
5. [Mejores Prácticas](#mejores-prácticas)
6. [Consideraciones Adicionales](#consideraciones-adicionales)
7. [Ejemplo Completo](#ejemplo-completo)
8. [Conclusión](#conclusión)

---

## ¿Qué es el Patrón DAO?

El **Patrón DAO (Data Access Object)** es un patrón de diseño que proporciona una abstracción entre la aplicación y la fuente de datos. 
Permite separar la lógica de negocio de los detalles de acceso a datos, lo que facilita el mantenimiento y la escalabilidad de la aplicación.

## Componentes del Patrón DAO

La estructura típica del patrón DAO incluye los siguientes componentes:

1. **Entidad (Modelo):** Clase Java que representa una tabla en la base de datos.
2. **Interfaz DAO:** Define métodos CRUD (Crear, Leer, Actualizar, Eliminar) y otras operaciones específicas.
3. **Implementación del DAO:** Implementa la interfaz DAO utilizando una tecnología de acceso a datos (por ejemplo, JDBC, Hibernate).
4. **Servicio/Negocio:** Clase que utiliza el DAO para realizar operaciones de negocio.

---

## Beneficios del Patrón DAO

- **Separación de Concerns:** Separa la lógica de acceso a datos de la lógica de negocio.
- **Mantenibilidad:** Facilita el mantenimiento y las actualizaciones, ya que los cambios en la lógica de acceso a datos no afectan la lógica de negocio.
- **Reutilización de Código:** Permite reutilizar el código de acceso a datos en diferentes partes de la aplicación.
- **Facilita las Pruebas:** Simplifica las pruebas unitarias al permitir el uso de DAOs mockeados.
- **Flexibilidad:** Facilita el cambio de la fuente de datos sin afectar la lógica de negocio (por ejemplo, cambiar de una base de datos SQL a NoSQL).

---

## Implementación Paso a Paso

### 1. Configuración del Proyecto

Para este ejemplo, usaremos **JDBC** para la interacción con la base de datos. Asegúrate de tener configurado tu entorno de desarrollo con:

- **Java Development Kit (JDK) 8 o superior**
- **Un IDE** como IntelliJ IDEA, Eclipse o NetBeans
- **Dependencias JDBC** para la base de datos que estés utilizando (por ejemplo, MySQL, PostgreSQL)

Para este ejemplo, supongamos que estamos utilizando **MySQL**.

#### Dependencias

Si estás utilizando **Maven**, agrega la dependencia de MySQL en tu `pom.xml`:

```xml
<dependencies>
    <!-- Dependencia de MySQL JDBC -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.32</version>
    </dependency>
</dependencies>
```

### 2. Creación de la Clase de Entidad

Supongamos que tenemos una tabla `Vehiculo` en la base de datos:

```sql
CREATE TABLE Vehiculo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    num_puertas INT
);
```

Creamos la clase de entidad correspondiente en Java:

```java
// src/main/java/com/ejemplo/modelo/Vehiculo.java

package com.ejemplo.modelo;

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

### 3. Definición de la Interfaz DAO

Definimos una interfaz que declara las operaciones que se pueden realizar sobre la entidad `Vehiculo`.

```java
// src/main/java/com/ejemplo/dao/VehiculoDAO.java

package com.ejemplo.dao;

import com.ejemplo.modelo.Vehiculo;
import java.util.List;

public interface VehiculoDAO {
    // Operaciones CRUD
    void insertar(Vehiculo vehiculo) throws DAOException;
    Vehiculo obtenerPorId(int id) throws DAOException;
    List<Vehiculo> obtenerTodos() throws DAOException;
    void actualizar(Vehiculo vehiculo) throws DAOException;
    void eliminar(int id) throws DAOException;
}
```

#### Nota sobre `DAOException`

Es una buena práctica definir una excepción personalizada para el DAO, lo que permite manejar de manera consistente los errores de acceso a datos.

```java
// src/main/java/com/ejemplo/dao/DAOException.java

package com.ejemplo.dao;

public class DAOException extends Exception {
    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
```

### 4. Implementación de la Interfaz DAO

Implementamos la interfaz `VehiculoDAO` utilizando **JDBC**.

```java
// src/main/java/com/ejemplo/dao/impl/VehiculoDAOImpl.java

package com.ejemplo.dao.impl;

import com.ejemplo.dao.DAOException;
import com.ejemplo.dao.VehiculoDAO;
import com.ejemplo.modelo.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {

    // URL de conexión, usuario y contraseña de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USUARIO = "tu_usuario";
    private static final String CONTRASENA = "tu_contraseña";

    // Consultas SQL
    private static final String INSERTAR = "INSERT INTO Vehiculo (marca, modelo, num_puertas) VALUES (?, ?, ?)";
    private static final String OBTENER_POR_ID = "SELECT * FROM Vehiculo WHERE id = ?";
    private static final String OBTENER_TODOS = "SELECT * FROM Vehiculo";
    private static final String ACTUALIZAR = "UPDATE Vehiculo SET marca = ?, modelo = ?, num_puertas = ? WHERE id = ?";
    private static final String ELIMINAR = "DELETE FROM Vehiculo WHERE id = ?";

    // Método para obtener la conexión
    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws DAOException {
        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setInt(3, vehiculo.getNumPuertas());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("Inserción de vehiculo fallida, no se afectaron filas.", null);
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    vehiculo.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error al insertar el vehiculo.", e);
        }
    }

    @Override
    public Vehiculo obtenerPorId(int id) throws DAOException {
        Vehiculo vehiculo = null;

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = mapearVehiculo(rs);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener el vehiculo por ID.", e);
        }

        return vehiculo;
    }

    @Override
    public List<Vehiculo> obtenerTodos() throws DAOException {
        List<Vehiculo> vehiculos = new ArrayList<>();

        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(OBTENER_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo vehiculo = mapearVehiculo(rs);
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener todos los vehiculos.", e);
        }

        return vehiculos;
    }

    @Override
    public void actualizar(Vehiculo vehiculo) throws DAOException {
        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(ACTUALIZAR)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setInt(3, vehiculo.getNumPuertas());
            ps.setInt(4, vehiculo.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("Actualización de vehiculo fallida, no se afectaron filas.", null);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar el vehiculo.", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        try (Connection conexion = obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(ELIMINAR)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new DAOException("Eliminación de vehiculo fallida, no se afectaron filas.", null);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el vehiculo.", e);
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

#### Explicación de la Implementación

1. **Constantes de Configuración:** Define la URL de conexión, el usuario y la contraseña para conectarse a la base de datos.

2. **Consultas SQL:** Define las consultas SQL necesarias para las operaciones CRUD.

3. **Método `obtenerConexion`:** Proporciona una conexión a la base de datos utilizando `DriverManager`. En entornos de producción, es preferible usar un pool de conexiones (discutido más adelante).

4. **Métodos CRUD:**
    - **`insertar`:** Inserta un nuevo vehículo en la base de datos y obtiene la clave generada automáticamente.
    - **`obtenerPorId`:** Recupera un vehículo por su ID.
    - **`obtenerTodos`:** Recupera todos los vehículos de la base de datos.
    - **`actualizar`:** Actualiza los detalles de un vehículo existente.
    - **`eliminar`:** Elimina un vehículo por su ID.

5. **`mapearVehiculo`:** Método auxiliar para convertir una fila del `ResultSet` en un objeto `Vehiculo`.

### 5. Uso del DAO en la Lógica de Negocio

Ahora, veamos cómo utilizar el `VehiculoDAO` en una capa de servicio o lógica de negocio.

```java
// src/main/java/com/ejemplo/servicio/VehiculoServicio.java

package com.ejemplo.servicio;

import com.ejemplo.dao.DAOException;
import com.ejemplo.dao.VehiculoDAO;
import com.ejemplo.dao.impl.VehiculoDAOImpl;
import com.ejemplo.modelo.Vehiculo;

import java.util.List;

public class VehiculoServicio {

    private VehiculoDAO vehiculoDAO;

    public VehiculoServicio() {
        this.vehiculoDAO = new VehiculoDAOImpl();
    }

    public void agregarVehiculo(String marca, String modelo, int numPuertas) {
        Vehiculo vehiculo = new Vehiculo(marca, modelo, numPuertas);
        try {
            vehiculoDAO.insertar(vehiculo);
            System.out.println("Vehiculo agregado con ID: " + vehiculo.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public Vehiculo obtenerVehiculo(int id) {
        try {
            return vehiculoDAO.obtenerPorId(id);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Vehiculo> listarVehiculos() {
        try {
            return vehiculoDAO.obtenerTodos();
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarVehiculo(int id, String marca, String modelo, int numPuertas) {
        Vehiculo vehiculo = new Vehiculo(id, marca, modelo, numPuertas);
        try {
            vehiculoDAO.actualizar(vehiculo);
            System.out.println("Vehiculo actualizado: " + vehiculo);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarVehiculo(int id) {
        try {
            vehiculoDAO.eliminar(id);
            System.out.println("Vehiculo eliminado con ID: " + id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
```

#### Uso en una Aplicación

```java
// src/main/java/com/ejemplo/Main.java

package com.ejemplo;

import com.ejemplo.modelo.Vehiculo;
import com.ejemplo.servicio.VehiculoServicio;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        VehiculoServicio servicio = new VehiculoServicio();

        // Agregar un nuevo vehículo
        servicio.agregarVehiculo("Toyota", "Corolla", 4);

        // Listar todos los vehículos
        List<Vehiculo> vehiculos = servicio.listarVehiculos();
        vehiculos.forEach(System.out::println);

        // Obtener un vehículo por ID
        Vehiculo vehiculo = servicio.obtenerVehiculo(1);
        System.out.println("Vehiculo obtenido: " + vehiculo);

        // Actualizar un vehículo
        servicio.actualizarVehiculo(1, "Honda", "Civic", 4);

        // Eliminar un vehículo
        servicio.eliminarVehiculo(1);
    }
}
```
