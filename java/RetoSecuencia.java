import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RetoSecuencia extends Reto {

    private List<Integer> patronNumerico;
    private List<Integer> secuenciaIncompleta;
    private int respuestaCorrecta;

    public RetoSecuencia(NivelDificultad dificultad) {
        super("Completar la secuencia numérica", dificultad);

        patronNumerico = new ArrayList<>();
        secuenciaIncompleta = new ArrayList<>();

        generarProblema();
    }

    @Override
    public void generarProblema() {

        if (dificultad == NivelDificultad.FACIL) {

            patronNumerico = new ArrayList<>(
                Arrays.asList(2, 4, 6, 8, 10)
            );

            respuestaCorrecta = 6;

        } else if (dificultad == NivelDificultad.MEDIO) {

            patronNumerico = new ArrayList<>(
                Arrays.asList(3, 6, 9, 12, 15)
            );

            respuestaCorrecta = 9;

        } else if (dificultad == NivelDificultad.DIFICIL) {

            patronNumerico = new ArrayList<>(
                Arrays.asList(2, 6, 12, 20, 30)
            );

            respuestaCorrecta = 20;

        } else {
            throw new IllegalStateException("Nivel de dificultad no válido");
        }

        secuenciaIncompleta = new ArrayList<>(patronNumerico);

        if (dificultad == NivelDificultad.DIFICIL) {
            secuenciaIncompleta.set(3, null);
        } else {
            secuenciaIncompleta.set(2, null);
        }
    }

    @Override
    public boolean evaluarRespuesta(String respuesta) {

        if (respuesta == null || respuesta.trim().isEmpty()) {
            return false;
        }

        try {
            String texto = respuesta.trim();

            if (!texto.matches("[+-]?[0-9]+")) {
                return false;
            }

            int numero = Integer.parseInt(texto);

            return numero == respuestaCorrecta;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Integer> getSecuenciaIncompleta() {
        return Collections.unmodifiableList(
            new ArrayList<>(secuenciaIncompleta)
        );
    }
}