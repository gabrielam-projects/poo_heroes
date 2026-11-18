import unittest
from mision import Mision
from partida import Partida
from personajes import Batman


class RetoFijo:
    def evaluar_respuesta(self, respuesta):
        return respuesta == "42"


def mision_completada(partida, id_mision, recompensa):
    mision = Mision(id_mision, RetoFijo(), recompensa)
    partida.agregar_mision(mision)
    assert mision.procesar_intento("42")
    return mision


class TestRecompensas(unittest.TestCase):
    def test_completar_y_acreditar(self):
        partida, batman = Partida(), Batman()
        mision = mision_completada(partida, "1", 30)
        self.assertEqual(batman.consultar_saldo(), 100)
        self.assertTrue(partida.acreditar_recompensa(batman, mision))
        self.assertEqual(batman.consultar_saldo(), 130)
        self.assertEqual(partida.recompensas_globales, 30)
        self.assertTrue(mision.recompensa_acreditada)

    def test_repetir_acreditacion_no_incrementa(self):
        partida, batman = Partida(), Batman()
        mision = mision_completada(partida, "1", 30)
        self.assertTrue(partida.acreditar_recompensa(batman, mision))
        self.assertFalse(partida.acreditar_recompensa(batman, mision))
        self.assertFalse(batman.acreditar_recompensa(mision))
        self.assertEqual(batman.consultar_saldo(), 130)
        self.assertEqual(partida.recompensas_globales, 30)

    def test_otro_heroe_no_cobra_la_misma_mision(self):
        partida, batman, otro = Partida(), Batman(), Batman()
        mision = mision_completada(partida, "1", 30)
        self.assertTrue(partida.acreditar_recompensa(batman, mision))
        self.assertFalse(partida.acreditar_recompensa(otro, mision))
        self.assertEqual(otro.consultar_saldo(), 100)
        self.assertEqual(partida.recompensas_globales, 30)

    def test_no_acredita_antes_de_completar(self):
        partida, batman = Partida(), Batman()
        activa = Mision("2", RetoFijo(), 20)
        partida.agregar_mision(activa)
        self.assertFalse(partida.acreditar_recompensa(batman, activa))
        self.assertEqual((batman.consultar_saldo(), partida.recompensas_globales), (100, 0))

        bloqueada = Mision("3", RetoFijo(), 20)
        partida.agregar_mision(bloqueada)
        for _ in range(3):
            bloqueada.procesar_intento("mal")
        self.assertEqual(bloqueada.consultar_estado(), "bloqueada")
        self.assertFalse(partida.acreditar_recompensa(batman, bloqueada))
        self.assertEqual((batman.consultar_saldo(), partida.recompensas_globales), (100, 0))

        self.assertTrue(activa.procesar_intento("42"))
        self.assertTrue(partida.acreditar_recompensa(batman, activa))
        self.assertFalse(partida.acreditar_recompensa(batman, activa))
        self.assertEqual((batman.consultar_saldo(), partida.recompensas_globales), (120, 20))

    def test_dos_misiones_distintas(self):
        partida, batman = Partida(), Batman()
        a = mision_completada(partida, "4", 30)
        b = mision_completada(partida, "5", 50)
        self.assertTrue(partida.acreditar_recompensa(batman, a))
        self.assertTrue(partida.acreditar_recompensa(batman, b))
        self.assertEqual(batman.consultar_saldo(), 180)
        self.assertEqual(partida.recompensas_globales, 80)
        self.assertFalse(partida.acreditar_recompensa(batman, a))
        self.assertFalse(partida.acreditar_recompensa(batman, b))
        self.assertEqual((batman.consultar_saldo(), partida.recompensas_globales), (180, 80))

    def test_mision_ajena_a_la_partida(self):
        partida, batman = Partida(), Batman()
        ajena = Mision("6", RetoFijo(), 40)
        ajena.procesar_intento("42")
        self.assertFalse(partida.acreditar_recompensa(batman, ajena))
        self.assertFalse(ajena.recompensa_acreditada)
        self.assertEqual((batman.consultar_saldo(), partida.recompensas_globales), (100, 0))

    def test_valores_nulos(self):
        partida, batman = Partida(), Batman()
        mision = mision_completada(partida, "7", 10)
        self.assertFalse(partida.acreditar_recompensa(None, mision))
        self.assertFalse(partida.acreditar_recompensa(batman, None))
        self.assertFalse(batman.acreditar_recompensa(None))
        self.assertFalse(mision.recompensa_acreditada)

    def test_recompensa_definida_y_valida(self):
        self.assertEqual(Mision("8", RetoFijo()).recompensa, Mision.RECOMPENSA_POR_DEFECTO)
        with self.assertRaises(ValueError):
            Mision("9", RetoFijo(), 0)


if __name__ == "__main__":
    unittest.main()
