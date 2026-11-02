from retos import Reto

class Mision:
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
        else:
            self.errores += 1
            if self.errores >= 3:
                self.estado = "bloqueada"
            return False

    def completar(self) -> None:
        self.estado = "completada"
