package e1;

class miHebraEj3 extends Thread{
    int miId;
    CuentaIncrementos1a contador;

    public miHebraEj3(int miId, CuentaIncrementos1a contador){
        this.miId = miId;
        this.contador = contador;
    }

    public void run(){
        System.out.println("Empiezo hebra: " + miId);
        for(int i = 0; i<1000000; i++){
            contador.incrementaContador();
        }
        System.out.println("Termino hebra: " + miId);
    }
}