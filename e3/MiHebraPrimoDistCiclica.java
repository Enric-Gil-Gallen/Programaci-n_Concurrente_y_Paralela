package e3;

import static e3.EjemploMuestraPrimosEnVector2a.esPrimo;

public class MiHebraPrimoDistCiclica extends Thread {
    int miId, numHebdas;
    long[] vectorNumeros;

    public MiHebraPrimoDistCiclica(int miId, int numHebdas, long[] vectorNumeros) {
        this.miId = miId;
        this.numHebdas = numHebdas;
        this.vectorNumeros = vectorNumeros;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {
        int ini = miId;
        int fin = vectorNumeros.length;
        int inc = numHebdas;

        for (int i = ini; i < fin; i += inc) {
            if (esPrimo(vectorNumeros[i])) {
                System.out.println("  Encontrado primo: " + vectorNumeros[i]);
            }
        }

    }


}