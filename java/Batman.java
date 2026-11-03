public class Batman extends Heroe {
    private int errores;

    public Batman() {
        super("Batman", 100, 3);
        this.errores = 0;
    }

    public boolean resolverMision(Mision m, String respuesta) {
        // TODO: Implementar resolución de misión
        return m.procesarIntento(respuesta);
    }
}
