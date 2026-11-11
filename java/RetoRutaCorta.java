import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RetoRutaCorta extends Reto {
    private Map<String, Map<String, Integer>> grafo = new HashMap<>();
    private String origen;
    private String destino;

    public RetoRutaCorta() {
        this("A", "D");
        generarProblema();
    }

    public RetoRutaCorta(String origen, String destino) {
        super("Ruta corta", NivelDificultad.FACIL);
        this.origen = origen;
        this.destino = destino;
    }

    public void agregarTramo(String a, String b, int costo) {
        if (costo <= 0 || a == null || b == null || a.equals(b)
                || a.contains("-") || b.contains("-")) {
            throw new IllegalArgumentException("Tramo incorrecto");
        }
        grafo.computeIfAbsent(a, k -> new HashMap<>()).put(b, costo);
        grafo.computeIfAbsent(b, k -> new HashMap<>()).put(a, costo);
    }

    @Override
    public void generarProblema() {
        grafo.clear();
        agregarTramo("A", "B", 2);
        agregarTramo("A", "C", 5);
        agregarTramo("B", "C", 1);
        agregarTramo("B", "D", 4);
        agregarTramo("C", "D", 2);
    }

    public long costoMinimo() {
        Map<String, Long> dist = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        for (String nodo : grafo.keySet()) dist.put(nodo, Long.MAX_VALUE);
        if (!dist.containsKey(origen) || !dist.containsKey(destino)) return Long.MAX_VALUE;
        dist.put(origen, 0L);

        while (true) {
            String actual = null;
            for (String nodo : dist.keySet()) {
                if (!visitados.contains(nodo) &&
                        (actual == null || dist.get(nodo) < dist.get(actual))) {
                    actual = nodo;
                }
            }
            if (actual == null || dist.get(actual) == Long.MAX_VALUE) break;
            if (actual.equals(destino)) return dist.get(actual);

            visitados.add(actual);
            for (String vecino : grafo.get(actual).keySet()) {
                long nuevo = dist.get(actual) + grafo.get(actual).get(vecino);
                if (nuevo < dist.get(vecino)) dist.put(vecino, nuevo);
            }
        }
        return Long.MAX_VALUE;
    }

    @Override
    public boolean evaluarRespuesta(String respuesta) {
        if (respuesta == null) return false;
        String[] nodos = respuesta.split("-", -1);
        if (nodos.length < 2 || !nodos[0].trim().equals(origen)
                || !nodos[nodos.length - 1].trim().equals(destino)) return false;

        long costo = 0;
        for (int i = 0; i < nodos.length - 1; i++) {
            String a = nodos[i].trim();
            String b = nodos[i + 1].trim();
            if (!grafo.containsKey(a) || !grafo.get(a).containsKey(b)) return false;
            costo += grafo.get(a).get(b);
        }
        return costo == costoMinimo();
    }

    public boolean bloquearTramo(String a, String b) {
        if (!grafo.containsKey(a) || !grafo.get(a).containsKey(b)) return false;
        int costo = grafo.get(a).remove(b);
        grafo.get(b).remove(a);
        if (costoMinimo() == Long.MAX_VALUE) {
            agregarTramo(a, b, costo);
            return false;
        }
        return true;
    }
}
