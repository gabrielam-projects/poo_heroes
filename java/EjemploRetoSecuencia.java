import java.util.Scanner;

public class EjemploRetoSecuencia {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        NivelDificultad[] niveles = {
            NivelDificultad.FACIL,
            NivelDificultad.MEDIO,
            NivelDificultad.DIFICIL
        };

        for (NivelDificultad nivel : niveles) {

            RetoSecuencia reto = new RetoSecuencia(nivel);

            System.out.println("\nNivel: " + nivel);
            System.out.println("Secuencia: " +
                reto.getSecuenciaIncompleta());

            System.out.print("¿Qué número falta? ");

            String respuesta = entrada.nextLine();

            if (reto.evaluarRespuesta(respuesta)) {
                System.out.println("¡Respuesta correcta!");
            } else {
                System.out.println("Respuesta incorrecta.");
            }
        }

        entrada.close();
    }
}
