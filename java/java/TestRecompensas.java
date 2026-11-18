public class TestRecompensas {
    static class RetoFijo extends Reto {
        RetoFijo() { super("Prueba", NivelDificultad.FACIL); }
        public boolean evaluarRespuesta(String respuesta) { return "42".equals(respuesta); }
        public void generarProblema() { }
    }

    static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    static Mision misionCompletada(Partida partida, String id, int recompensa) {
        Mision m = new Mision(id, new RetoFijo(), recompensa);
        partida.agregarMision(m);
        verificar(m.procesarIntento("42"), "completar " + id);
        return m;
    }

    public static void main(String[] args) {
        Partida partida = new Partida();
        Batman batman = new Batman();
        Mision m1 = misionCompletada(partida, "1", 30);
        verificar(batman.consultarSaldo() == 100, "saldo inicial");
        verificar(partida.acreditarRecompensa(batman, m1), "primera acreditacion");
        verificar(batman.consultarSaldo() == 130, "saldo aumenta con la recompensa");
        verificar(partida.getRecompensasGlobales() == 30, "total de la partida aumenta");
        verificar(m1.isRecompensaAcreditada(), "mision registra el pago");

        verificar(!partida.acreditarRecompensa(batman, m1), "segunda acreditacion rechazada");
        verificar(!batman.acreditarRecompensa(m1), "acreditar directo en el heroe tambien rechazado");
        verificar(batman.consultarSaldo() == 130, "saldo sin cambios");
        verificar(partida.getRecompensasGlobales() == 30, "total sin cambios");

        Batman otro = new Batman();
        verificar(!partida.acreditarRecompensa(otro, m1), "otro heroe no cobra la misma mision");
        verificar(otro.consultarSaldo() == 100, "saldo del otro heroe intacto");
        verificar(partida.getRecompensasGlobales() == 30, "total intacto con otro heroe");

        Partida p2 = new Partida();
        Batman b2 = new Batman();
        Mision activa = new Mision("2", new RetoFijo(), 20);
        p2.agregarMision(activa);
        verificar(!p2.acreditarRecompensa(b2, activa), "mision activa no acredita");
        verificar(b2.consultarSaldo() == 100 && p2.getRecompensasGlobales() == 0, "nada cambia con mision activa");

        Mision bloqueada = new Mision("3", new RetoFijo(), 20);
        p2.agregarMision(bloqueada);
        for (int i = 0; i < 3; i++) bloqueada.procesarIntento("mal");
        verificar("bloqueada".equals(bloqueada.getEstado()), "mision bloqueada");
        verificar(!p2.acreditarRecompensa(b2, bloqueada), "mision bloqueada no acredita");
        verificar(b2.consultarSaldo() == 100 && p2.getRecompensasGlobales() == 0, "nada cambia con mision bloqueada");

        verificar(activa.procesarIntento("42"), "completar mision activa");
        verificar(p2.acreditarRecompensa(b2, activa), "acredita tras completar");
        verificar(!p2.acreditarRecompensa(b2, activa), "no se repite");
        verificar(b2.consultarSaldo() == 120 && p2.getRecompensasGlobales() == 20, "totales tras acreditar");

        Partida p3 = new Partida();
        Batman b3 = new Batman();
        Mision a = misionCompletada(p3, "4", 30);
        Mision b = misionCompletada(p3, "5", 50);
        verificar(p3.acreditarRecompensa(b3, a), "acreditar primera");
        verificar(p3.acreditarRecompensa(b3, b), "acreditar segunda");
        verificar(b3.consultarSaldo() == 180, "saldo suma ambas");
        verificar(p3.getRecompensasGlobales() == 80, "total coincide con lo acreditado");
        verificar(!p3.acreditarRecompensa(b3, a) && !p3.acreditarRecompensa(b3, b), "sin duplicados");
        verificar(b3.consultarSaldo() == 180 && p3.getRecompensasGlobales() == 80, "totales estables");

        Partida p4 = new Partida();
        Batman b4 = new Batman();
        Mision ajena = new Mision("6", new RetoFijo(), 40);
        ajena.procesarIntento("42");
        verificar(!p4.acreditarRecompensa(b4, ajena), "mision ajena no acredita");
        verificar(!ajena.isRecompensaAcreditada(), "mision ajena sin marca de pago");
        verificar(b4.consultarSaldo() == 100 && p4.getRecompensasGlobales() == 0, "nada cambia con mision ajena");

        verificar(!p4.acreditarRecompensa(null, ajena) && !p4.acreditarRecompensa(b4, null), "nulos rechazados");
        verificar(!b4.acreditarRecompensa(null), "heroe rechaza mision nula");

        verificar(new Mision("7", new RetoFijo()).getRecompensa() == Mision.RECOMPENSA_POR_DEFECTO, "recompensa por defecto");
        boolean lanzo = false;
        try {
            new Mision("8", new RetoFijo(), 0);
        } catch (IllegalArgumentException e) {
            lanzo = true;
        }
        verificar(lanzo, "recompensa no positiva rechazada");

        System.out.println("OK: acreditar, repetir, antes de completar, dos misiones, otro heroe y mision ajena");
    }
}
