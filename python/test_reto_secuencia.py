import unittest

from retos import RetoSecuencia
from nivel_dificultad import NivelDificultad


class TestRetoSecuencia(unittest.TestCase):

    def test_niveles(self):

        casos = [
            (NivelDificultad.FACIL, [2, 4, None, 8, 10], "6"),
            (NivelDificultad.MEDIO, [3, 6, None, 12, 15], "9"),
            (NivelDificultad.DIFICIL, [2, 6, 12, None, 30], "20")
        ]

        for nivel, secuencia, respuesta in casos:
            with self.subTest(nivel=nivel):
                reto = RetoSecuencia(nivel)

                self.assertEqual(
                    reto.obtener_secuencia_incompleta(),
                    secuencia
                )

                self.assertTrue(
                    reto.evaluar_respuesta(respuesta)
                )

    
    def test_respuestas_incorrectas(self):

        casos = [
            (NivelDificultad.FACIL, "5"),
            (NivelDificultad.MEDIO, "8"),
            (NivelDificultad.DIFICIL, "19")
        ]

        for nivel, respuesta in casos:
            with self.subTest(nivel=nivel):
                reto = RetoSecuencia(nivel)
                self.assertFalse(reto.evaluar_respuesta(respuesta))

    def test_formatos_invalidos(self):

        for nivel in NivelDificultad:
            reto = RetoSecuencia(nivel)

            for respuesta in ["", "hola", "6.5", " ", None, 6]:
                with self.subTest(nivel=nivel, respuesta=respuesta):
                    self.assertFalse(reto.evaluar_respuesta(respuesta))


    def test_generacion_reproducible(self):

        reto = RetoSecuencia(NivelDificultad.MEDIO)

        primera = reto.obtener_secuencia_incompleta()

        reto.generar_problema()

        segunda = reto.obtener_secuencia_incompleta()

        self.assertEqual(primera, segunda)


if __name__ == "__main__":
    unittest.main()
