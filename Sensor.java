public class Sensor extends Thread {
    private int id;
    private int baseEventos;
    private int ns;
    private Buzon entrada;

    public Sensor(int id, int baseEventos, int ns, Buzon entrada) {
        this.id = id;
        this.baseEventos = baseEventos;
        this.ns = ns;
        this.entrada = entrada;
    }

    public void run() {
        int eventosGenerados = id * baseEventos;
        for (int i = 1; i <= eventosGenerados; i++) {
            String idEvento = "S" + id + "E" + i;
            int tipoDestino = (int) (Math.random() * ns) + 1;
            Evento evento = new Evento(idEvento, tipoDestino);
            entrada.almacenar(evento);
            System.out.println("Sensor " + id + " genera " + idEvento);
        }
    }
}