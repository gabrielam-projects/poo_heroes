from retos import Reto

class Mision:
    LIMITE_ERRORES = 3

    def __init__(self, id_mision: str, reto: Reto):
        self.id_mision = id_mision
        self.reto = reto
        self.estado = "activa"
        self.pistas = []
        self.errores = 0

    def procesar_intento(self, respuesta: str) -> bool:
        if self.estado != "activa":
            return False
        if self.reto.evaluar_respuesta(respuesta):
            self.completar()
            return True
        self.errores += 1
        if self.errores >= self.LIMITE_ERRORES:
            self.estado = "bloqueada"
        return False

    def consultar_intentos_restantes(self) -> int:
        if self.estado != "activa":
            return 0
        return max(0, self.LIMITE_ERRORES - self.errores)

    def completar(self) -> None:
        if self.estado == "activa":
            self.estado = "completada"
