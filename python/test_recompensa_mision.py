import unittest
from mision import Mision
from personajes import Batman
from partida import Partida

class RetoPrueba:
    def evaluar_respuesta(self, respuesta):
        return respuesta == "bien"

class TestRecompensas(unittest.TestCase):
    def test_completar_y_acreditar(self):
        partida, batman = Partida(), Batman()
        m = Mision("1", RetoPrueba(), 30)
        partida.agregar_mision(m)
        self.assertTrue(batman.resolver_mision(m, "bien"))
        self.assertTrue(partida.acreditar_recompensa(batman, m))
        self.assertTrue(m.recompensa_acreditada)
        self.assertEqual(batman.consultar_saldo(), 130)
        self.assertEqual(partida.recompensas_globales, 30)

    def test_sin_repeticiones(self):
        p, b, otro = Partida(), Batman(), Batman()
        m = Mision("2", RetoPrueba(), 40)
        p.agregar_mision(m)
        m.completar()
        self.assertTrue(p.acreditar_recompensa(b, m))
        self.assertFalse(p.acreditar_recompensa(b, m))
        self.assertFalse(p.acreditar_recompensa(otro, m))
        self.assertFalse(b.acreditar_recompensa(m))
        self.assertEqual((b.consultar_saldo(), otro.consultar_saldo(), p.recompensas_globales), (140, 100, 40))

    def test_antes_de_completar(self):
        p, b = Partida(), Batman()
        m = Mision("3", RetoPrueba(), 15)
        p.agregar_mision(m)
        self.assertFalse(p.acreditar_recompensa(b, m))
        self.assertEqual((b.consultar_saldo(), p.recompensas_globales), (100, 0))
        m.completar()
        self.assertTrue(p.acreditar_recompensa(b, m))
        self.assertEqual((b.consultar_saldo(), p.recompensas_globales), (115, 15))

    def test_mision_bloqueada(self):
        p, b = Partida(), Batman()
        m = Mision("4", RetoPrueba(), 25)
        p.agregar_mision(m)
        for _ in range(3):
            m.procesar_intento("mal")
        self.assertEqual(m.consultar_estado(), "bloqueada")
        self.assertFalse(p.acreditar_recompensa(b, m))
        self.assertEqual((b.consultar_saldo(), p.recompensas_globales), (100, 0))

    def test_dos_misiones(self):
        p, b = Partida(), Batman()
        a, c = Mision("5", RetoPrueba(), 20), Mision("6", RetoPrueba(), 50)
        p.agregar_mision(a)
        p.agregar_mision(c)
        a.completar()
        c.completar()
        self.assertTrue(p.acreditar_recompensa(b, a))
        self.assertTrue(p.acreditar_recompensa(b, c))
        self.assertFalse(p.acreditar_recompensa(b, a))
        self.assertEqual((b.consultar_saldo(), p.recompensas_globales), (170, 70))

    def test_mision_ajena(self):
        p, b = Partida(), Batman()
        ajena = Mision("7", RetoPrueba(), 30)
        ajena.completar()
        self.assertFalse(p.acreditar_recompensa(b, ajena))
        self.assertFalse(ajena.recompensa_acreditada)

    def test_recompensa_invalida(self):
        with self.assertRaises(ValueError):
            Mision("8", RetoPrueba(), 0)
        with self.assertRaises(ValueError):
            Mision("8", RetoPrueba(), -3)
        self.assertEqual(Mision("9", RetoPrueba()).recompensa, 10)

if __name__ == "__main__":
    unittest.main()
