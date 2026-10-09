import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class LibroDAO {

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
    
    public void imprimirBiblioteca(Connection conexion) {
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, titulo, autor, editorial, estado FROM Libro");

            System.out.printf("%-5s%-30s%-30s%-10s%-20s%-10%n", "id", "titulo", "autor", "isbn", "editorial", "estado");
            System.out.println("-".repeat(95));

            while (rs.next()) {
                System.out.printf("%-5s%-30s%-30s%-10s%-20s%-10%n",
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("isbn"),
                rs.getString("editorial"),
                rs.getString("estado"));
                }
        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
            System.out.println("SQLState: "+ e.getSQLState());
            System.out.println("VendorError: "+ e.getErrorCode());
        }
    }


    public void imprimirLibro(Connection conexion, int id) {
        Libro libro = buscarLibro(conexion, id);
        if (libro == null) {
            System.out.println("No existe ningún libro con id: " + id);
            return;
        }

        System.out.println("ID: "+ libro.getId());
        System.out.println("Título: "+ libro.getTitulo());
        System.out.println("Autor: "+ libro.getAutor());
        System.out.println("ISBN: "+ libro.getIsbn());
        System.out.println("Editorial: "+ libro.getEditorial());
        System.out.println("Páginas: "+ libro.getPaginas());
        System.out.println("Sinopsis: "+ libro.getSinopsis());
        System.out.println("Inicio Lectura: "+ libro.getInicioLectura());
        System.out.println("Fin Lectura: "+ libro.getFinLectura());
        System.out.println("Estado: "+ libro.getEstado().getValorDB());
  

        
    }


    public Libro buscarLibro (Connection conexion, int id) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT titulo, autor, isbn, editorial, paginas, " 
            + "sinopsis, inicio_lectura, fin_lectura, estado FROM Libro WHERE id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn"),
                    rs.getString("editorial"),
                    rs.getInt("paginas"),
                    rs.getString("sinopsis"),
                    EstadoLectura.desdeDB(rs.getString("estado")));
                libro.setId(id);
                Date inicioLectura = rs.getDate("inicio_lectura");
                Date finLectura = rs.getDate("fin_lectura");
                if (inicioLectura != null) libro.setInicioLectura(inicioLectura.toLocalDate());
                if (finLectura != null) libro.setFinLectura(finLectura.toLocalDate());
                
                return libro;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
            System.out.println("SQLState: "+ e.getSQLState());
            System.out.println("VendorError: "+ e.getErrorCode());
        }
        return null;
    }


    public void añadirLibro(Connection conexion, Libro libro) {

        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO Libro (titulo, autor, isbn, editorial, paginas, sinopsis, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getPaginas());
            ps.setString(6, libro.getSinopsis());
            ps.setString(7, libro.getEstado().getValorDB());

            ps.executeUpdate();
            System.out.println("Libro añadido correctamente.");

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
            System.out.println("SQLState: "+ e.getSQLState());
            System.out.println("VendorError: "+ e.getErrorCode());
        }
    }

    public void modificarLibro(Connection conexion, Scanner sc, int id) {
        Libro libro = buscarLibro(conexion, id);
        if (libro == null) {
            System.out.println("No existe ningún libro con id: " + id);
            return;
        }
        
        libro.setTitulo(pedir(sc, "Título", libro.getTitulo()));
        libro.setAutor(pedir(sc, "Autor", libro.getAutor()));
        libro.setIsbn(pedir(sc, "ISBN", libro.getIsbn()));
        libro.setEditorial(pedir(sc, "Editorial", libro.getEditorial()));
        libro.setPaginas(Integer.parseInt(pedir(sc, "Páginas", String.valueOf(libro.getPaginas()))));
        libro.setSinopsis(pedir(sc, "Sinopsis", libro.getSinopsis()));
        libro.setEstado(EstadoLectura.desdeDB(pedir(sc, "Estado", libro.getEstado().getValorDB())));

        String inicioActual = (libro.getInicioLectura() != null) ? libro.getInicioLectura().toString() : "";
        String inicioL = pedir (sc, "Inicio Lectura (aaaa-mm-dd)", inicioActual);
        libro.setInicioLectura(inicioL.isEmpty() ? null : LocalDate.parse(inicioL));

        String finActual = (libro.getFinLectura() != null) ? libro.getFinLectura().toString() : "";
        String finL = pedir (sc, "Fin Lectura (aaaa-mm-dd)", finActual);
        libro.setFinLectura(finL.isEmpty() ? null : LocalDate.parse(finL));

        Date inicio = null;
        if (libro.getInicioLectura() != null) {
            inicio = Date.valueOf(libro.getInicioLectura());
        }

        Date fin = null;
        if (libro.getFinLectura() != null) {
            fin = Date.valueOf(libro.getFinLectura());
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE Libro SET titulo = ?, autor = ?, isbn = ?, editorial = ?, paginas = ?, sinopsis = ?, inicio_lectura = ?, fin_lectura = ?, estado = ?");
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getPaginas());
            ps.setString(6, libro.getSinopsis());
            ps.setDate(7, inicio);
            ps.setDate(8, fin);
            ps.setString(9, libro.getEstado().getValorDB());

            ps.executeUpdate();
            System.out.println("Libro modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
            System.out.println("SQLState: "+ e.getSQLState());
            System.out.println("VendorError: "+ e.getErrorCode());
        }
    }

    public void eliminarLibro(Connection conexion, int id) {
        Libro libro = buscarLibro(conexion, id);
        if (libro == null) {
            System.out.println("No existe ningún libro con id: " + id);
            return;
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE * FROM Libro WHERE id = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Libro eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
            System.out.println("SQLState: "+ e.getSQLState());
            System.out.println("VendorError: "+ e.getErrorCode());
        }
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
