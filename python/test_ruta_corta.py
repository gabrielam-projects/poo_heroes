import unittest
from mision import Mision
from personajes import GreenGoblin
from reto_ruta_corta import RetoRutaCorta
from retos import RetoSecuencia
from nivel_dificultad import NivelDificultad


class TestRutaCorta(unittest.TestCase):
    def test_ruta_minima(self):
        reto = RetoRutaCorta()
        self.assertEqual(reto.costo_minimo(), 5)
        self.assertTrue(reto.evaluar_respuesta("A-B-C-D"))

    def test_ruta_mas_cara(self):
        self.assertFalse(RetoRutaCorta().evaluar_respuesta("A-B-D"))

    def test_ruta_incorrecta(self):
        reto = RetoRutaCorta()
        self.assertFalse(reto.evaluar_respuesta("A-D"))
        self.assertFalse(reto.evaluar_respuesta("A-X-D"))

    def test_green_goblin(self):
        reto = RetoRutaCorta()
        self.assertTrue(Mision("1", reto).aplicar_dificultad(GreenGoblin()))
        self.assertEqual(reto.costo_minimo(), 6)
        self.assertTrue(reto.evaluar_respuesta("A-B-D"))

    def test_otro_reto(self):
        reto = RetoSecuencia(NivelDificultad.FACIL)
        self.assertFalse(Mision("2", reto).aplicar_dificultad(GreenGoblin()))
        self.assertEqual(reto.dificultad, NivelDificultad.FACIL)

    def test_mision_completada(self):
        reto = RetoRutaCorta()
        mision = Mision("3", reto)
        mision.completar()
        self.assertFalse(mision.aplicar_dificultad(GreenGoblin()))
        self.assertEqual(reto.costo_minimo(), 5)

    def test_mision_bloqueada(self):
        reto = RetoRutaCorta()
        mision = Mision("4", reto)
        for _ in range(3):
            mision.procesar_intento("mal")
        self.assertFalse(mision.aplicar_dificultad(GreenGoblin()))
        self.assertEqual(reto.costo_minimo(), 5)

    def test_no_desconectar(self):
        reto = RetoRutaCorta("A", "B")
        reto.agregar_tramo("A", "B", 3)
        self.assertFalse(Mision("5", reto).aplicar_dificultad(GreenGoblin("A", "B")))
        self.assertTrue(reto.evaluar_respuesta("A-B"))

    def test_crear_mision(self):
        reto = RetoRutaCorta()
        GreenGoblin().plantear_mision("6", reto)
        self.assertTrue(reto.evaluar_respuesta("A-B-D"))


if __name__ == "__main__":
    unittest.main()
