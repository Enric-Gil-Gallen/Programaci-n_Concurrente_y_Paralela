package e2;

import static e2.EjemploFuncionCostosa1a.evaluaFuncion;

public class Ej2_MyThreadCiclica extends Thread  {
    int miId, nElem, numHebdas;
    double vectorX[], vectorY[];

    public Ej2_MyThreadCiclica(int miId, int nElem, int numHebdas, double[] vectorX, double[] vectorY) {
        this.miId = miId;
        this.nElem = nElem;
        this.numHebdas = numHebdas;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {
        int ini = miId;
        int fin = nElem;
        int inc = numHebdas;

        for (int i = ini; i < fin; i += inc) {
            vectorY[i] = evaluaFuncion(vectorX[i]);
        }

    }

}
