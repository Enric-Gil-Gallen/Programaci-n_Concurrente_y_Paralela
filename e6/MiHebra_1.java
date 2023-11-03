package e6;

import java.util.Map;
import java.util.Vector;

import static e6.EjemploPalabraMasUsada1a.contabilizaPalabraSynchronized;

public class MiHebra_1 extends Thread {
    int miId;
    int numHebras;
    Map<String,Integer> maCuentaPalabras;
    Vector<String> vectorLineas;
    String palabraActual;

    public MiHebra_1(int miId, int numHebras, Map<String, Integer> maCuentaPalabras , Vector<String> vectorLineas) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.maCuentaPalabras = maCuentaPalabras;
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
                    contabilizaPalabraSynchronized( maCuentaPalabras, palabraActual );
                }
            }
        }
    }


}
