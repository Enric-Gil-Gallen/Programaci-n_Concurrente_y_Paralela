package e2;

import static e2.EjemploFuncionSencilla1a.evaluaFuncion;

public class Ej2_MyThreadBloques extends Thread{
    int miId, nElem, numHebdas;
    double vectorX[], vectorY[];

    public Ej2_MyThreadBloques(int miId, int nElem, int numHebdas, double[] vectorX, double[] vectorY) {
        this.miId = miId;
        this.nElem = nElem;
        this.numHebdas = numHebdas;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {

        int tamBloque = (nElem + numHebdas - 1) / numHebdas;
        int iniElem = tamBloque * miId;
        int finElem = Math.min(iniElem + tamBloque, nElem);

        for (int i = iniElem; i < finElem; i++)
            vectorY[i] = evaluaFuncion(vectorX[i]);

    }
}
