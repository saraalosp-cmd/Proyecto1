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
}
