# Videojuego POO - Versión 3 (Héroes y Acertijos)

Bienvenido al repositorio del caso de estudio de la Unidad II. Este código base contiene las clases abstractas e interfaces que modelan la Versión 3 del juego.

## Reglas de Negocio (Diseño V3)

## 1. Ciclo de Vida de los Intentos y Estado de la Misión

- **Registro de Errores:** Cada intento fallido suma $1$ al contador de errores de la misión activa.
- **Límite y Bloqueo:** Al alcanzar el límite establecido (3 errores), la misión cambia su estado a `bloqueada`.
- **Rechazo de Intentos:** Todo cuarto intento posterior sobre una misión bloqueada debe ser rechazado de forma inmediata sin procesarse.
- **Recompensas Únicas:** La recompensa correspondiente se calcula y acredita **una sola vez** al completar exitosamente la misión.

---

## 2. Dificultades Dinámicas

- **Condición de Activación:** Las clases que implementan la interfaz `Dificultad` (por ejemplo, el bloqueo de rutas o sabotajes) solo pueden aplicarse si la misión se encuentra en estado `activa`.
- **Validación de Solución:** Ninguna dificultad puede aplicarse si su modificación deja el reto sin una solución matemática o lógica posible.
- **Estado Persistente y Restablecimiento:**
    - Al reiniciar una partida, se restauran las condiciones iniciales del reto.
    - Se conservan las compras de pistas realizadas y los premios registrados previamente.
    - La relectura de una pista previamente adquirida no genera ningún costo adicional.

---

## 3. Gestión de Partida y Misiones

- **Contenedor Principal (`Partida`):** Actúa como el orquestador central del juego. Administra una colección de objetos `Mision` mediante una relación de composición (una misión no existe de forma independiente fuera de la partida).
- **Composición Interna:** Cada objeto `Mision` encapsula exactamente un `Reto` y puede contener cero o más objetos `Pista`.
- **Configuración Dinámica:** Una partida puede configurarse agregando misiones de forma flexible que contengan retos de distinta naturaleza y con diversos niveles de dificultad.

---

## 4. Jerarquía Polimórfica de Retos Matemáticos

- **Clase Abstracta Base (`Reto`):** Define el contrato común para todos los problemas del juego mediante métodos como `evaluarRespuesta()`, `generarProblema()` y `aumentarDificultad()`.
- **Tipos Concretos de Retos:**
    - `RetoSecuencia`: Representa ejercicios basados en patrones y series numéricas.
    - `RetoOrdenamiento`: Modela ejercicios de ordenamiento de listas de elementos.
    - `RetoRutaCorta`: Gestiona ejercicios de optimización de caminos sobre grafos.
- **Escala de Dificultad:** La complejidad de cada reto está tipada mediante el enumerado `NivelDificultad` (`FACIL`, `MEDIO`, `DIFICIL`).

---

## 5. Especialización y Roles de los Villanos

- **Contrato de Villano:** La clase abstracta `Villano` obliga a sus subclases a implementar el método `complicarReto(r: Reto)`.
- **Modificación Dirigida:**
    - **Joker:** Evalúa y eleva la complejidad exclusivamente cuando el reto es de tipo `RetoSecuencia`.
    - **GreenGoblin:** Modifica y aumenta la dificultad únicamente en retos de tipo `RetoRutaCorta`.
- **Generación de Desafíos:** Los villanos actúan como creadores de misiones mediante el método `plantearMision(r: Reto)`.

---

## 6. Interacción de Héroes y Ayudantes

- **Resolución de Misiones:** El `Heroe` (por ejemplo, *Batman*) interactúa con el sistema para resolver misiones, consumiendo intentos y administrando un saldo de créditos.
- **Sistema de Pistas:** El héroe puede solicitar asistencia a un `Ayudante` (por ejemplo, *Alfred*), quien le provee objetos `Pista` a cambio de un costo predefinido en el catálogo.

## Instrucciones para Estudiantes (GitHub Flow)
- Revisa el tablero de Issues y asígnate una tarea (máximo 2 personas Peer-Programming por tarea para evitar colisiones).
- Haz un Fork del repositorio y trabaja en una rama descriptiva (ej. feature/34-reto-spiderman).
- Puedes agregar clases concretas o métodos privados, pero NO puedes modificar las firmas de los métodos en las clases abstractas o interfaces ya definidas.
- Sube tus cambios y abre un Pull Request (PR).
- Solicita la revisión de al menos dos compañeros de clase. Una vez aprobada por ellos, la profesora hará el merge final.

## Recompensas de las misiones (T06)

Cada misión tiene una recompensa. Si no se indica el monto al crearla, vale 10 créditos.

- **Mision:** guarda el monto y si la recompensa ya se entregó.
- **Heroe:** recibe los créditos solamente si la misión está completada y no se han cobrado antes. En ese caso `acreditarRecompensa` devuelve `true`; si no, devuelve `false`.
- **Partida:** llama al héroe para entregar la recompensa y suma al total `recompensasGlobales` solamente cuando el pago se hizo. La misión tiene que estar agregada a esa partida.

Por ejemplo, si Batman empieza con 100 créditos y completa una misión que paga 30, después de cobrar queda con 130 y el total de la partida pasa a 30. Si intenta cobrarla de nuevo, ambos números se quedan igual. Una misión activa o bloqueada no paga nada.

Pruebas de Python desde la raíz:

```bash
python -m unittest discover -s python -p "test_recompensa_mision.py" -v
```

Pruebas de Java desde `java/`:

```bash
javac NivelDificultad.java Personaje.java Reto.java Heroe.java Batman.java Mision.java Partida.java TestRecompensaMision.java
java TestRecompensaMision
```
