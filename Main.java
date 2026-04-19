import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        int ni = 0;
        int baseEventos = 0;
        int nc = 0;
        int ns = 0;
        int tam1 = 0;
        int tam2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split("=");
                String clave = partes[0].trim();
                int valor = Integer.parseInt(partes[1].trim());

                switch (clave) {
                    case "ni": ni = valor; break;
                    case "baseEventos": baseEventos = valor; break;
                    case "nc": nc = valor; break;
                    case "ns": ns = valor; break;
                    case "tam1": tam1 = valor; break;
                    case "tam2": tam2 = valor; break;
                }
            }

            // Crear buzones
            Buzon entrada = new Buzon(-1); // ilimitado
            Buzon alertas = new Buzon(-1); // ilimitado
            Buzon clasificacion = new Buzon(tam1);

            Buzon[] consolidacion = new Buzon[ns];
            for (int i = 0; i < ns; i++) {
                consolidacion[i] = new Buzon(tam2);
            }

            // Calcular total de eventos
            int totalEventos = 0;
            for (int i = 1; i <= ni; i++) {
                totalEventos += i * baseEventos;
            }

            // Servidores
            for (int i = 0; i < ns; i++) {
                new ServidorDespliegue(i + 1, consolidacion[i]).start();
            }

            // Clasificadores
            Contador contador = new Contador(nc);

            for (int i = 0; i < nc; i++) {
                new Clasificador(clasificacion, consolidacion, ns, contador).start();
            }

            // Administrador
            new Administrador(alertas, clasificacion, nc).start();

            // Broker
            new BrokeryAnalizador(entrada, clasificacion, alertas, totalEventos).start();

            // Sensores
            for (int i = 1; i <= ni; i++) {
                new Sensor(i, baseEventos, ns, entrada).start();
            }

            System.out.println(">>> Simulación iniciada correctamente");
            System.out.println(">>> Total eventos: " + totalEventos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}