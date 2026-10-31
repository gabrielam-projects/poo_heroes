public class Batman extends Heroe {

    public Batman(String nombre, int saldo) {
        super(nombre, saldo);
    }

    public void resolverMision(Mision m, String respuesta) {
        m.procesarIntento(respuesta);
    }
}