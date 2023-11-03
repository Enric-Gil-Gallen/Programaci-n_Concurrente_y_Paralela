package e1;

import static e1.GUIPrimoSencillo1a.esPrimo;

public class miHebraEje4 extends Thread {
    long numero;

    public miHebraEje4(long num) {
        this.numero = num;
    }

    public void run(){
        System.out.println( "Examinando numero: " + numero );
        boolean primo = esPrimo( numero );
        if( primo ) {
            System.out.println( "El numero " + numero + " SI es primo." );
        } else {
            System.out.println( "El numero " + numero + " NO es primo." );
        }
    }
}
