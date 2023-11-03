package e3;

import java.util.concurrent.atomic.AtomicInteger;

import static e3.EjemploMuestraPrimosEnVector2a.esPrimo;

public class MiHebraPrimoDistPorDinamica  extends Thread {
    AtomicInteger n;
    long[] vectorNumeros;
    int miId, inumHebdas;

    public MiHebraPrimoDistPorDinamica(AtomicInteger n, long[] vectorNumeros, int miId, int inumHebdas) {
        this.n = n;
        this.vectorNumeros = vectorNumeros;
        this.miId = miId;
        this.inumHebdas = inumHebdas;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {
        int valor_inicial = n.getAndIncrement();
        int incremento = n.get();

        for (int pos = valor_inicial; pos < vectorNumeros.length; pos = incremento){

            if (esPrimo(vectorNumeros[pos])) {
                System.out.println("  Encontrado primo: " + vectorNumeros[pos]);
            }

            incremento = n.getAndIncrement();
        }

    }
}