import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Entrada {
    
    private final Scanner sc;

    public Entrada (Scanner sc) {
        this.sc = sc;
    }
    
    /**
     * Pide el ISBN y valida que tenga 13 dígitos.
     * Se eliminan espacios y guiones.
     * 
     * @return el ISBN con 13 dígitos.
    */
    public String validarIsbn () {
        while(true) {
            System.out.println("ISBN (13 dígitos): ");
            String valor = sc.nextLine().replaceAll("[-\\s]", "");
            if (valor.matches("\\d{13}")) {
                return valor;
            }
            System.out.println("ISBN no válido, debe tener 13 dígitos.");
        }
    }

    /**
     * Pide un entero y repite la pregunta hasta que sea válido.
     * 
     * @param dato nombre del dato solicitado
     * @return el entero introducido cuando sea correcto.
     */
    public int validarEnteros (String dato) {
        while(true) {
            System.out.println(dato + ": ");
            String valor = sc.nextLine().trim();
            if (valor.matches("-?\\d+")) {
                return Integer.parseInt(valor);
            }
            System.out.println("Introduce un número entero válido.");
        }
    }

    /**
     * Pide un entero mostrando el valor actual.
     * Si se deja vacío, se mantiene el actual.
     * 
     * @param dato nombre del dato solicitado.
     * @param numero valor actual.
     * @return el entero introducido o el actual si se deja vacío.
     */
    public int validarEnteros (String dato, int numero) {
        while(true) {
            System.out.println(numero + " [" + numero + "]: ");
            String valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                return numero;
            }
            if (valor.matches("-?\\d+")) {
                return Integer.parseInt(valor);
            }
            System.out.println("Introduce un número entero válido.");
        }
    }

    /**
     * Pide el estado de lectura y valida que sea uno de los cuatro permitidos.
     * No distingue mayúsculas de minúsculas y acepta "leído".
     * 
     * @return el estado correspondiente.
     */
    public EstadoLectura validarEstado() {
        while (true) {
            System.out.println("Estado (lo tengo, leyendo, leido, deseado): ");
            String valor = sc.nextLine().trim().toLowerCase().replace("í", "i");
            if (valor.matches("lo tengo|leyendo|leido|deseado")) {
                return EstadoLectura.desdeDB(valor);
            }
            System.out.println("Estado no válido.");
        }
    }

    /**
     * Pide una fecha con formato dd-mm-aaaa y comprueba que sea correcto.
     * Si se deja vacío no se asigna ninguna fecha.
     * 
     * @param dato nombre del dato solicitado
     * @return fecha introducida si es correcta, o null si se deja vacío.
     */
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

    /**
     * Pide un texto, eliminando los espacios al principio y al final y dejando
     * la primera letra en mayúscula. 
     * 
     * @param dato nombre del dato solicitado.
     * @return texto formateado.
     */
    public String validarTexto(String dato) {
        while(true) {
            System.out.println(dato + ": ");
            String valor = sc.nextLine().trim();
            if (!valor.isEmpty()) {
                String[] palabras = valor.split("\\s+");
                String resultado = "";
                for (String palabra : palabras) {
                    resultado += palabra.substring(0,1).toUpperCase() + palabra.substring(1) + " ";
                }
                return resultado.trim();
            }
            System.out.println("Debes introducir un valor.");
        }
    }
}
