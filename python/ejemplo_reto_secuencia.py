from retos import RetoSecuencia
from nivel_dificultad import NivelDificultad


niveles = [
    NivelDificultad.FACIL,
    NivelDificultad.MEDIO,
    NivelDificultad.DIFICIL
]

for nivel in niveles:

    reto = RetoSecuencia(nivel)

    print("\nNivel:", nivel.name)
    print("Secuencia:", reto.obtener_secuencia_incompleta())

    respuesta = input("¿Qué número falta? ")

    if reto.evaluar_respuesta(respuesta):
        print("¡Respuesta correcta!")
    else:
        print("Respuesta incorrecta.")
