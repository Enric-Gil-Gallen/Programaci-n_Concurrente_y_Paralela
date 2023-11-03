package e3;

import static e3.EjemploMuestraPrimosEnVector2a.esPrimo;

public class MiHebraPrimoDistPorBloques extends Thread {
    int miId, numHebdas, nElem;
    long[] vectorNumeros;

    public MiHebraPrimoDistPorBloques(int miId, int numHebdas, int nElem, long[] vectorNumeros) {
        this.miId = miId;
        this.numHebdas = numHebdas;
        this.nElem = nElem;
        this.vectorNumeros = vectorNumeros;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {
        int tamBloque = (nElem + numHebdas - 1) / numHebdas;
        int iniElem = tamBloque * miId;
        int finElem = Math.min(iniElem + tamBloque, nElem);

        for (int i = iniElem; i < finElem; i++)
            if (esPrimo(vectorNumeros[i])) {
                System.out.println("  Encontrado primo: " + vectorNumeros[i]);
            }
    }
}