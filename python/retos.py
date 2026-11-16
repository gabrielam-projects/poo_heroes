import re
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
        self.secuencia_incompleta = []
        self.respuesta_correcta = 0

        self.generar_problema()

    def generar_problema(self) -> None:

        if self.dificultad == NivelDificultad.FACIL:

            self.patron_numerico = [2, 4, 6, 8, 10]
            self.respuesta_correcta = 6

        elif self.dificultad == NivelDificultad.MEDIO:

            self.patron_numerico = [3, 6, 9, 12, 15]
            self.respuesta_correcta = 9

        elif self.dificultad == NivelDificultad.DIFICIL:

            self.patron_numerico = [2, 6, 12, 20, 30]
            self.respuesta_correcta = 20

        else:
            raise ValueError("Nivel de dificultad no válido")

        self.secuencia_incompleta = self.patron_numerico.copy()

        if self.dificultad == NivelDificultad.DIFICIL:
            self.secuencia_incompleta[3] = None
        else:
            self.secuencia_incompleta[2] = None

    def evaluar_respuesta(self, respuesta: str) -> bool:

        if not isinstance(respuesta, str):
            return False

        respuesta = respuesta.strip()

        if not respuesta:
            return False

        if not re.fullmatch(r"[+-]?[0-9]+", respuesta):
            return False

        try:
            numero = int(respuesta)

            if numero < -2147483648 or numero > 2147483647:
                return False

            return numero == self.respuesta_correcta

        except ValueError:
            return False

    def obtener_secuencia_incompleta(self) -> list:
        return self.secuencia_incompleta.copy()
