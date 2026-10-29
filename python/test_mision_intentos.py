import unittest
from mision import Mision
from personajes import Batman, Heroe

class RetoPrueba:
    def __init__(self, correcta):
        self.correcta = correcta
        self.evaluaciones = 0

    def evaluar_respuesta(self, respuesta):
        self.evaluaciones += 1
        return respuesta == self.correcta

class TestMisionIntentos(unittest.TestCase):
    def test_errores_y_bloqueo(self):
        m = Mision("m1", RetoPrueba("42"))
        self.assertEqual((m.errores, m.consultar_intentos_restantes()), (0, 3))
        for numero in range(1, 4):
            self.assertFalse(m.procesar_intento("mal"))
            self.assertEqual((m.errores, m.consultar_intentos_restantes()), (numero, 3 - numero))
        self.assertEqual(m.estado, "bloqueada")

    def test_cuarto_intento_rechazado(self):
        reto = RetoPrueba("42")
        m = Mision("m1", reto)
        for _ in range(3):
            m.procesar_intento("mal")
        self.assertFalse(m.procesar_intento("42"))
        self.assertEqual(reto.evaluaciones, 3)
        m.completar()
        self.assertEqual(m.estado, "bloqueada")

    def test_completada_no_reprocesa(self):
        reto = RetoPrueba("42")
        m = Mision("m1", reto)
        self.assertTrue(m.procesar_intento("42"))
        self.assertEqual(m.consultar_intentos_restantes(), 0)
        self.assertFalse(m.procesar_intento("no"))
        self.assertEqual((m.estado, m.errores, reto.evaluaciones), ("completada", 0, 1))

    def test_independencia(self):
        a, b = Mision("a", RetoPrueba("1")), Mision("b", RetoPrueba("2"))
        a.procesar_intento("mal")
        self.assertEqual((a.consultar_intentos_restantes(), b.consultar_intentos_restantes()), (2, 3))

    def test_heroe_sin_contadores(self):
        batman = Batman()
        self.assertFalse(hasattr(batman, "errores"))
        self.assertFalse(hasattr(batman, "intentos_restantes"))
        self.assertFalse(hasattr(batman, "restar_intento"))
        self.assertEqual(batman.consultar_saldo(), 100)
        self.assertTrue(batman.pagar(25))
        self.assertEqual(batman.consultar_saldo(), 75)
        with self.assertRaises(TypeError):
            Heroe("generico")

if __name__ == "__main__":
    unittest.main()
