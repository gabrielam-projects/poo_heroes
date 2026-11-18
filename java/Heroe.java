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

    public boolean acreditarRecompensa(Mision mision) {
        if (mision == null || !mision.marcarRecompensaAcreditada()) return false;
        saldo += mision.getRecompensa();
        return true;
    }

    public void restarIntento() {
        if (intentosRestantes > 0) {
            intentosRestantes--;
        }
    }
}
