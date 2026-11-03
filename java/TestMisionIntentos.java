public class TestMisionIntentos {
    static class RetoContador extends Reto {
        int llamadas = 0;
        RetoContador() { super("Prueba", NivelDificultad.FACIL); }
        public boolean evaluarRespuesta(String respuesta) {
            llamadas++;
            return "42".equals(respuesta);
        }
        public void generarProblema() { }
    }

    static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    public static void main(String[] args) {
        Batman batman = new Batman();
        RetoContador reto = new RetoContador();
        Mision bloqueada = new Mision("1", reto);
        for (int n = 2; n >= 0; n--) {
            verificar(!batman.resolverMision(bloqueada, "mal"), "fallo esperado");
            verificar(bloqueada.consultarIntentosRestantes() == n, "intentos restantes");
        }
        verificar("bloqueada".equals(bloqueada.consultarEstado()), "bloqueo");
        verificar(!batman.resolverMision(bloqueada, "42"), "cuarto intento rechazado");
        verificar(reto.llamadas == 3, "no debe evaluar tras bloqueo");

        RetoContador reto2 = new RetoContador();
        Mision completada = new Mision("2", reto2);
        verificar(batman.resolverMision(completada, "42"), "completar");
        verificar(!batman.resolverMision(completada, "mal"), "rechazar completada");
        verificar(reto2.llamadas == 1 && "completada".equals(completada.getEstado()), "no evaluar tras completar");

        RetoContador reto3 = new RetoContador();
        RetoContador reto4 = new RetoContador();
        Mision primera = new Mision("3", reto3);
        Mision segunda = new Mision("4", reto4);
        verificar(!primera.procesarIntento("mal"), "primer intento");
        verificar(primera.consultarIntentosRestantes() == 2, "primera independiente");
        verificar(segunda.consultarIntentosRestantes() == 3, "segunda independiente");
        verificar(segunda.procesarIntento("42"), "segunda completada");
        verificar(primera.consultarIntentosRestantes() == 2 && reto3.llamadas == 1, "primera conservada");
        verificar(segunda.consultarIntentosRestantes() == 0 && reto4.llamadas == 1, "segunda completa");

        System.out.println("OK: bloqueo, completada y dos misiones independientes");
    }
}
