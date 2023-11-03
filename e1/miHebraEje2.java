package e1;

class MiHebraEje2 extends Thread {
    int miId;
    int num1;
    int num2;
    int actual = 0;
    public MiHebraEje2(int miId, int num1, int num2) {
        this.miId = miId;
        this.num1 = num1;
        this.num2 = num2;
    }

    public void run() {
        for (int t = 0; t < 1000000; t++){
            for (int i = num1; i < num2; i++){
                actual += i;
            }
        }

        System.out.println("Mi hebra es: "+miId+" -- Suma: " + actual);
    }
}