from abc import ABC, abstractmethod
from retos import Reto, RetoSecuencia
from mision import Mision

class Personaje(ABC):
    def __init__(self, nombre: str):
        self.nombre = nombre


class Heroe(Personaje,ABC):
    def __init__(self, nombre: str, saldo: int = 100, intentos_restantes: int = 3):
        super().__init__(nombre)
        self.saldo = saldo
        self.intentos_restantes = intentos_restantes

    def consultar_saldo(self) -> int:
        return self.saldo

    def pagar(self, costo: int) -> bool:
        if self.saldo >= costo:
            self.saldo -= costo
            return True
        return False


class Batman(Heroe):
    def __init__(self):
        super().__init__("Batman", 100, 3)
        self.errores = 0


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
