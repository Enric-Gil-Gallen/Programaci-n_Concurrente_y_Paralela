package e1;

class EjemploCreacionRunnable {
  public static void main( String args[] ) {
    new Thread( new MiRun(0)).start();
    new Thread( new MiRun(1)).start();
  }
}
