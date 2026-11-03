import unittest
from mision import Mision
from personajes import Batman


class RetoContador:
    def __init__(self, correcta="42"):
        self.correcta = correcta
        self.llamadas = 0

    def evaluar_respuesta(self, respuesta):
        self.llamadas += 1
        return respuesta == self.correcta


class TestMisionIntentos(unittest.TestCase):
    def test_bloqueo_y_cuarto_intento(self):
        reto = RetoContador()
        mision = Mision("1", reto)
        for restantes in (2, 1, 0):
            self.assertFalse(mision.procesar_intento("mal"))
            self.assertEqual(mision.consultar_intentos_restantes(), restantes)
        self.assertEqual((reto.llamadas, mision.errores, mision.consultar_estado()),
                         (3, 3, "bloqueada"))
        self.assertFalse(mision.procesar_intento("42"))
        self.assertEqual((reto.llamadas, mision.errores, mision.estado),
                         (3, 3, "bloqueada"))

    def test_completada_no_se_evalua_otra_vez(self):
        reto = RetoContador()
        mision = Mision("2", reto)
        self.assertTrue(mision.procesar_intento("42"))
        self.assertFalse(mision.procesar_intento("mal"))
        self.assertEqual((reto.llamadas, mision.errores, mision.consultar_estado()),
                         (1, 0, "completada"))
        self.assertEqual(mision.consultar_intentos_restantes(), 0)

    def test_dos_misiones_independientes(self):
        reto1, reto2 = RetoContador(), RetoContador()
        mision1 = Mision("3", reto1)
        mision2 = Mision("4", reto2)
        self.assertFalse(mision1.procesar_intento("mal"))
        self.assertEqual(mision1.consultar_intentos_restantes(), 2)
        self.assertEqual(mision2.consultar_intentos_restantes(), 3)
        self.assertTrue(mision2.procesar_intento("42"))
        self.assertEqual((mision1.errores, mision1.estado, reto1.llamadas), (1, "activa", 1))
        self.assertEqual((mision2.errores, mision2.estado, reto2.llamadas), (0, "completada", 1))

    def test_batman_conserva_metodos_sin_contadores(self):
        batman = Batman()
        self.assertEqual(batman.consultar_saldo(), 100)
        self.assertFalse(hasattr(batman, "errores"))
        self.assertFalse(hasattr(batman, "intentos_restantes"))
        mision = Mision("5", RetoContador())
        self.assertTrue(batman.resolver_mision(mision, "42"))
        self.assertEqual(mision.consultar_estado(), "completada")


if __name__ == "__main__":
    unittest.main()
