public class TestMisionEstado {
    static class RetoContador extends Reto {
        int evaluaciones = 0;

        RetoContador() {
            super("Prueba", NivelDificultad.FACIL);
        }

        @Override
        public boolean evaluarRespuesta(String respuesta) {
            evaluaciones++;
            return respuesta.equals("correcta");
        }

        @Override
        public void generarProblema() { }
    }

    static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    static int errores(Mision mision) {
        try {
            java.lang.reflect.Field campo = Mision.class.getDeclaredField("errores");
            campo.setAccessible(true);
            return campo.getInt(mision);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    static void testActiva() {
        RetoContador reto = new RetoContador();
        Mision mision = new Mision("1", reto);
        comprobar(!mision.procesarIntento("mal"), "Error inicial");
        comprobar(reto.evaluaciones == 1 && errores(mision) == 1 && mision.getEstado().equals("activa"), "Estado activo");
        comprobar(mision.procesarIntento("correcta"), "Respuesta correcta");
        comprobar(reto.evaluaciones == 2 && errores(mision) == 1 && mision.getEstado().equals("completada"), "Mision completada");
    }

    static void testCompletada() {
        RetoContador reto = new RetoContador();
        Mision mision = new Mision("2", reto);
        comprobar(mision.procesarIntento("correcta"), "Completar mision");
        comprobar(!mision.procesarIntento("mal"), "Rechazar error posterior");
        comprobar(!mision.procesarIntento("correcta"), "Rechazar acierto posterior");
        comprobar(reto.evaluaciones == 1 && errores(mision) == 0 && mision.getEstado().equals("completada"), "No volver a evaluar");
    }

    static void testBloqueada() {
        RetoContador reto = new RetoContador();
        Mision mision = new Mision("3", reto);
        for (int i = 0; i < 3; i++) comprobar(!mision.procesarIntento("mal"), "Error esperado");
        comprobar(mision.getEstado().equals("bloqueada"), "Bloquear despues de 3 errores");
        comprobar(!mision.procesarIntento("correcta"), "Rechazar cuarto intento");
        comprobar(!mision.procesarIntento("mal"), "Rechazar quinto intento");
        comprobar(reto.evaluaciones == 3 && errores(mision) == 3 && mision.getEstado().equals("bloqueada"), "No volver a evaluar");
    }

    static void testOtroEstado() {
        RetoContador reto = new RetoContador();
        Mision mision = new Mision("4", reto);
        mision.completar();
        comprobar(!mision.procesarIntento("correcta"), "Estado finalizado");
        comprobar(reto.evaluaciones == 0 && errores(mision) == 0 && mision.getEstado().equals("completada"), "Sin evaluaciones");
    }

    public static void main(String[] args) {
        testActiva();
        testCompletada();
        testBloqueada();
        testOtroEstado();
        System.out.println("OK: 4 casos de Mision");
    }
}
