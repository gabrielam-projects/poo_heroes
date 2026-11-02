# Pruebas del Issue #10

Las pruebas comprueban que una mision solo procesa respuestas si esta activa. Los retos de prueba cuentan las evaluaciones para verificar que no se repitan al completar o bloquear una mision.

## Python
Desde la raiz del repositorio:

```bash
python -m unittest discover -s python -p "test_mision_estado.py" -v
```

## Java
Desde la raiz del repositorio:

```bash
cd java
javac NivelDificultad.java Reto.java Mision.java TestMisionEstado.java
java TestMisionEstado
```

Se requiere Python 3 y un JDK instalado. No se necesitan bibliotecas adicionales.
