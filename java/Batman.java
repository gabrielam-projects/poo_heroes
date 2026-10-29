public class Batman extends Heroe {
    public Batman() {
        super("Batman", 100);
    }

    public boolean resolverMision(Mision m, String respuesta) {
        // TODO: Implementar resolución de misión
        return m.procesarIntento(respuesta);
    }
}
