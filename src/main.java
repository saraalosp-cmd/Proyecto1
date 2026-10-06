import java.sql.Connection;
import java.util.Scanner;

public class main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Connection conn = ConexionDB.conectar(sc);
        if (conn == null) {
            System.out.println("No se pudo conectar. Saliendo.");
            return;
        } 
    }
}
