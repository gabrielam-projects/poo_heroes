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
class Ayudante(Personaje, ABC):
    def __init__(self, nombre: str, costo_pista: int):
        super().__init__(nombre)
        self._costo_pista = costo_pista 

    @abstractmethod
    def dar_pista(self, m: Mision) -> str:
        """Método abstracto que debe ser implementado por las subclases"""
        pass

class Alfred(Ayudante):
    def __init__(self, pistas: list):
        super().__init__("Alfred", costo_pista=15) 
        self.__pistas = pistas 

    def obtener_pista(self, i: int) -> str:
        if 0 <= i < len(self.__pistas):
            return self.__pistas[i].getTexto() 
        return "Pista no disponible."

    def dar_pista(self, m: Mision) -> str:
        if self.__pistas:
            return f"Tengo una sugerencia para la misión '{m.id}': {self.obtener_pista(0)}"
        return "Lo siento, amo Bruce, no tengo pistas para esta misión."
