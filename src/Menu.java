import java.util.Scanner;

public class Menu {
    
    private Scanner sc = new Scanner(System.in);
    private LibroDAO libroDAO = new LibroDAO();

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
            System.out.println(menu);
            opcion = sc.nextInt();

            switch(opcion) {
                case 1: 
                    conexion();
                    break;
                case 2: 
                    imprimirBiblioteca();
                    break;
                case 3: 
                    System.out.println("Introduce el id: ");
                    id = sc.nextInt();
                    imprimirLibro(id);
                    break;
                case 4:
                    System.out.println("Título: ");
                    String titulo = sc.nextLine();
                    System.out.println("Autor: ");
                    String autor = sc.nextLine();
                    System.out.println("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.println("Editorial: ");
                    String editorial = sc.nextLine();
                    System.out.println("Páginas: ");
                    int paginas = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Sinopsis: ");
                    String sinopsis = sc.nextLine();
                    System.out.println("Estado: ");
                    EstadoLectura estado = EstadoLectura.valueOf(sc.nextLine().toUpperCase());

                    Libro libro = new Libro (titulo, autor, isbn, editorial, paginas, sinopsis, estado);
                    añadirLibro(libro);
                    break;
                case 5: 
                    System.out.println("Introduce el id: ");
                    id = sc.nextInt();
                    modificarLibro(id);
                    break;
                case 6: 
                    System.out.println("Introduce el id: ");
                    id = sc.nextInt();
                    eliminarLibro(id);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    desconexion();
                    break;
                default: 
                    System.out.println("Opción no válida.");

            }

        } while (opcion != 0);
    }

}
