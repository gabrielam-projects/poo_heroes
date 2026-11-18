from typing import List
from mision import Mision
from personajes import Heroe

class Partida:
    def __init__(self):
        self.misiones: List[Mision] = []
        self.recompensas_globales = 0

    def agregar_mision(self, mision: Mision) -> None:
        self.misiones.append(mision)

    def acreditar_recompensa(self, heroe: Heroe, mision: Mision) -> bool:
        if heroe is None or mision is None or mision not in self.misiones:
            return False
        if not heroe.acreditar_recompensa(mision):
            return False
        self.recompensas_globales += mision.recompensa
        return True

    def iniciar_juego(self) -> None:
        # TODO: Implementar inicio del juego
        pass
