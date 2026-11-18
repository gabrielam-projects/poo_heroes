import java.util.ArrayList;
import java.util.List;

public class Mision {
    private static final int LIMITE_ERRORES = 3;
    public static final int RECOMPENSA_POR_DEFECTO = 10;
    private String id;
    private String estado;
    private Reto reto;
    private List<String> pistas;
    private int errores;
    private int recompensa;
    private boolean recompensaAcreditada;

    public Mision(String id, Reto reto) {
        this(id, reto, RECOMPENSA_POR_DEFECTO);
    }

    public Mision(String id, Reto reto, int recompensa) {
        if (recompensa <= 0) {
            throw new IllegalArgumentException("La recompensa debe ser mayor que cero");
        }
        this.id = id;
        this.reto = reto;
        this.estado = "activa";
        this.pistas = new ArrayList<>();
        this.errores = 0;
        this.recompensa = recompensa;
        this.recompensaAcreditada = false;
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

    // true solo la primera vez y solo si la misión está completada
    public boolean marcarRecompensaAcreditada() {
        if (!"completada".equals(estado) || recompensaAcreditada) {
            return false;
        }
        recompensaAcreditada = true;
        return true;
    }

    public int getRecompensa() { return recompensa; }
    public boolean isRecompensaAcreditada() { return recompensaAcreditada; }
    public String consultarEstado() { return estado; }
    public String getEstado() { return estado; }
    public Reto getReto() { return reto; }
}
