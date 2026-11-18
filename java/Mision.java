import java.util.ArrayList;
import java.util.List;

public class Mision {
    private static final int LIMITE_ERRORES = 3;
    private String id;
    private String estado;
    private Reto reto;
    private List<String> pistas;
    private int errores;
    private int recompensa;
    private boolean recompensaAcreditada;

    public Mision(String id, Reto reto) {
        this(id, reto, 10);
    }

    public Mision(String id, Reto reto, int recompensa) {
        if (recompensa <= 0) throw new IllegalArgumentException("La recompensa debe ser positiva");
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

    public int getRecompensa() { return recompensa; }
    public boolean isRecompensaAcreditada() { return recompensaAcreditada; }

    public boolean marcarRecompensaAcreditada() {
        if (!"completada".equals(estado) || recompensaAcreditada) return false;
        recompensaAcreditada = true;
        return true;
    }

    public String consultarEstado() { return estado; }
    public String getEstado() { return estado; }
    public Reto getReto() { return reto; }
}
