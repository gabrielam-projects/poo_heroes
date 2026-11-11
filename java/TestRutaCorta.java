public class TestRutaCorta {
    static void revisar(boolean valor) {
        if (!valor) throw new AssertionError("Prueba fallida");
    }

    public static void main(String[] args) {
        RetoRutaCorta reto = new RetoRutaCorta();
        revisar(reto.costoMinimo() == 5);
        revisar(reto.evaluarRespuesta("A-B-C-D"));
        revisar(!reto.evaluarRespuesta("A-B-D"));
        revisar(!reto.evaluarRespuesta("A-D"));
        revisar(!reto.evaluarRespuesta("A-X-D"));

        GreenGoblin goblin = new GreenGoblin();
        revisar(new Mision("1", reto).aplicarDificultad(goblin));
        revisar(reto.costoMinimo() == 6);
        revisar(reto.evaluarRespuesta("A-B-D"));

        RetoSecuencia otro = new RetoSecuencia(NivelDificultad.FACIL);
        revisar(!new Mision("2", otro).aplicarDificultad(goblin));
        revisar(otro.getDificultad() == NivelDificultad.FACIL);

        RetoRutaCorta r2 = new RetoRutaCorta();
        Mision terminada = new Mision("3", r2);
        terminada.completar();
        revisar(!terminada.aplicarDificultad(goblin));
        revisar(r2.costoMinimo() == 5);

        RetoRutaCorta r3 = new RetoRutaCorta();
        Mision bloqueada = new Mision("4", r3);
        for (int i = 0; i < 3; i++) bloqueada.procesarIntento("mal");
        revisar(!bloqueada.aplicarDificultad(goblin));
        revisar(r3.costoMinimo() == 5);

        RetoRutaCorta sinSalida = new RetoRutaCorta("A", "B");
        sinSalida.agregarTramo("A", "B", 3);
        revisar(!new Mision("5", sinSalida).aplicarDificultad(new GreenGoblin("A", "B")));
        revisar(sinSalida.evaluarRespuesta("A-B"));

        RetoRutaCorta creado = new RetoRutaCorta();
        new GreenGoblin().plantearMision("6", creado);
        revisar(creado.evaluarRespuesta("A-B-D"));

        System.out.println("Pruebas correctas");
    }
}
