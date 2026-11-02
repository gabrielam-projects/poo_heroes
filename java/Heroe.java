public abstract class Heroe extends Personaje {
    protected int saldo;

    public Heroe(String nombre, int saldo) {
        super(nombre);
        this.saldo = saldo;
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
}