package e3;


import java.util.concurrent.atomic.AtomicInteger;

// ===========================================================================
public class EjemploMuestraPrimosEnVector2a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     numHebras;
    long    t1, t2;
    double  ts, tc, tb, td, sp_ciclical, sp_bloques, sp_dianmica;
//    long    vectorNumeros[] = {
//                200000033L, 200000039L, 200000051L, 200000069L,
//                200000161L, 200000183L, 200000201L, 200000209L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
//                4L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
//            };
     long    vectorNumeros[] = {
                 200000033L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000039L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000051L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000069L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000161L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000183L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000201L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                 200000209L, 4L, 4L, 4L, 4L, 4L, 4L, 4L
             };
    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
//      System.err.println( "Uso: java programa <numHebras>" );
//      System.exit( -1 );
    }
    try {
//      numHebras = Integer.parseInt( args[ 0 ] );
      numHebras = 4;
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }
    //
    // Implementacion secuencial.
    //
    System.out.println( "" );
    System.out.println( "Implementacion secuencial." );
    t1 = System.nanoTime();
    for( int i = 0; i < vectorNumeros.length; i++ ) {
      if( esPrimo( vectorNumeros[ i ] ) ) {
        System.out.println( "  Encontrado primo: " + vectorNumeros[ i ] );
      }
    }
    t2 = System.nanoTime();
    ts = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + ts );

    //
    // Implementacion paralela ciclica.
    //
    System.out.println( "" );
    System.out.println( "Implementacion paralela ciclica." );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela ciclica

    // Crea y arranca el vector de hebras.
    MiHebraPrimoDistCiclica[] vectorHebras = new MiHebraPrimoDistCiclica[numHebras];
    for (int i = 0; i < numHebras; i++) {
      vectorHebras[i] = new MiHebraPrimoDistCiclica(i, numHebras, vectorNumeros);
      vectorHebras[i].start();
    }

    // Espera a que terminen las hebras.

    for (int i = 0; i < numHebras; i++) {
      try {
        vectorHebras[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    tc = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    sp_ciclical = ts / tc;
    System.out.println( "Tiempo paralela ciclica (seg.):              " + tc );
    System.out.println( "Incremento paralela ciclica:                 " + sp_ciclical );
    /*
    //
    // Implementacion paralela por bloques.
    //
      */
    System.out.println( "" );
    System.out.println( "Implementacion paralela bloques." );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela ciclica

    // Crea y arranca el vector de hebras.
    MiHebraPrimoDistPorBloques[] vectorHebrasBloques = new MiHebraPrimoDistPorBloques[numHebras];

    for (int i = 0; i < numHebras; i++) {
      vectorHebrasBloques[i] = new MiHebraPrimoDistPorBloques(i, numHebras, vectorNumeros.length, vectorNumeros);
      vectorHebrasBloques[i].start();
    }

    // Espera a que terminen las hebras.

    for (int i = 0; i < numHebras; i++) {
      try {
        vectorHebrasBloques[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    tb = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    sp_bloques = ts / tb;
    System.out.println( "Tiempo paralela bloques (seg.):              " + tb );
    System.out.println( "Incremento paralela or bloques:                 " + sp_bloques );

    //
    // Implementacion paralela dinamica.
    //
    // ....

    System.out.println( "" );
    System.out.println( "Implementacion paralela dinamica." );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela ciclica

    AtomicInteger n = new AtomicInteger(0);
    // Crea y arranca el vector de hebras.
    MiHebraPrimoDistPorDinamica[] vectorHebrasDinamica = new MiHebraPrimoDistPorDinamica[numHebras];

    for (int i = 0; i < numHebras; i++) {
      vectorHebrasDinamica[i] = new MiHebraPrimoDistPorDinamica(n, vectorNumeros, i, numHebras);
      vectorHebrasDinamica[i].start();
    }

    // Espera a que terminen las hebras.

    for (int i = 0; i < numHebras; i++) {
      try {
        vectorHebrasDinamica[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    td = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    sp_dianmica = ts / td;
    System.out.println( "Tiempo paralela bloques (seg.):              " + td );
    System.out.println( "Incremento paralela or bloques:                 " + sp_dianmica );

  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) { 
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}
