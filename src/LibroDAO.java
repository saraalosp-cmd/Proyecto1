import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibroDAO {
    
    public List<Libro> imprimirBiblioteca() {

    }

    public Libro imprimirLibro(int id) {

    }

    public void añadirLibro(Libro libro) {

    }

    public void modificarLibro(int id) {

    }

    public void eliminarLibro(int id) {

    }

    public Connection conexion(Scanner sc) {

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

    public void desconexion(Connection conexDB) {
		if (conexDB != null) {
			try {
				conexDB.close();
				System.out.println("\nDesconectado correctamente.");
			} catch (SQLException e) {
				System.out.println("Error al desconectar: " + e.getMessage());
			}
		} else {
			System.out.println("No hay ninguna conexión activa.");
		}
    }
}
