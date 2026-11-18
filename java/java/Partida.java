import java.util.ArrayList;
import java.util.List;

public class Partida {
    private List<Mision> misiones;
    private int recompensasGlobales;

    public Partida() {
        this.misiones = new ArrayList<>();
        this.recompensasGlobales = 0;
    }

    public void agregarMision(Mision m) {
        misiones.add(m);
    }

    // el total solo aumenta si el héroe confirma la acreditación
    public boolean acreditarRecompensa(Heroe h, Mision m) {
        if (h == null || m == null || !misiones.contains(m)) {
            return false;
        }
        if (h.acreditarRecompensa(m)) {
            recompensasGlobales += m.getRecompensa();
            return true;
        }
        return false;
    }

    public int getRecompensasGlobales() {
        return recompensasGlobales;
    }

    public void iniciarJuego() {
        // TODO: Implementar inicio del juego
    }
}
