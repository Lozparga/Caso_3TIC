public class Contador {
    private int activos;

    public Contador(int total) {
        this.activos = total;
    }

    public synchronized boolean soyElUltimo() {
        activos--;
        return activos == 0;
    }
}