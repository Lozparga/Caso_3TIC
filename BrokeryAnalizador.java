public class BrokeryAnalizador extends Thread {
    
    private Buzon entrada;
    private Buzon clasificacion;
    private Buzon alertas;
    private int totalEventos; 

    public BrokeryAnalizador(Buzon entrada, Buzon clasificacion, Buzon alertas, int totalEventos) {
        this.entrada = entrada;
        this.clasificacion = clasificacion;
        this.alertas = alertas;
        this.totalEventos = totalEventos;
    }

    public void run() {
        for (int i = 0; i < totalEventos; i++) {
            Evento evento = entrada.obtener(); //Obtiene que pasiva
            int random = (int) (Math.random() * 201);
            System.out.println("Broker recibe " + evento.getId());
            if (random % 8 == 0) {
                System.out.println("Broker envía a ALERTAS: " + evento.getId());
                alertas.almacenar(evento);
            } else {
                //Semiactiva
            boolean almacenado = false;
            while (!almacenado) {
                almacenado = clasificacion.almacenarClasificacion(evento);
                if (!almacenado) {
                    Thread.yield();
                    }
                }
            System.out.println("Broker envía a CLASIFICACIÓN: " + evento.getId());
            }
        }
        //Evento final enviada al admin
        Evento fin = new Evento("FIN", -1, true);
        alertas.almacenar(fin);
    }
}

