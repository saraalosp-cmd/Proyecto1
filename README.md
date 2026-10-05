# Proyecto1

## 📚 Biblioteca
Aplicación para gestionar la biblioteca personal. Permite almacenar libros y hacer un seguimiento de cada uno según su estado: los que quiero, los que tengo, los que estoy leyendo, los que he leído.
Cada libro se guarda con: título, autor, ISBN, editorial, número de páginas, sinopsis, fechas de lectura y estado.

### 🛠️ Tecnologías

- Java 
- MySQL
- JDBC (driver de conexión)
- Visual Studio Code

### ✅ Requisitos funcionales
Como usuario de la app, quiero:

-  Consultar la información de un libro.
-  Visualizar la biblioteca completa.
-  Añadir, actualizar, eliminar libros para mantener la biblioteca actualizada.
-  Modificar la sinopsis de cualquier libro.
-  Elegir el estado de cada libro (lo tengo, leyendo, deseado).
-  Filtrar por autor, título o estado.
-  Añadir el libro leyendo el ISBN.
-  Añadir fecha inicio/fecha fin.
-  Que el estado cambie a leyendo si se añade una fecha inicio.
-  Que el estado cambie a leído si la fecha fin es anterior a la actual.
-  Conocer las páginas de cada libro.
-  Conocer la editorial de cada libro.
-  Tener acceso a la biblioteca a través de sus páginas.

### 🖥️ Interfaz

- Sección **Biblioteca**: listado de libros con título, autor, estado y fechas de lectura, con accesos para modificar la información y el estado.
- Sección **Libro**: información ampliada (ISBN, páginas, editorial) y sinopsis editable.
- Barra superior de navegación, con la configuración del perfil.

### 🗄️ Base de datos

```sql
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE IF NOT EXISTS Libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR (200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    ISBN VARCHAR(50) UNIQUE,
    editorial VARCHAR(50),
    paginas INT,
    sinopsis TEXT,
    inicio_lectura DATE,
    fin_lectura DATE,
    estado ENUM ("lo tengo", "leyendo", "leído", "deseado") NOT NULL DEAFULT "lo tengo"
);
```

### 🔌 Conexión DB

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConexionDB {
    public static Connection conectar(Scanner sc) {

        String servidor = pedir(sc, "Servidor", "localhost");
        String puerto = pedir(sc, "Puerto", "3306");
        String db = pedir(sc, "Base de datos", "biblioteca");
        String usuario = pedir(sc, "Usuario", "root");
        System.out.println("Contraseña []: ");
        String contrasenya = sc.nextLine();

        String url = "jdbc:mysql://" + servidor + ":" + puerto + "/" + db + "?serverTimezone=UTC";

        try {
            Connection conexDB = DriverManager.getConnection(url, usuario, contrasenya);
            System.out.println("Conexión exitosa!\n");
            return conexDB;
        } catch (SQLException e) {
            System.out.println("Error en conexión :" + e.getMessage());
            return null;
        }
    }

    public static String pedir(Scanner sc, String dato, String defecto) {
        System.out.println(dato + " [" + defecto + "]: ");
        String valor = sc.nextLine().trim();
        return valor.isEmpty() ? defecto : valor;
    }
}

```
Decido descargar el [driver para la conexión](https://dev.mysql.com/downloads/connector/j/) desde la página oficial de MySQL,
y crear la siguiente estructura en mi proyecto:

- src: código fuente
- lib: conector MySQL (.jar)
- biblioteca.sql

Añado también un archivo settings.json para indicar a VSC que los .jar de lib son librerías del proyecto

```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```
Después, recargo el proyecto (*Java: Clean Java Language Server Workspace*).

El código de conexión está sacado de un proyecto de 1ºDAM para Programación, con pequeños ajustes:

- El ejercicio solicitaba usar Console para ocultar la contraseña, pero daba muchos problemas.
- A la hora de realizar pruebas, había que introducir muchas veces cada parámetro de acceso, por lo que he decidido crear un método que los solicite pasándole el valor por defecto. De esta forma, si no cambia, con presionar Enter tengo el valor que yo utilizo para conectarme a la base de datos.

