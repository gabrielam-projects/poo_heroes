from abc import ABC, abstractmethod
from retos import Reto, RetoSecuencia
from mision import Mision
from typing import List

class Personaje(ABC):
    def __init__(self, nombre: str):
        self.nombre = nombre


class Heroe(Personaje, ABC):
    def __init__(self, nombre: str, saldo: int = 100):
        super().__init__(nombre)
        self.saldo = saldo

    @abstractmethod
    def consultar_saldo(self) -> int:
        return self.saldo

    def pagar(self, costo: int) -> bool:
        if self.saldo >= costo:
            self.saldo -= costo
            return True
        return False


class Batman(Heroe):
    def __init__(self):
        super().__init__("Batman", 100)


class Villano(Personaje, ABC):
    def __init__(self, nombre: str, tipo_reto_especialidad: str):
        super().__init__(nombre)
        self.tipo_reto_especialidad = tipo_reto_especialidad

    @abstractmethod
    def complicar_reto(self, reto: Reto) -> None:
        pass

    def plantear_mision(self, id_mision: str, reto: Reto) -> Mision:
        self.complicar_reto(reto)
        return Mision(id_mision, reto)


class Joker(Villano):
    def __init__(self):
        super().__init__("Joker", "RetoSecuencia")

    def complicar_reto(self, reto: Reto) -> None:
        if isinstance(reto, RetoSecuencia):
            reto.aumentar_dificultad()
            print("¡El Joker ha complicado la secuencia numérica!")


class GreenGoblin(Villano):
    def __init__(self):
        super().__init__("Green Goblin", "RetoRutaCorta")

    def complicar_reto(self, reto: Reto) -> None:
        # TODO: Complicar reto si es de tipo RetoRutaCorta
        pass


class Pista:
    def __init__(self, texto: str, precio: int):
        self.__texto: str = texto
        self.__precio: int = precio

    def getTexto(self) -> str:
        return self.__texto


class Ayudante(Personaje, ABC):
    def __init__(self, nombre: str, costo_pista: int):
        super().__init__(nombre)
        self._costo_pista = costo_pista

    @abstractmethod
    def dar_pista(self, m: Mision) -> str:
        pass


class Alfred(Ayudante):
    def __init__(self, nombre: str, costo_pista: int, pistas_iniciales: List[Pista] = None):
        super().__init__(nombre, costo_pista)
        self.__pistas: List[Pista] = pistas_iniciales if pistas_iniciales else []

    def dar_pista(self, m: Mision) -> str:
        if self.__pistas:
            return f"Dando pista para la misión: {self.obtener_pista(0)}"
        return "No quedan pistas disponibles."

    def obtener_pista(self, i: int) -> str:
        try:
            return self.__pistas[i].getTexto() 
        except IndexError:
            return "Índice de pista no válido."