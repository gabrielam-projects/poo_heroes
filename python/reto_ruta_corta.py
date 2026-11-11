from retos import Reto
from nivel_dificultad import NivelDificultad


class RetoRutaCorta(Reto):
    def __init__(self, origen="A", destino="D"):
        super().__init__("Ruta corta", NivelDificultad.FACIL)
        self.origen = origen
        self.destino = destino
        self.grafo = {}
        if origen == "A" and destino == "D":
            self.generar_problema()

    def agregar_tramo(self, a, b, costo):
        if costo <= 0 or not a or not b or a == b or "-" in a or "-" in b:
            raise ValueError("Tramo incorrecto")
        self.grafo.setdefault(a, {})[b] = costo
        self.grafo.setdefault(b, {})[a] = costo

    def generar_problema(self):
        self.grafo = {}
        self.agregar_tramo("A", "B", 2)
        self.agregar_tramo("A", "C", 5)
        self.agregar_tramo("B", "C", 1)
        self.agregar_tramo("B", "D", 4)
        self.agregar_tramo("C", "D", 2)

    def costo_minimo(self):
        dist = {nodo: float("inf") for nodo in self.grafo}
        if self.origen not in dist or self.destino not in dist:
            return float("inf")
        dist[self.origen] = 0
        visitados = set()

        while True:
            pendientes = [n for n in dist if n not in visitados]
            if not pendientes:
                break
            actual = min(pendientes, key=lambda n: dist[n])
            if dist[actual] == float("inf"):
                break
            if actual == self.destino:
                return dist[actual]

            visitados.add(actual)
            for vecino, costo in self.grafo[actual].items():
                dist[vecino] = min(dist[vecino], dist[actual] + costo)
        return float("inf")

    def evaluar_respuesta(self, respuesta):
        if not isinstance(respuesta, str):
            return False
        nodos = [n.strip() for n in respuesta.split("-")]
        if len(nodos) < 2 or nodos[0] != self.origen or nodos[-1] != self.destino:
            return False

        costo = 0
        for a, b in zip(nodos, nodos[1:]):
            if b not in self.grafo.get(a, {}):
                return False
            costo += self.grafo[a][b]
        return costo == self.costo_minimo()

    def bloquear_tramo(self, a, b):
        if b not in self.grafo.get(a, {}):
            return False
        costo = self.grafo[a].pop(b)
        self.grafo[b].pop(a)
        if self.costo_minimo() == float("inf"):
            self.agregar_tramo(a, b, costo)
            return False
        return True
