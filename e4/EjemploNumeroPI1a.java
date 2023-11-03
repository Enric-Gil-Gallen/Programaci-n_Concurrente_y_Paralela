package e4;

import java.util.concurrent.atomic.DoubleAdder;

class Acumula {
  double sum;

  public Acumula(){
    this.sum = 0;
  }

  synchronized void acumulaNum(double num){
    sum += num;
  }

  synchronized double dameNum() {
    return sum;
  }
}

class MiHebraMultAcumulaciones1 extends Thread{
  // ===========================================================================
  int miId, numHebras;
  long numRec;
  Acumula a;
  double baseRec, x;

  // -------------------------------------------------------------------------
  MiHebraMultAcumulaciones1(int miId, int numHebras, long numRec, Acumula a) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRec = numRec;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    baseRec = 1.0 / ((double) numRec);
    for (long i = miId; i < numRec; i += numHebras) {
      x = baseRec * (((double) i) + 0.5);
      a.acumulaNum(EjemploNumeroPI1a.f(x));
    }
  }
}

class MiHebraUnaAcumulaciones1_2 extends Thread{
  // ===========================================================================
  int miId, numHebras;
  long numRec;
  Acumula a;
  double baseRec, x;

  // -------------------------------------------------------------------------
  MiHebraUnaAcumulaciones1_2(int miId, int numHebras, long numRec, Acumula a) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRec = numRec;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    baseRec = 1.0 / ((double) numRec);
    double sumaL = 0.0;
    for (long i = miId; i < numRec; i += numHebras) {
      x = baseRec * (((double) i) + 0.5);
      sumaL += EjemploNumeroPI1a.f(x);
    }
    a.acumulaNum(sumaL);
  }
}

class AcumulaAtomica {
  DoubleAdder sum;

  // -------------------------------------------------------------------------
  public AcumulaAtomica() {
    this.sum = new DoubleAdder();
  }

  // -------------------------------------------------------------------------
  void acumulaDato(double num) {
    this.sum.add(num);
  }

  // -------------------------------------------------------------------------
  double dameDato() {
    return sum.doubleValue();
  }
}

class MiHebraMultAcumulacionAtomica1_3 extends Thread {
  // ===========================================================================
  int miId, numHebras;
  long numRec;
  AcumulaAtomica a;
  double baseRec, x;

  MiHebraMultAcumulacionAtomica1_3(int miId, int numHebras, long numRec, AcumulaAtomica a) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRec = numRec;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    baseRec = 1.0 / ((double) numRec);
    for (long i = miId; i < numRec; i += numHebras) {
      x = baseRec * (((double) i) + 0.5);
      a.acumulaDato(EjemploNumeroPI1a.f(x));
    }

  }
}

class MiHebraUnaAcumulacionAtomica1_3 extends Thread {
  int miId, numHebras;
  long numRec;
  AcumulaAtomica a;
  double baseRec, x;

  MiHebraUnaAcumulacionAtomica1_3(int miId, int numHebras, long numRec, AcumulaAtomica a) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRec = numRec;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    baseRec = 1.0 / ((double) numRec);
    double suma = 0.0;
    for (long i = miId; i < numRec; i += numHebras) {
      x = baseRec * (((double) i) + 0.5);
      suma+=EjemploNumeroPI1a.f(x);
    }
    a.acumulaDato(suma);
  }
}


// ===========================================================================
class EjemploNumeroPI1a {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                        numRectangulos;
    double                      baseRectangulo, x, suma, pi;
    int                         numHebras;
    long                        t1, t2;
    double                      tSec, tPar;
    Acumula                     a;
    AcumulaAtomica              aAtomica;
    //MiHebraMultAcumulaciones1  vt[];

    // Comprobacion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <numRectangulos>" );
      System.exit( -1 );
    }
    try {
      numHebras      = Integer.parseInt( args[ 0 ] );
      numRectangulos = Long.parseLong( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras      = -1;
      numRectangulos = -1;
      System.out.println( "ERROR: Numeros de entrada incorrectos." );
      System.exit( -1 );
    }


    System.out.println();
    System.out.println( "Calculo del numero PI mediante integracion." );

    //
    // Calculo del numero PI de forma secuencial.
    //
    System.out.println();
    System.out.println( "Comienzo del calculo secuencial." );
    t1 = System.nanoTime();
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    suma           = 0.0;
    for( long i = 0; i < numRectangulos; i++ ) {
      x = baseRectangulo * ( ( ( double ) i ) + 0.5 );
      suma += f( x );
    }
    pi = baseRectangulo * suma;
    t2 = System.nanoTime();
    tSec = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version secuencial. Numero PI: " + pi );
    System.out.println( "Tiempo secuencial (s.):        " + tSec );

    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra.
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Multiples acumulaciones por hebra." );
    t1 = System.nanoTime();

    a = new Acumula();
    MiHebraMultAcumulaciones1[] h1_1 = new MiHebraMultAcumulaciones1[numHebras];

    for (int i = 0; i < numHebras; i++) {
      h1_1[i] = new MiHebraMultAcumulaciones1(i, numHebras, numRectangulos, a);
      h1_1[i].start();
    }
    try{
      for (int i = 0; i < numHebras; i++) {
        h1_1[i].join();
      }
    }catch (InterruptedException ex){
      ex.printStackTrace();
    }

    pi = baseRectangulo * a.dameNum();
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra.
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Una acumulacion por hebra." );
    t1 = System.nanoTime();

    a = new Acumula();
    MiHebraUnaAcumulaciones1_2[] h1_2 = new MiHebraUnaAcumulaciones1_2[numHebras];

    for (int i = 0; i < numHebras; i++) {
      h1_2[i] = new MiHebraUnaAcumulaciones1_2(i, numHebras, numRectangulos, a);
      h1_2[i].start();
    }
    try{
      for (int i = 0; i < numHebras; i++) {
        h1_2[i].join();
      }
    }catch (InterruptedException ex){
      ex.printStackTrace();
    }

    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra (Atomica)
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Multiples acumulaciones por hebra (At)." );
    t1 = System.nanoTime();

    aAtomica = new AcumulaAtomica();
    MiHebraMultAcumulacionAtomica1_3[] h1_3 = new MiHebraMultAcumulacionAtomica1_3[numHebras];

    for (int i = 0; i < numHebras; i++) {
      h1_3[i] = new MiHebraMultAcumulacionAtomica1_3(i, numHebras, numRectangulos, aAtomica);
      h1_3[i].start();
    }
    try{
      for (int i = 0; i < numHebras; i++) {
        h1_3[i].join();
      }
    }catch (InterruptedException ex){
      ex.printStackTrace();
    }

    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra (Atomica).
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Una acumulacion por hebra (At)." );
    t1 = System.nanoTime();

    aAtomica = new AcumulaAtomica();
    MiHebraUnaAcumulacionAtomica1_3[] h1_4 = new MiHebraUnaAcumulacionAtomica1_3[numHebras];

    for (int i = 0; i < numHebras; i++) {
      h1_4[i] = new MiHebraUnaAcumulacionAtomica1_3(i, numHebras, numRectangulos, aAtomica);
      h1_4[i].start();
    }
    try{
      for (int i = 0; i < numHebras; i++) {
        h1_4[i].join();
      }
    }catch (InterruptedException ex){
      ex.printStackTrace();
    }

    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    System.out.println();
    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  static double f( double x ) {
    return ( 4.0/( 1.0 + x*x ) );
  }
}

