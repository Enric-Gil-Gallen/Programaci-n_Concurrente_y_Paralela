package e1;

class MiHebra extends Thread {
    int miId;
    public MiHebra( int miId ) {
        this.miId = miId;
    }
    public void run() {
        int num_impresiones = 1000;
        for (int i = 0; i < num_impresiones; i++){
            System.out.println("Mi identificador de hebra es: "+ miId);
        }
    }
}