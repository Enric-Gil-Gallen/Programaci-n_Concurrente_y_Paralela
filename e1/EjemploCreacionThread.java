package e1;

class EjemploCreacionThread {
  public static void main( String args[] ) {
    new  MiHebra(0).start();
    new  MiHebra(1).start();
  }
}
