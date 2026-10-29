import unittest

from personajes import Batman, Heroe


class TestBatmanSaldo(unittest.TestCase):
    def test_batman_es_instanciable(self):
        batman = Batman()
        self.assertEqual(batman.nombre, "Batman")

    def test_saldo_inicial(self):
        self.assertEqual(Batman().consultar_saldo(), 100)

    def test_pago_valido(self):
        batman = Batman()
        self.assertTrue(batman.pagar(25))
        self.assertEqual(batman.consultar_saldo(), 75)

    def test_pago_rechazado_no_modifica_saldo(self):
        batman = Batman()
        self.assertFalse(batman.pagar(150))
        self.assertEqual(batman.consultar_saldo(), 100)

    def test_heroe_permanece_abstracto(self):
        with self.assertRaises(TypeError):
            Heroe("Heroe generico")


if __name__ == "__main__":
    unittest.main()
