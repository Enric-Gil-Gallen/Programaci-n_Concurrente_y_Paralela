package e3;

// ============================================================================
class CuentaIncrementos_Synchronized {
// ============================================================================
  int numIncrementos = 0;

  // --------------------------------------------------------------------------
  synchronized void incrementaNumIncrementos() {
    numIncrementos++;
  }

  // --------------------------------------------------------------------------
  synchronized int dameNumIncrementos() {
    return( numIncrementos );
  }
}


// ============================================================================
class MiHebra_Synchronized extends Thread {
// ============================================================================
  int tope;
  CuentaIncrementos_Synchronized  c;

  // --------------------------------------------------------------------------
  public MiHebra_Synchronized( int tope, CuentaIncrementos_Synchronized c ) {
    this.tope  = tope;
    this.c     = c;
  }

  // --------------------------------------------------------------------------
  synchronized public void run() {
    for( int i = 0; i < tope; i++ ) {
      c.incrementaNumIncrementos();
    }
  }
}

// ============================================================================
class EjemploCuentaIncrementos_Synchronized {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    long    t1, t2;
    double  tt;
    int     numHebras, tope;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <tope>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      tope      = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      tope      = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    System.out.println( "numHebras: " + numHebras );
    System.out.println( "tope:      " + tope );
    
    System.out.println( "Creando y arrancando " + numHebras + " hebras." );
    t1 = System.nanoTime();
    MiHebra_Synchronized v[] = new MiHebra_Synchronized[ numHebras ];
    CuentaIncrementos_Synchronized c = new CuentaIncrementos_Synchronized();
    for( int i = 0; i < numHebras; i++ ) {
      v[ i ] = new MiHebra_Synchronized( tope, c );
      v[ i ].start();
    }
    for( int i = 0; i < numHebras; i++ ) {
      try {
        v[ i ].join();
      } catch( InterruptedException ex ) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Total de incrementos: " + c.dameNumIncrementos() );
    System.out.println( "Tiempo transcurrido en segs.: " + tt );
  }
}

