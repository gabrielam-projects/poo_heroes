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

    public boolean acreditarRecompensa(Heroe heroe, Mision mision) {
        if (heroe == null || mision == null || !misiones.contains(mision)) return false;
        if (!heroe.acreditarRecompensa(mision)) return false;
        recompensasGlobales += mision.getRecompensa();
        return true;
    }

    public int getRecompensasGlobales() { return recompensasGlobales; }

    public void iniciarJuego() {
        // TODO: Implementar inicio del juego
    }
}
