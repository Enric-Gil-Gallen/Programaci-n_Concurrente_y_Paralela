package e1;

public class Ejercicio2 {
    public static void main( String args[] ){
        MiHebraEje2 h0 = new MiHebraEje2(0, 1,10);
        MiHebraEje2 h1 = new MiHebraEje2(1,1,2);
        h0.start();
        System.out.println("Empieza hebra 1");
        h1.start();
        System.out.println("Empieza hebra 2");
        try{
            h0.join();
            h1.join();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Acaba hebra 1");
        System.out.println("Acaba hebra 2");

    }


}