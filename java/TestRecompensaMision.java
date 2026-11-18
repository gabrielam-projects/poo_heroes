public class TestRecompensaMision {
    static class RetoPrueba extends Reto {
        RetoPrueba() { super("Prueba", NivelDificultad.FACIL); }
        public boolean evaluarRespuesta(String respuesta) { return "bien".equals(respuesta); }
        public void generarProblema() { }
    }

    static void comprobar(boolean ok, String caso) {
        if (!ok) throw new AssertionError(caso);
    }

    public static void main(String[] args) {
        Partida partida = new Partida();
        Batman batman = new Batman();
        Batman otro = new Batman();
        Mision primera = new Mision("1", new RetoPrueba(), 30);
        partida.agregarMision(primera);
        comprobar(!partida.acreditarRecompensa(batman, primera), "activa");
        comprobar(primera.procesarIntento("bien"), "completar");
        comprobar(partida.acreditarRecompensa(batman, primera), "primera acreditacion");
        comprobar(batman.consultarSaldo() == 130, "saldo correcto");
        comprobar(partida.getRecompensasGlobales() == 30, "total correcto");
        comprobar(primera.isRecompensaAcreditada(), "marca de pago");
        comprobar(!partida.acreditarRecompensa(batman, primera), "no repetir");
        comprobar(!partida.acreditarRecompensa(otro, primera), "otro heroe");
        comprobar(otro.consultarSaldo() == 100, "sin dinero para otro");
        comprobar(!batman.acreditarRecompensa(primera), "pago directo repetido");
        comprobar(batman.consultarSaldo() == 130 && partida.getRecompensasGlobales() == 30, "sin duplicados");

        Mision segunda = new Mision("2", new RetoPrueba(), 50);
        partida.agregarMision(segunda);
        comprobar(segunda.procesarIntento("bien"), "segunda completada");
        comprobar(partida.acreditarRecompensa(batman, segunda), "segunda acreditada");
        comprobar(batman.consultarSaldo() == 180 && partida.getRecompensasGlobales() == 80, "dos misiones");

        Mision bloqueada = new Mision("3", new RetoPrueba(), 20);
        partida.agregarMision(bloqueada);
        for (int i = 0; i < 3; i++) bloqueada.procesarIntento("mal");
        comprobar(!partida.acreditarRecompensa(batman, bloqueada), "bloqueada");
        comprobar(batman.consultarSaldo() == 180 && partida.getRecompensasGlobales() == 80, "total estable");

        Mision ajena = new Mision("4", new RetoPrueba(), 10);
        ajena.completar();
        comprobar(!partida.acreditarRecompensa(batman, ajena), "mision ajena");
        comprobar(!ajena.isRecompensaAcreditada(), "ajena sin cobrar");
        comprobar(new Mision("5", new RetoPrueba()).getRecompensa() == 10, "recompensa predeterminada");
        boolean invalida = false;
        try { new Mision("6", new RetoPrueba(), 0); }
        catch (IllegalArgumentException e) { invalida = true; }
        comprobar(invalida, "recompensa cero");
        System.out.println("Pruebas de recompensas correctas");
    }
}
