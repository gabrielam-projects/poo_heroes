public class TestRetoSecuencia {
    static void revisar(boolean condicion) {
        if (!condicion) throw new AssertionError("Prueba fallida");
    }

    static void probarNivel(NivelDificultad nivel, String secuencia, String respuesta) {
        RetoSecuencia reto = new RetoSecuencia(nivel);
        revisar(reto.mostrarSecuencia().equals(secuencia));
        revisar(reto.evaluarRespuesta(respuesta));
        revisar(!reto.evaluarRespuesta("999"));
        revisar(!reto.evaluarRespuesta(""));
        revisar(!reto.evaluarRespuesta("hola"));
        revisar(!reto.evaluarRespuesta("10.0"));
        revisar(!reto.evaluarRespuesta(null));
        reto.generarProblema();
        revisar(reto.mostrarSecuencia().equals(secuencia));
        revisar(reto.evaluarRespuesta(respuesta));
    }

    public static void main(String[] args) {
        probarNivel(NivelDificultad.FACIL, "2, 4, 6, 8, ?", "10");
        probarNivel(NivelDificultad.MEDIO, "3, 6, 12, 24, ?", "48");
        probarNivel(NivelDificultad.DIFICIL, "2, 3, 5, 8, ?", "13");

        RetoSecuencia reto = new RetoSecuencia(NivelDificultad.FACIL);
        reto.aumentarDificultad();
        reto.generarProblema();
        revisar(reto.mostrarSecuencia().equals("3, 6, 12, 24, ?"));

        System.out.println("Pruebas correctas");
    }
}
