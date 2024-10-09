package es.cheste.UD2.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Statement;

public class EJEMPLO2_Conexion {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {

        ConexionBD conexion = new ConexionBD();
        consultaEmpleado(conexion);
        conexion.desconectar();
    }

    private static void consultaEmpleado(ConexionBD conexion) {

        String sql = "SELECT first_name, last_name  FROM employees LIMIT 25";
        if(conexion == null) {
            LOGGER.error("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            System.out.println("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            return;
        }

        try (Statement stmt = conexion.getConnection().createStatement();){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Empleado: " + rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        } catch (Exception e) {
            LOGGER.error("Error al consultar empleados", e);
        }
    }

}
