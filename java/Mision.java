import java.util.ArrayList;
import java.util.List;

public class Mision {
    private static final int LIMITE_ERRORES = 3;
    private String id;
    private String estado;
    private Reto reto;
    private List<String> pistas;
    private int errores;

    public Mision(String id, Reto reto) {
        this.id = id;
        this.reto = reto;
        this.estado = "activa";
        this.pistas = new ArrayList<>();
        this.errores = 0;
    }

    public boolean procesarIntento(String respuesta) {
        if (!"activa".equals(estado)) {
            return false;
        }
        if (reto.evaluarRespuesta(respuesta)) {
            completar();
            return true;
        }
        errores++;
        if (errores >= LIMITE_ERRORES) {
            estado = "bloqueada";
        }
        return false;
    }

    public int consultarIntentosRestantes() {
        if (!"activa".equals(estado)) {
            return 0;
        }
        return Math.max(0, LIMITE_ERRORES - errores);
    }

    public void completar() {
        if ("activa".equals(estado)) {
            estado = "completada";
        }
    }

    public String getEstado() { return estado; }
    public Reto getReto() { return reto; }
}
