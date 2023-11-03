package e1;


// ============================================================================
class CuentaIncrementos1a {
// ============================================================================
  long contador = 0;

  // --------------------------------------------------------------------------
  void incrementaContador() {
    contador++;
  }

  // --------------------------------------------------------------------------
  long dameContador() {
    return( contador );
  }
}


// ============================================================================
class EjemploIncrementos1a {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int  numHebras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
      System.err.println( "Uso: java programa <numHebras>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    System.out.println( "numHebras: " + numHebras );

    CuentaIncrementos1a contador = new CuentaIncrementos1a();
    System.out.println("Valor inicial: "+contador.dameContador());
    miHebraEj3[] vectorHebra = new miHebraEj3[numHebras];
    for (int i = 0; i < numHebras; i++){
      vectorHebra[i] = new miHebraEj3(i, contador);
      vectorHebra[i].start();
    }

    try{
      for (int i = 0; i < numHebras; i++){
        vectorHebra[i].join();
      }
    }catch (InterruptedException ex){
      ex.printStackTrace();
    }


    System.out.println("Valor final del contador: "+contador.dameContador());
  }
}

