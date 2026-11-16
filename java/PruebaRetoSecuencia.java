import java.util.Arrays;

public class PruebaRetoSecuencia {

    public static void main(String[] args) {

        probarNivel(
            NivelDificultad.FACIL,
            Arrays.asList(2, 4, null, 8, 10),
            "6"
        );

        probarNivel(
            NivelDificultad.MEDIO,
            Arrays.asList(3, 6, null, 12, 15),
            "9"
        );

        probarNivel(
            NivelDificultad.DIFICIL,
            Arrays.asList(2, 6, 12, null, 30),
            "20"
        );

        probarRespuestasInvalidas();

        probarReproducibilidad();

        System.out.println("\nTodas las pruebas pasaron.");
    }

    public static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    public static void probarNivel(
        NivelDificultad nivel,
        java.util.List<Integer> secuencia,
        String respuesta
    ) {

        RetoSecuencia reto = new RetoSecuencia(nivel);

        comprobar(
            reto.getSecuenciaIncompleta().equals(secuencia),
            "Secuencia incorrecta en " + nivel
        );

        comprobar(
            reto.evaluarRespuesta(respuesta),
            "Respuesta correcta rechazada en " + nivel
        );

        System.out.println("Prueba superada: " + nivel);
    }

    public static void probarRespuestasInvalidas() {

        for (NivelDificultad nivel : NivelDificultad.values()) {

            RetoSecuencia reto = new RetoSecuencia(nivel);

            comprobar(!reto.evaluarRespuesta("0"), "Respuesta incorrecta");
            comprobar(!reto.evaluarRespuesta(""), "Respuesta vacía");
            comprobar(!reto.evaluarRespuesta(null), "Respuesta nula");
            comprobar(!reto.evaluarRespuesta("hola"), "Texto inválido");
            comprobar(!reto.evaluarRespuesta("6.5"), "Número decimal");
            comprobar(!reto.evaluarRespuesta("99999999999999999999"),
                "Número fuera de rango");
        }

        System.out.println("Pruebas de respuestas inválidas superadas.");
    }

    public static void probarReproducibilidad() {

        RetoSecuencia reto =
            new RetoSecuencia(NivelDificultad.MEDIO);

        java.util.List<Integer> primera =
            reto.getSecuenciaIncompleta();

        reto.generarProblema();

        java.util.List<Integer> segunda =
            reto.getSecuenciaIncompleta();

        comprobar(
            primera.equals(segunda),
            "La generación no es reproducible"
        );

        System.out.println("Prueba de reproducibilidad superada.");
    }
}
