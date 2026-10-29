public class TestMisionIntentos {
    static class RetoPrueba extends Reto {
        RetoPrueba() {
            super("Prueba", NivelDificultad.FACIL);
        }

        public boolean evaluarRespuesta(String respuesta) {
            return respuesta.equals("42");
        }

        public void generarProblema() { }
    }

    public static void main(String[] args) {
        Batman batman = new Batman();
        Mision mision = new Mision("1", new RetoPrueba());

        if (mision.consultarIntentosRestantes() != 3)
            throw new AssertionError("Debe iniciar con 3 intentos");

        for (int restantes = 2; restantes >= 0; restantes--) {
            batman.resolverMision(mision, "mal");
            if (mision.consultarIntentosRestantes() != restantes)
                throw new AssertionError("Intentos incorrectos");
        }

        if (!mision.getEstado().equals("bloqueada"))
            throw new AssertionError("La misión debe bloquearse");

        if (batman.resolverMision(mision, "42"))
            throw new AssertionError("No debe aceptar otro intento");

        Mision otra = new Mision("2", new RetoPrueba());
        if (!batman.resolverMision(otra, "42"))
            throw new AssertionError("Debe aceptar la respuesta correcta");

        System.out.println("Pruebas correctas");
    }
}
