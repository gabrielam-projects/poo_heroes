public abstract class Heroe extends Personaje {
    protected int saldo;
    protected int intentosRestantes;

    public Heroe(String nombre, int saldo, int intentosRestantes) {
        super(nombre);
        this.saldo = saldo;
        this.intentosRestantes = intentosRestantes;
    }

    public int consultarSaldo() {
        return saldo;
    }

    public boolean pagar(int costo) {
        if (saldo >= costo) {
            saldo -= costo;
            return true;
        }
        return false;
    }

    // true solo en la primera acreditación de una misión completada
    public boolean acreditarRecompensa(Mision m) {
        if (m == null || !m.marcarRecompensaAcreditada()) {
            return false;
        }
        saldo += m.getRecompensa();
        return true;
    }

    public void restarIntento() {
        if (intentosRestantes > 0) {
            intentosRestantes--;
        }
    }
}
