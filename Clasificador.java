public class Clasificador extends Thread {

    private Buzon clasificacion;
    private Buzon[] servidores;
    private int ns;
    private Contador contador;

    public Clasificador(Buzon clasificacion, Buzon[] servidores, int ns, Contador contador) {
        this.clasificacion = clasificacion;
        this.servidores = servidores;
        this.ns = ns;
        this.contador = contador;
    }

    @Override
    public void run() {

        boolean terminar = false;

        while (!terminar) {

            Evento evento = clasificacion.obtener();
            System.out.println("Clasificador recibe: " + evento.getId());
            if (evento.esFin()) {
                System.out.println("Clasificador recibe FIN");
                terminar = true;
                if (contador.soyElUltimo()) {
                    System.out.println("Último clasificador → envía FIN a servidores");
                    for (int i = 0; i < ns; i++) {
                        Evento fin = new Evento("FIN", -1, true);
                        boolean enviado = false;
                        while (!enviado) {
                            enviado = servidores[i].almacenarClasificacion(fin);
                            if (!enviado) {
                                Thread.yield();
                            }
                        }
                    }
                }

            } else {

                int destino = evento.getTipo() - 1;
                System.out.println("Clasificador envía " + evento.getId() + " → Servidor " + (destino + 1));
                boolean enviado = false;
                while (!enviado) {
                    enviado = servidores[destino].almacenarClasificacion(evento);
                    if (!enviado) {
                        Thread.yield();
                    }
                }
            }
        }
    }
}