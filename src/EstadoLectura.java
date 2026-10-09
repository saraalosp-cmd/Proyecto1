public enum EstadoLectura {
    // valores
    LO_TENGO("lo tengo"),
    LEYENDO("leyendo"),
    LEIDO("leido"),
    DESEADO("deseado");

    // atributo
    private final String valorDB;

    // constructor
    EstadoLectura (String valorDB) {
        this.valorDB = valorDB;
    }

    // getter
    public String getValorDB() { return valorDB;}

    // valor leído de la DB
    public static EstadoLectura desdeDB (String valor) {
        for (EstadoLectura estado : values()) {
            if (estado.valorDB.equals(valor)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + valor);
    }
}
