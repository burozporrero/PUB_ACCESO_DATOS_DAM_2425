# POJO's

Un POJO (Plain Old Java Object) es una clase simple de Java que no sigue ningún estándar especial más allá de las convenciones básicas de Java. En el contexto de JPA (Java Persistence API), un POJO se usa como entidad para mapear una tabla en una base de datos relacional.

### Características que un POJO en JPA **debe** cumplir:

1. **Anotación `@Entity`**:
    - La clase debe estar anotada con `@Entity` para que JPA la reconozca como una entidad que será gestionada por el framework de persistencia.
   ```java
   @Entity
   public class MyEntity { ... }
   ```

2. **Constructor sin parámetros (constructor por defecto)**:
    - JPA requiere un constructor público o protegido sin argumentos para poder crear instancias de la entidad de forma automática cuando recupera datos de la base de datos.
   ```java
   public MyEntity() { }
   ```

3. **Clave primaria (`@Id`)**:
    - Debe tener un atributo marcado con la anotación `@Id`, que representará la clave primaria de la tabla asociada en la base de datos.
   ```java
   @Id
   private Long id;
   ```

4. **Serializable** (opcional en algunos casos, pero recomendado):
    - Las clases POJO que representan entidades JPA **deben** implementar la interfaz `Serializable` si la entidad se va a pasar a través de diferentes capas o si se usa en contextos distribuidos (aunque no es obligatorio estrictamente para JPA).
   ```java
   public class MyEntity implements Serializable { ... }
   ```

5. **Modificadores de acceso**:
    - Los campos (atributos) de la clase deben ser privados o al menos no públicos, siguiendo los principios de encapsulación. Esto obliga a usar getters y setters para acceder a los campos.

6. **Relaciones entre entidades** (si las hay):
    - Si el POJO tiene relaciones con otras entidades (como `@OneToMany`, `@ManyToOne`, etc.), debe especificar las correspondientes anotaciones JPA para definir dichas relaciones.
   ```java
   @OneToMany(mappedBy = "parent")
   private List<ChildEntity> children;
   ```

### Características **aconsejables** para un POJO en JPA:

1. **Uso de `@Table` para personalizar la tabla**:
    - Aunque JPA genera automáticamente el nombre de la tabla en función de la clase, es recomendable usar `@Table` para definir explícitamente el nombre de la tabla.
   ```java
   @Entity
   @Table(name = "my_table")
   public class MyEntity { ... }
   ```

2. **Definir la estrategia de generación de claves primarias**:
    - Es aconsejable anotar la clave primaria con `@GeneratedValue` y especificar la estrategia de generación (`AUTO`, `SEQUENCE`, `IDENTITY`, etc.).
   ```java
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   ```

3. **Clases wrapper** en lugar de tipos primitivos para los atributos. Las bases de datos relacionales permiten que 
   los campos tengan valores null. Sin embargo, los tipos primitivos en Java no pueden tener el valor `null` (por 
   ejemplo, un int siempre tiene un valor por defecto de 0 si no se inicializa). En cambio, las clases envoltorio 
   pueden contener valores null, lo que permite reflejar de manera precisa el estado de los datos en la base de datos.

   ```java
   private Long id;
   private String name;
   private Integer age;
   private Boolean active;
   ```

4. **Métodos `equals()` y `hashCode()`**:
    - Es recomendable sobrescribir estos métodos para garantizar que las comparaciones entre entidades sean consistentes y eficientes, especialmente cuando se trabaja con colecciones o en contextos de caché.
   ```java
   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;
       MyEntity myEntity = (MyEntity) o;
       return Objects.equals(id, myEntity.id);
   }

   @Override
   public int hashCode() {
       return Objects.hash(id);
   }
   ```

5. **Uso de `toString()`**:
    - Sobrescribir `toString()` para facilitar el registro y depuración es una buena práctica, pero cuidado con incluir relaciones perezosas (lazy-loaded), ya que puede generar problemas de rendimiento o errores de carga tardía.
   ```java
   @Override
   public String toString() {
       return "MyEntity{" +
               "id=" + id +
               '}';
   }
   ```

6. **Validaciones con `@NotNull`, `@Size`, etc.**:
    - Es aconsejable usar anotaciones de validación como `@NotNull`, `@Size`, `@Min`, etc., para validar los atributos de la entidad y asegurarse de que la integridad de los datos se mantiene tanto en la aplicación como en la base de datos.
   ```java
   @NotNull
   @Size(min = 3, max = 100)
   private String name;
   ```

7. **Lazy loading en colecciones**:
    - Para mejorar el rendimiento, es recomendable que las relaciones que involucran colecciones (como `@OneToMany`) se carguen de manera perezosa (`fetch = FetchType.LAZY`), de modo que solo se carguen cuando se necesiten.
   ```java
   @OneToMany(fetch = FetchType.LAZY)
   private List<ChildEntity> children;
   ```

8. **Anotación `@Version` (control de concurrencia optimista)**:
    - En casos donde haya concurrencia en la actualización de los datos, es aconsejable usar la anotación `@Version` para controlar las versiones de las entidades y evitar actualizaciones inconsistentes.
   ```java
   @Version
   private Integer version;
   ```

9. **Uso de `@Transient` para campos no persistentes**:
    - Si hay campos que no deben persistirse en la base de datos, se aconseja marcarlos con `@Transient` para evitar que JPA intente mapearlos.
   ```java
   @Transient
   private String tempData;
   ```
