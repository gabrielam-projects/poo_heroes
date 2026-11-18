from retos import Reto

class Mision:
    LIMITE_ERRORES = 3
    RECOMPENSA_POR_DEFECTO = 10

    def __init__(self, id_mision: str, reto: Reto, recompensa: int = RECOMPENSA_POR_DEFECTO):
        if recompensa <= 0:
            raise ValueError("La recompensa debe ser mayor que cero")
        self.id_mision = id_mision
        self.reto = reto
        self.estado = "activa"
        self.pistas = []
        self.errores = 0
        self.recompensa = recompensa
        self.recompensa_acreditada = False

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

    def consultar_estado(self) -> str:
        return self.estado

    def completar(self) -> None:
        if self.estado == "activa":
            self.estado = "completada"

    def marcar_recompensa_acreditada(self) -> bool:
        # True solo la primera vez y solo si la misión está completada
        if self.estado != "completada" or self.recompensa_acreditada:
            return False
        self.recompensa_acreditada = True
        return True
