import unittest
from mision import Mision
from personajes import Batman


class RetoPrueba:
    def evaluar_respuesta(self, respuesta):
        return respuesta == "42"


class TestMisionIntentos(unittest.TestCase):
    def test_bloqueo(self):
        mision = Mision("1", RetoPrueba())
        self.assertEqual(mision.consultar_intentos_restantes(), 3)

        for restantes in (2, 1, 0):
            self.assertFalse(mision.procesar_intento("mal"))
            self.assertEqual(mision.consultar_intentos_restantes(), restantes)

        self.assertEqual(mision.estado, "bloqueada")
        self.assertFalse(mision.procesar_intento("42"))

    def test_respuesta_correcta(self):
        mision = Mision("2", RetoPrueba())
        self.assertTrue(mision.procesar_intento("42"))
        self.assertEqual(mision.estado, "completada")

    def test_batman_sin_contadores(self):
        batman = Batman()
        self.assertFalse(hasattr(batman, "errores"))
        self.assertFalse(hasattr(batman, "intentos_restantes"))


if __name__ == "__main__":
    unittest.main()
