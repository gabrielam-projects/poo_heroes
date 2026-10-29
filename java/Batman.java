public class Batman extends Heroe {
    public Batman() {
        super("Batman", 100);
    }

    public boolean resolverMision(Mision m, String respuesta) {
        return m.procesarIntento(respuesta);
    }
}
