package e6;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static e6.EjemploPalabraMasUsada1a.contabilizaPalabraConcurrentHashMapAtomica;

public class MiHebra_6 extends Thread{
    int miId;
    int numHebras;
    final ConcurrentHashMap<String, AtomicInteger> chaCuentaPalabras;
    Vector<String> vectorLineas;
    String palabraActual;

    public MiHebra_6(int miId, int numHebras, ConcurrentHashMap<String, AtomicInteger> chaCuentaPalabras, Vector<String> vectorLineas) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.chaCuentaPalabras = chaCuentaPalabras;
        this.vectorLineas = vectorLineas;
    }

    public void run(){
        for( int i = miId; i < vectorLineas.size(); i+= numHebras ) {
            // Procesa la linea "i".
            String[] palabras = vectorLineas.get( i ).split( "\\W+" );
            for( int j = 0; j < palabras.length; j++ ) {
                // Procesa cada palabra de la linea "i", si es distinta de blancos.
                palabraActual = palabras[ j ].trim();
                if( palabraActual.length() >= 1 ) {
                    contabilizaPalabraConcurrentHashMapAtomica(chaCuentaPalabras, palabraActual);
                }
            }
        }
    }


}