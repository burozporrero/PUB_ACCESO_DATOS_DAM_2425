# UD 2. Gestionar connectores

Este tema se trabaja sobre todo con el libro de la asignatura.

A continuación dejo información sobre algunos puntos relevantes:

1. [Desfase Objeto-relaciona](teoria/DesfaseObjetoRelacional.md)
2. [Aquí se detallan los pasos de conexión con la base de datos](teoria/JDBC.md)
3. Uso del [patrón de diseño DAO](teoria/PatronDAO.md) 

Para entender la conexión a base de datos dejo dos ejemplos:

1. [En el primero](practica/EJEMPLO1_Conexion.java) la idea es ver que la conexión es 
   muy sencilla de hacer y requiere muy pocos pasos, pero en ningún 
   caso debe hacerse así, ya que estamos deja toda la información de conexión desprotegida.
2. [En el segundo](practica/EJEMPLO2_Conexion.java), se muestra un ejemplo de como se 
   debe hacer, de una manera mucho más segura.
