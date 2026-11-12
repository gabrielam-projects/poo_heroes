import unittest
from retos import RetoSecuencia
from nivel_dificultad import NivelDificultad


class TestRetoSecuencia(unittest.TestCase):
    def comprobar_nivel(self, nivel, secuencia, respuesta):
        reto = RetoSecuencia(nivel)
        self.assertEqual(reto.mostrar_secuencia(), secuencia)
        self.assertTrue(reto.evaluar_respuesta(respuesta))
        self.assertFalse(reto.evaluar_respuesta("999"))
        reto.generar_problema()
        self.assertEqual(reto.mostrar_secuencia(), secuencia)
        self.assertTrue(reto.evaluar_respuesta(respuesta))

    def test_facil(self):
        self.comprobar_nivel(NivelDificultad.FACIL, "2, 4, 6, 8, ?", "10")

    def test_medio(self):
        self.comprobar_nivel(NivelDificultad.MEDIO, "3, 6, 12, 24, ?", "48")

    def test_dificil(self):
        self.comprobar_nivel(NivelDificultad.DIFICIL, "2, 3, 5, 8, ?", "13")

    def test_formato_invalido(self):
        reto = RetoSecuencia(NivelDificultad.FACIL)
        for respuesta in (None, "", "  ", "hola", "10.0", "1 0", "+10", "-10"):
            self.assertFalse(reto.evaluar_respuesta(respuesta))

    def test_cambiar_dificultad(self):
        reto = RetoSecuencia(NivelDificultad.FACIL)
        reto.aumentar_dificultad()
        reto.generar_problema()
        self.assertEqual(reto.mostrar_secuencia(), "3, 6, 12, 24, ?")
        self.assertTrue(reto.evaluar_respuesta("48"))


if __name__ == "__main__":
    unittest.main()
