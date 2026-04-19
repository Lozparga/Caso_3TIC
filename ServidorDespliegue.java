public class ServidorDespliegue extends Thread {

    private Buzon buzon;
    private int id;

    public ServidorDespliegue(int id, Buzon buzon) {
        this.id = id;
        this.buzon = buzon;
    }

    @Override
    public void run() {
        boolean activo = true;
        while (activo) {
            // Espera pasiva
            Evento evento = buzon.obtener();
            if (evento.esFin()) {
                activo = false;
                System.out.println("Servidor " + id + " finaliza.");
            } 
            else {
                System.out.println("Servidor " + id + " procesando evento " + evento.getId());
                //Simular procesamiento (100ms - 1000ms)
                int tiempo = (int)(Math.random() * 901) + 100;
                try {
                    Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}