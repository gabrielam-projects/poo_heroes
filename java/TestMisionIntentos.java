public class TestMisionIntentos {
    private static class RetoPrueba extends Reto {
        private final String correcta;
        int evaluaciones = 0;
        RetoPrueba(String correcta) {
            super("Prueba", NivelDificultad.FACIL);
            this.correcta = correcta;
        }
        @Override public boolean evaluarRespuesta(String respuesta) {
            evaluaciones++;
            return correcta.equals(respuesta);
        }
        @Override public void generarProblema() {}
    }
    private static void verificar(boolean ok, String mensaje) {
        if (!ok) throw new AssertionError(mensaje);
    }
    public static void main(String[] args) {
        Batman batman = new Batman();
        verificar(batman.consultarSaldo() == 100, "saldo inicial");
        verificar(java.util.Arrays.stream(Batman.class.getDeclaredFields()).noneMatch(f -> f.getName().equals("errores")), "Batman sin errores");
        verificar(java.util.Arrays.stream(Heroe.class.getDeclaredFields()).noneMatch(f -> f.getName().equals("intentosRestantes")), "Heroe sin intentosRestantes");
        verificar(java.util.Arrays.stream(Heroe.class.getDeclaredMethods()).noneMatch(m -> m.getName().equals("restarIntento")), "sin restarIntento");
        RetoPrueba r = new RetoPrueba("42");
        Mision m = new Mision("m1", r);
        verificar(m.consultarIntentosRestantes() == 3, "inicial");
        for (int i = 1; i <= 3; i++) {
            verificar(!batman.resolverMision(m, "mal"), "respuesta incorrecta");
            verificar(m.consultarIntentosRestantes() == 3 - i, "intentos restantes");
        }
        verificar("bloqueada".equals(m.getEstado()), "bloqueo");
        verificar(!batman.resolverMision(m, "42") && r.evaluaciones == 3, "cuarto intento no evaluado");
        m.completar();
        verificar("bloqueada".equals(m.getEstado()), "bloqueo irreversible");
        RetoPrueba otra = new RetoPrueba("42");
        Mision completada = new Mision("m2", otra);
        verificar(batman.resolverMision(completada, "42"), "resuelta");
        verificar(completada.consultarIntentosRestantes() == 0, "completada sin intentos");
        verificar(!batman.resolverMision(completada, "mal") && otra.evaluaciones == 1, "no reprocesar completada");
        Mision independiente = new Mision("m3", new RetoPrueba("42"));
        verificar(independiente.consultarIntentosRestantes() == 3, "independiente");
        System.out.println("Pruebas Java Issue #8: OK");
    }
}
