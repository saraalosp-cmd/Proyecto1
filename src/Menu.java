import java.sql.Connection;
import java.util.Scanner;

public class Menu {
        private Scanner sc = new Scanner(System.in);
        private LibroDAO libroDAO = new LibroDAO();
        private Connection conexion;

        String menu = """
            BIBLIOTECA
            1. Conectar
            2. Imprimir biblioteca
            3. Ver libro
            4. Añadir libro
            5. Modificar libro
            6. Eliminar libro
            0. Salir
        """;

        public void validarOpcion() {
            int opcion;
            int id;

            do {
                System.out.println("\n" + menu);
                opcion = Integer.parseInt(sc.nextLine());

                if (opcion >= 2 && opcion <= 6 && conexion == null) {
                    System.out.println("Primero conecta la base de datos (opción 1).");
                    continue;
                }

                switch(opcion) {
                    case 1: 
                        conexion = libroDAO.conexion(sc);
                        break;
                    case 2: 
                        System.out.println("\nIMPRIMIR BIBLIOTECA");
                        libroDAO.imprimirBiblioteca(conexion);
                        break;
                    case 3: 
                        System.out.println("\nIMPRIMIR LIBRO");
                        System.out.println("Introduce el id: ");
                        id = Integer.parseInt(sc.nextLine());
                        libroDAO.imprimirLibro(conexion, id);
                        break;
                    case 4:
                        System.out.println("\nAÑADIR LIBRO");
                        System.out.println("Título: ");
                        String titulo = sc.nextLine();
                        System.out.println("Autor: ");
                        String autor = sc.nextLine();
                        System.out.println("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.println("Editorial: ");
                        String editorial = sc.nextLine();
                        System.out.println("Páginas: ");
                        int paginas = Integer.parseInt(sc.nextLine());
                        System.out.println("Sinopsis: ");
                        String sinopsis = sc.nextLine();
                        System.out.println("Estado (lo tengo, leyendo, leido, deseado): ");
                        EstadoLectura estado = EstadoLectura.desdeDB(sc.nextLine().toLowerCase());

                        Libro libro = new Libro (titulo, autor, isbn, editorial, paginas, sinopsis, estado);
                        libroDAO.añadirLibro(conexion, libro);
                        break;
                    case 5: 
                        System.out.println("\nMODIFICAR LIBRO");
                        System.out.println("Introduce el id: ");
                        id = Integer.parseInt(sc.nextLine());
                        libroDAO.modificarLibro(conexion,sc, id);
                        break;
                    case 6: 
                        System.out.println("\nELIMINAR LIBRO");
                        System.out.println("Introduce el id: ");
                        id = Integer.parseInt(sc.nextLine());
                        libroDAO.eliminarLibro(conexion, id);
                        break;
                    case 0:
                        System.out.println("\nSALIR");
                        System.out.println("Saliendo...");
                        libroDAO.desconexion(conexion);
                        break;
                    default: 
                        System.out.println("Opción no válida.");

                }
            } while (opcion != 0);
        }
    public static void main(String[] args) {
        Menu app = new Menu();
        app.validarOpcion();
    }
}