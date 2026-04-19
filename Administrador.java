public class Administrador extends Thread {

    private Buzon alertas;
    private Buzon clasificacion;
    private int nc;

    public Administrador(Buzon alertas, Buzon clasificacion, int nc) {
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.nc = nc;
    }

    @Override
    public void run() {
        boolean terminar = false;
        while (!terminar) {
            Evento evento = alertas.obtener(); // espera pasiva
            if (evento.esFin()) {
                System.out.println("Admin recibe FIN → avisa a clasificadores");
                terminar = true;
                // enviar FIN a cada clasificador
                for (int i = 0; i < nc; i++) {
                    Evento fin = new Evento("FIN", -1, true);
                    boolean enviado = false;
                    while (!enviado) {
                        enviado = clasificacion.almacenarClasificacion(fin);
                        if (!enviado) {
                            Thread.yield(); // semiactiva
                        }
                    }
                }

            } else {

                System.out.println("Admin revisa " + evento.getId());
                int random = (int) (Math.random() * 21);
                if (random % 4 == 0) {
                    boolean enviado = false;
                    while (!enviado) {
                        enviado = clasificacion.almacenarClasificacion(evento);
                        System.out.println("Admin aprueba → CLASIFICACIÓN: " + evento.getId());
                        if (!enviado) {
                            Thread.yield();
                        }
                    }

                } else {
                    // evento descartado
                    System.out.println("Admin descarta evento: " + evento.getId());
                }
            }
        }
    }
}