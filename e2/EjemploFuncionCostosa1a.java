package e2;

// ============================================================================
class EjemploFuncionCostosa1a {
// ============================================================================

    // --------------------------------------------------------------------------
    public static void main(String args[]) {
        int n, numHebras;
        long t1, t2;
        double sumaX, sumaY, ts, tc, tb, sp_c, sp_b;

        // Comprobacion y extraccion de los argumentos de entrada.
        if (args.length != 2) {
            System.err.println("Uso: java programa <numHebras> <tamanyo>");
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

        // Crea los vectores.
        double vectorX[] = new double[n];
        double vectorY[] = new double[n];

        //
        // Implementacion secuencial.
        //
        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);
        t1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            vectorY[i] = evaluaFuncion(vectorX[i]);
        }
        t2 = System.nanoTime();
        ts = ((double) (t2 - t1)) / 1.0e9;
        System.out.println("Tiempo secuencial (seg.):                    " + ts);
        imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);
        System.out.println("\n");

        //
        // Implementacion paralela ciclica.
        //
        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);
        t1 = System.nanoTime();
        // Gestion de hebras para la implementacion paralela ciclica

        // Crea y arranca el vector de hebras.
        Ej2_MyThreadCiclica[] vectorHebras = new Ej2_MyThreadCiclica[numHebras];
        for (int i = 0; i < numHebras; i++) {
            vectorHebras[i] = new Ej2_MyThreadCiclica(i, n, numHebras, vectorX, vectorY);
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

        t2 = System.nanoTime();
        tc = ((double) (t2 - t1)) / 1.0e9;
        sp_c = ts / tc;
        System.out.println("Tiempo paralela ciclica (seg.):              " + tc);
        System.out.println("Incremento paralela ciclica:                 " + sp_c );
        //imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);
        System.out.println("\n");

        //
        // Implementacion paralela por bloques.
        //
        //
        //

        inicializaVectorX(vectorX);
        inicializaVectorY(vectorY);
        t1 = System.nanoTime();

        // Crea y arranca el vector de hebras.
        Ej2_MyThreadBloques[] vectorHebras_2 = new Ej2_MyThreadBloques[numHebras];
        for (int i = 0; i < numHebras; i++) {
            vectorHebras_2[i] = new Ej2_MyThreadBloques(i, n, numHebras, vectorX, vectorY);
            vectorHebras_2[i].start();
        }

        // Espera a que terminen las hebras.

        for (int i = 0; i < numHebras; i++) {
            try {
                vectorHebras_2[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tb = ((double) (t2 - t1)) / 1.0e9;
        sp_b = ts / tb;
        System.out.println("Tiempo paralela bloques (seg.):              " + tb);
        System.out.println("Incremento paralela bloques:                 " + sp_b );
        //// imprimeResultado( vectorX, vectorY );
        // Comprueba el resultado.
        sumaX = sumaVector(vectorX);
        sumaY = sumaVector(vectorY);
        System.out.println("Suma del vector X:          " + sumaX);
        System.out.println("Suma del vector Y:          " + sumaY);


        System.out.println("Fin del programa.");
    }

    // --------------------------------------------------------------------------
    static void inicializaVectorX(double vectorX[]) {
        if (vectorX.length == 1) {
            vectorX[0] = 0.0;
        } else {
            for (int i = 0; i < vectorX.length; i++) {
                vectorX[i] = 10.0 * (double) i / ((double) vectorX.length - 1);
            }
        }
    }

    // --------------------------------------------------------------------------
    static void inicializaVectorY(double vectorY[]) {
        for (int i = 0; i < vectorY.length; i++) {
            vectorY[i] = 0.0;
        }
    }

    // --------------------------------------------------------------------------
    static double sumaVector(double vector[]) {
        double suma = 0.0;
        for (int i = 0; i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    // --------------------------------------------------------------------------
    static double evaluaFuncion(double x) {
        return Math.sin(Math.exp(-x) + Math.log1p(x));
    }

    // --------------------------------------------------------------------------
    static void imprimeVector(double vector[]) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println(" vector[ " + i + " ] = " + vector[i]);
        }
    }

    // --------------------------------------------------------------------------
    static void imprimeResultado(double vectorX[], double vectorY[]) {
        for (int i = 0; i < Math.min(vectorX.length, vectorY.length); i++) {
            System.out.println("  i: " + i +
                    "  x: " + vectorX[i] +
                    "  y: " + vectorY[i]);
        }
    }

}

