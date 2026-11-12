from abc import ABC, abstractmethod
from nivel_dificultad import NivelDificultad

class Reto(ABC):
    def __init__(self, descripcion: str, dificultad: NivelDificultad):
        self.descripcion = descripcion
        self.dificultad = dificultad

    @abstractmethod
    def evaluar_respuesta(self, respuesta: str) -> bool:
        pass

    @abstractmethod
    def generar_problema(self) -> None:
        pass

    def aumentar_dificultad(self) -> None:
        if self.dificultad == NivelDificultad.FACIL:
            self.dificultad = NivelDificultad.MEDIO
        elif self.dificultad == NivelDificultad.MEDIO:
            self.dificultad = NivelDificultad.DIFICIL


class RetoSecuencia(Reto):
    def __init__(self, dificultad: NivelDificultad):
        super().__init__("Completar la secuencia numérica", dificultad)
        self.patron_numerico = []
        self.respuesta_esperada = 0
        self.generar_problema()

    def generar_problema(self) -> None:
        if self.dificultad == NivelDificultad.FACIL:
            self.patron_numerico = [2, 4, 6, 8]
            self.respuesta_esperada = 10
        elif self.dificultad == NivelDificultad.MEDIO:
            self.patron_numerico = [3, 6, 12, 24]
            self.respuesta_esperada = 48
        else:
            self.patron_numerico = [2, 3, 5, 8]
            self.respuesta_esperada = 13

    def mostrar_secuencia(self) -> str:
        return ", ".join(str(n) for n in self.patron_numerico) + ", ?"

    def evaluar_respuesta(self, respuesta: str) -> bool:
        if not isinstance(respuesta, str):
            return False
        texto = respuesta.strip()
        if not texto.isascii() or not texto.isdigit():
            return False
        try:
            return int(texto) == self.respuesta_esperada
        except ValueError:
            return False
