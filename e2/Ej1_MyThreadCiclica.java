package e2;

public class Ej1_MyThreadCiclica extends Thread  {
    int miId, nElem, numHebdas;

    public Ej1_MyThreadCiclica(int miId, int nElem, int numHebdas) {
        this.miId = miId;
        this.nElem = nElem;
        this.numHebdas = numHebdas;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {
        int ini = miId;
        int fin = nElem;
        int inc = numHebdas;

        for (int i = ini; i < fin; i += inc) {
            System.out.print(i + " ");
        }

        System.out.println("-------------------");
    }
}
