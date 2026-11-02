import unittest
from mision import Mision
from retos import Reto
from nivel_dificultad import NivelDificultad


class RetoContador(Reto):
    def __init__(self):
        super().__init__("Prueba", NivelDificultad.FACIL)
        self.evaluaciones = 0

    def evaluar_respuesta(self, respuesta: str) -> bool:
        self.evaluaciones += 1
        return respuesta == "correcta"

    def generar_problema(self) -> None:
        pass


class TestMisionEstado(unittest.TestCase):
    def test_activa_procesa_respuestas(self):
        reto = RetoContador()
        mision = Mision("1", reto)
        self.assertFalse(mision.procesar_intento("mal"))
        self.assertEqual((reto.evaluaciones, mision.errores, mision.estado),
                         (1, 1, "activa"))
        self.assertTrue(mision.procesar_intento("correcta"))
        self.assertEqual((reto.evaluaciones, mision.errores, mision.estado),
                         (2, 1, "completada"))

    def test_completada_no_evalua_ni_cambia(self):
        reto = RetoContador()
        mision = Mision("2", reto)
        self.assertTrue(mision.procesar_intento("correcta"))
        self.assertFalse(mision.procesar_intento("mal"))
        self.assertFalse(mision.procesar_intento("correcta"))
        self.assertEqual((reto.evaluaciones, mision.errores, mision.estado),
                         (1, 0, "completada"))

    def test_bloqueada_no_evalua_ni_cambia(self):
        reto = RetoContador()
        mision = Mision("3", reto)
        for _ in range(3):
            self.assertFalse(mision.procesar_intento("mal"))
        self.assertFalse(mision.procesar_intento("correcta"))
        self.assertFalse(mision.procesar_intento("mal"))
        self.assertEqual((reto.evaluaciones, mision.errores, mision.estado),
                         (3, 3, "bloqueada"))

    def test_otro_estado_no_evalua_ni_cambia(self):
        reto = RetoContador()
        mision = Mision("4", reto)
        mision.estado = "pendiente"
        self.assertFalse(mision.procesar_intento("correcta"))
        self.assertEqual((reto.evaluaciones, mision.errores, mision.estado),
                         (0, 0, "pendiente"))


if __name__ == "__main__":
    unittest.main()
