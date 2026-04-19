import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Evento> colaEventos = new LinkedList<>();
    private int capacidad;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }

    // Buzon entrada ilimitado
    public synchronized void almacenar(Evento evento) {
        colaEventos.add(evento);
        notify();
    }

    // Obtener con el broker es con espera pasiva
    public synchronized Evento obtener() {
        while (colaEventos.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Evento evento = colaEventos.poll();
        notify();
        return evento;
    }

    // Clasificación (NO bloqueante → semiactiva afuera)
    public synchronized boolean almacenarClasificacion(Evento evento){
        if (colaEventos.size() >= capacidad) {
            return false; //no pudo
        }
        colaEventos.add(evento);
        notify();
        return true; //pudo
    }
}