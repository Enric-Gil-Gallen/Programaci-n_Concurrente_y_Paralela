package e2;

// ============================================================================
class EjemploMuestraNumeros1_1_ciclica {
// ============================================================================

    // --------------------------------------------------------------------------
    public static void main(String args[]) {
        int n, numHebras;

        // Comprobacion y extraccion de los argumentos de entrada.
        if (args.length != 2) {
            System.err.println("Uso: java programa <numHebras> <n>");
            System.exit(-1);
        }
        try {
            numHebras = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            numHebras = -1;
            n = -1;
            System.out.println("ERROR: Argumentos numericos incorrectos.");
            System.exit(-1);
        }

        // Crea y arranca el vector de hebras.
        Ej1_MyThreadCiclica[] vectorHebras = new Ej1_MyThreadCiclica[numHebras];
        for (int i = 0; i < numHebras; i++) {
            vectorHebras[i] = new Ej1_MyThreadCiclica(i, n, numHebras);
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

    }


}
