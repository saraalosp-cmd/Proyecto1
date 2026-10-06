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
