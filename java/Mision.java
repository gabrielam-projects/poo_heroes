import java.util.ArrayList;
import java.util.List;

public class Mision {
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
        
        if (!this.estado.equals("activa")) {
            return false;
        }

        if (reto.evaluarRespuesta(respuesta)) {
            completar();
            return true;
        } else {
           
            this.errores++;
            if (this.errores >= 3) {
                this.estado = "bloqueada";
            }
            return false;
        }
    }

    public void completar() {
        this.estado = "completada";
    }

    public String getEstado() {
        return estado;
    }

    public Reto getReto() {
        return reto;
    }
}