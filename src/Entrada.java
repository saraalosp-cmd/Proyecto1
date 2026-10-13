import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Entrada {
    
    private final Scanner sc;

    public Entrada (Scanner sc) {
        this.sc = sc;
    }
    
    public String validarIsbn () {
        while(true) {
            System.out.println("ISBN (13 dígitos): ");
            String valor = sc.nextLine().replaceAll("[\\s]", "");
            if (valor.matches("\\d{13}")) {
                return valor;
            }
            System.out.println("ISBN no válido, debe tener 13 dígitos.");
        }
    }

    public int validarEnteros (String numero) {
        while(true) {
            System.out.println(numero + ": ");
            String valor = sc.nextLine().trim();
            if (valor.matches("-?\\d+")) {
                return Integer.parseInt(valor);
            }
            System.out.println("Introduce un número entero válido.");
        }
    }

    public EstadoLectura validarEstado() {
        while (true) {
            System.out.println("Estado (lo tengo, leyendo, leido, deseado): ");
            String valor = sc.nextLine().trim().toLowerCase().replace("í", "i");
            if (valor.matches("lo tengo | leyendo | leido | deseado")) {
                return EstadoLectura.desdeDB(valor);
            }
            System.out.println("Estado no válido.");
        }
    }

    public LocalDate validarFecha(String dato) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while(true) {
            System.out.println(dato + "(dd-mm-aaaa): ");
            String valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                return null;
            }

            try {
                return LocalDate.parse(valor, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida.");
            }
        }
    }
}
