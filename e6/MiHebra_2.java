package e6;

import java.util.Hashtable;
import java.util.Vector;

import static e6.EjemploPalabraMasUsada1a.contabilizaPalabraHashtable;

public class MiHebra_2 extends Thread{
    int miId;
    int numHebras;
    Hashtable<String,Integer> htCuentaPalabras;
    Vector<String> vectorLineas;
    String palabraActual;

    public MiHebra_2(int miId, int numHebras, Hashtable<String, Integer> htCuentaPalabras, Vector<String> vectorLineas) {
        this.miId = miId;
        this.numHebras = numHebras;
        this.htCuentaPalabras = htCuentaPalabras;
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
                    contabilizaPalabraHashtable(htCuentaPalabras, palabraActual);
                }
            }
        }
    }


}