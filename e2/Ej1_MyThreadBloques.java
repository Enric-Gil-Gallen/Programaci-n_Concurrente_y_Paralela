package e2;

public class Ej1_MyThreadBloques extends Thread{
    int miId, nElem, numHebras;

    public Ej1_MyThreadBloques(int miId, int nElem, int numHebras) {
        this.miId = miId;
        this.nElem = nElem;
        this.numHebras = numHebras;
    }

    // Implementacion paralela con distribucion ciclica
    public void run() {

        int tamBloque = (nElem + numHebras - 1) / numHebras;
        int iniElem = tamBloque * miId;
        int finElem = Math.min(iniElem + tamBloque, nElem);

        for (int i = iniElem; i < finElem; i++)
            System.out.print(i + " ");

        System.out.println("-------------------");
    }
}
