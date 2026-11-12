import java.util.Arrays;
import java.util.List;

public class RetoSecuencia extends Reto {
    private List<Integer> patronNumerico;
    private int respuestaEsperada;

    public RetoSecuencia(NivelDificultad dificultad) {
        super("Completar la secuencia numérica", dificultad);
        generarProblema();
    }

    @Override
    public void generarProblema() {
        if (dificultad == NivelDificultad.FACIL) {
            patronNumerico = Arrays.asList(2, 4, 6, 8);
            respuestaEsperada = 10;
        } else if (dificultad == NivelDificultad.MEDIO) {
            patronNumerico = Arrays.asList(3, 6, 12, 24);
            respuestaEsperada = 48;
        } else {
            patronNumerico = Arrays.asList(2, 3, 5, 8);
            respuestaEsperada = 13;
        }
    }

    public String mostrarSecuencia() {
        String texto = "";
        for (int numero : patronNumerico) {
            if (!texto.isEmpty()) texto += ", ";
            texto += numero;
        }
        return texto + ", ?";
    }

    @Override
    public boolean evaluarRespuesta(String respuesta) {
        if (respuesta == null) return false;
        String texto = respuesta.trim();
        if (!texto.matches("[0-9]+")) return false;

        try {
            return Integer.parseInt(texto) == respuestaEsperada;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
