package e6;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

// ============================================================================
class EjemploPalabraMasUsada1a {
// ============================================================================

    // -------------------------------------------------------------------------
    public static void main(String args[]) {
        long t1, t2;
        double tSecuencial, t1SynchronizedMap, t2Hashtable, t3ConcurrentHashMap,
                t4ConcurrentHashMap, t5ConcurrentHashMapAtomica, t6ConcurrentHashMapAtomica
                ,t7Streams;
        int numHebras;
        String nombreFichero, palabraActual;
        Vector<String> vectorLineas;
        HashMap<String, Integer> hmCuentaPalabras;
        HashMap<String, Integer> maCuentaPalabras;
        Hashtable<String, Integer> htCuentaPalabras;
        ConcurrentHashMap<String, Integer> chCuentaPalabras;
        ConcurrentHashMap<String, AtomicInteger> chaCuentaPalabras;

        // Comprobacion y extraccion de los argumentos de entrada.

        if (args.length != 2) {
            System.err.println("Uso: java programa <numHebras> <fichero>");
            System.exit(-1);
        }

        try {
            numHebras = Integer.parseInt(args[0]);
            nombreFichero = args[1];
        } catch (NumberFormatException ex) {
            numHebras = -1;
            nombreFichero = "";
            System.out.println("ERROR: Argumentos numericos incorrectos.");
            System.exit(-1);
        }

        // Lectura y carga de lineas en "vectorLineas".
        vectorLineas = readFile(nombreFichero);
        System.out.println("Numero de lineas leidas: " + vectorLineas.size());
        System.out.println();

        //
        // Implementacion secuencial sin temporizar.
        //
        hmCuentaPalabras = new HashMap<String, Integer>(1000, 0.75F);
        for (int i = 0; i < vectorLineas.size(); i++) {
            // Procesa la linea "i".
            String[] palabras = vectorLineas.get(i).split("\\W+");
            for (int j = 0; j < palabras.length; j++) {
                // Procesa cada palabra de la linea "i", si es distinta de blancos.
                palabraActual = palabras[j].trim();
                if (palabraActual.length() >= 1) {
                    contabilizaPalabra(hmCuentaPalabras, palabraActual);
                }
            }
        }

        //
        // Implementacion secuencial.
        //
        t1 = System.nanoTime();
        hmCuentaPalabras = new HashMap<String, Integer>(1000, 0.75F);
        for (int i = 0; i < vectorLineas.size(); i++) {
            // Procesa la linea "i".
            String[] palabras = vectorLineas.get(i).split("\\W+");
            for (int j = 0; j < palabras.length; j++) {
                // Procesa cada palabra de la linea "i", si es distinta de blancos.
                palabraActual = palabras[j].trim();
                if (palabraActual.length() >= 1) {
                    contabilizaPalabra(hmCuentaPalabras, palabraActual);
                }
            }
        }
        t2 = System.nanoTime();
        tSecuencial = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. secuencial: ");
        imprimePalabraMasUsadaYVeces(hmCuentaPalabras);
        System.out.println(" Tiempo(s): " + tSecuencial);
        System.out.println("Num. elems. tabla hash: " + hmCuentaPalabras.size());
        System.out.println();


        //
        // Implementacion paralela 1: Uso de synchronizedMap.
        //
        t1 = System.nanoTime();

        maCuentaPalabras = new HashMap<String, Integer>(1000, 0.75F);
        Map<String, Integer> maCuentaPalabrasSynchronized = Collections.synchronizedMap(maCuentaPalabras);

        MiHebra_1[] h1 = new MiHebra_1[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h1[i] = new MiHebra_1(i, numHebras, maCuentaPalabrasSynchronized, vectorLineas);
            h1[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h1[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t1SynchronizedMap = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 1: ");
        imprimePalabraMasUsadaYVeces(maCuentaPalabras);
        System.out.println(" Tiempo(s): " + t1SynchronizedMap + " , Incremento " + tSecuencial / t1SynchronizedMap);
        System.out.println("Num. elems. tabla hash: " + maCuentaPalabras.size());
        System.out.println();


        //
        // Implementacion paralela 2: Uso de Hashtable.
        //
        t1 = System.nanoTime();

        htCuentaPalabras = new Hashtable<>(1000, 0.75F);
        MiHebra_2[] h2 = new MiHebra_2[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h2[i] = new MiHebra_2(i, numHebras, htCuentaPalabras, vectorLineas);
            h2[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h2[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t2Hashtable = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 2: ");
        imprimePalabraMasUsadaYVeces(htCuentaPalabras);
        System.out.println(" Tiempo(s): " + t2Hashtable + " , Incremento " + tSecuencial / t2Hashtable);
        System.out.println("Num. elems. tabla hash: " + maCuentaPalabras.size());
        System.out.println();


        //
        // Implementacion paralela 3: Uso de ConcurrentHashMap
        //
        t1 = System.nanoTime();

        chCuentaPalabras = new ConcurrentHashMap<>(1000, 0.75F);
        MiHebra_3[] h3 = new MiHebra_3[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h3[i] = new MiHebra_3(i, numHebras, chCuentaPalabras, vectorLineas);
            h3[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h3[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t3ConcurrentHashMap = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 3: ");
        imprimePalabraMasUsadaYVeces(chCuentaPalabras);
        System.out.println(" Tiempo(s): " + t3ConcurrentHashMap + " , Incremento " + tSecuencial / t3ConcurrentHashMap);
        System.out.println("Num. elems. tabla hash: " + chCuentaPalabras.size());
        System.out.println();


        //
        // Implementacion paralela 4: Uso de ConcurrentHashMap
        //
        t1 = System.nanoTime();

        chCuentaPalabras = new ConcurrentHashMap<>(1000, 0.75F);
        MiHebra_4[] h4 = new MiHebra_4[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h4[i] = new MiHebra_4(i, numHebras, chCuentaPalabras, vectorLineas);
            h4[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h4[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t4ConcurrentHashMap = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 4: ");
        imprimePalabraMasUsadaYVeces(chCuentaPalabras);
        System.out.println(" Tiempo(s): " + t4ConcurrentHashMap + " , Incremento " + tSecuencial / t4ConcurrentHashMap);
        System.out.println("Num. elems. tabla hash: " + chCuentaPalabras.size());
        System.out.println();


        //
        // Implementacion paralela 5: Uso de ConcurrentHashMap
        //
        t1 = System.nanoTime();

        chaCuentaPalabras = new ConcurrentHashMap<>(1000, 0.75F);
        MiHebra_5[] h5 = new MiHebra_5[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h5[i] = new MiHebra_5(i, numHebras, chaCuentaPalabras, vectorLineas);
            h5[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h5[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t5ConcurrentHashMapAtomica = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 5: ");
        imprimePalabraMasUsadaYVecesAtomicas(chaCuentaPalabras);
        System.out.println(" Tiempo(s): " + t5ConcurrentHashMapAtomica + " , Incremento " + tSecuencial / t5ConcurrentHashMapAtomica);
        System.out.println("Num. elems. tabla hash: " + chaCuentaPalabras.size());
        System.out.println();

        //
        // Implementacion paralela 6: Uso de ConcurrentHashMap
        //
        t1 = System.nanoTime();

        chaCuentaPalabras = new ConcurrentHashMap<>(1000, 0.75F, 256);
        MiHebra_6[] h6 = new MiHebra_6[numHebras];

        for (int i = 0; i < numHebras; i++) {
            h6[i] = new MiHebra_6(i, numHebras, chaCuentaPalabras, vectorLineas);
            h6[i].start();
        }

        try {
            for (int i = 0; i < numHebras; i++) {
                h6[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2 = System.nanoTime();
        t6ConcurrentHashMapAtomica = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 6: ");
        imprimePalabraMasUsadaYVecesAtomicas(chaCuentaPalabras);
        System.out.println(" Tiempo(s): " + t6ConcurrentHashMapAtomica + " , Incremento " + tSecuencial / t6ConcurrentHashMapAtomica);
        System.out.println("Num. elems. tabla hash: " + chaCuentaPalabras.size());
        System.out.println();

        //
        // Implementacion paralela 7: Uso de Streams
        t1 = System.nanoTime();
        Map<String, Long> stCuentaPalabras = vectorLineas.parallelStream()
                .filter(s -> s != null)
                .map(s -> s.split("\\W+"))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .filter(s -> (s.length() >= 1))
                .collect(groupingBy(s -> s, counting()));
        t2 = System.nanoTime();
        t7Streams = ((double) (t2 - t1)) / 1.0e9;
        System.out.print("Implemen. paralela 7: ");
        System.out.println(" Tiempo(s): " + t7Streams + " , Incremento " + tSecuencial / t7Streams);
        System.out.println("Num. elems. tabla hash: " + stCuentaPalabras.size());
        System.out.println();

        System.out.println("Fin de programa.");
    }

    // -------------------------------------------------------------------------
    public static Vector<String> readFile(String fileName) {
        BufferedReader br;
        String linea;
        Vector<String> data = new Vector<String>();

        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((linea = br.readLine()) != null) {
                //// System.out.println( "Leida linea: " + linea );
                data.add(linea);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    // -------------------------------------------------------------------------
    public static void contabilizaPalabra(
            HashMap<String, Integer> cuentaPalabras,
            String palabra) {
        Integer numVeces = cuentaPalabras.get(palabra);
        if (numVeces != null) {
            cuentaPalabras.put(palabra, numVeces + 1);
        } else {
            cuentaPalabras.put(palabra, 1);
        }
    }

    public static synchronized void contabilizaPalabraSynchronized(Map<String, Integer> cuentaPalabras, String palabra) {
        Integer numVeces = cuentaPalabras.get(palabra);
        if (numVeces != null) {
            cuentaPalabras.put(palabra, numVeces + 1);
        } else {
            cuentaPalabras.put(palabra, 1);
        }
    }

    public static synchronized void contabilizaPalabraHashtable(Hashtable<String, Integer> cuentaPalabras, String palabra) {
        Integer numVeces = cuentaPalabras.get(palabra);
        if (numVeces != null) {
            cuentaPalabras.put(palabra, numVeces + 1);
        } else {
            cuentaPalabras.put(palabra, 1);
        }
    }

    public static synchronized void contabilizaPalabraConcurrentHashMap(ConcurrentHashMap<String, Integer> cuentaPalabras, String palabra) {
        Integer numVeces = cuentaPalabras.get(palabra);
        if (numVeces != null) {
            cuentaPalabras.put(palabra, numVeces + 1);
        } else {
            cuentaPalabras.put(palabra, 1);
        }
    }

    public static void contabilizaPalabraConcurrentHashMapEje4(ConcurrentHashMap<String, Integer> cuentaPalabras, String palabra) {
        Integer numVeces;
        int num;
        boolean cambio;

        numVeces = cuentaPalabras.putIfAbsent(palabra, 1);
        if (numVeces != null) {
            num = numVeces;
            while (true) {
                cambio = cuentaPalabras.replace(palabra, num, num + 1);

                if (cambio) break;
                num = cuentaPalabras.get(palabra);
            }
        }
    }

    public static void contabilizaPalabraConcurrentHashMapAtomica(ConcurrentHashMap<String, AtomicInteger> cuentaPalabras, String palabra) {
        AtomicInteger numVeces;

        numVeces = cuentaPalabras.putIfAbsent(palabra, new AtomicInteger(1));
        if (numVeces != null) {
            numVeces.getAndIncrement();
        }
    }

    // --------------------------------------------------------------------------
    static void imprimePalabraMasUsadaYVeces(
            Map<String, Integer> cuentaPalabras) {
        Vector<Map.Entry> lista =
                new Vector<Map.Entry>(cuentaPalabras.entrySet());

        String palabraMasUsada = "";
        int numVecesPalabraMasUsada = 0;
        // Calcula la palabra mas usada.
        for (int i = 0; i < lista.size(); i++) {
            String palabra = (String) lista.get(i).getKey();
            int numVeces = (Integer) lista.get(i).getValue();
            if (i == 0) {
                palabraMasUsada = palabra;
                numVecesPalabraMasUsada = numVeces;
            } else if (numVecesPalabraMasUsada < numVeces) {
                palabraMasUsada = palabra;
                numVecesPalabraMasUsada = numVeces;
            }
        }
        // Imprime resultado.
        System.out.print("( Palabra: '" + palabraMasUsada + "' " +
                "veces: " + numVecesPalabraMasUsada + " )");
    }

    static void imprimePalabraMasUsadaYVecesAtomicas(Map<String, AtomicInteger> cuentaPalabras) {
        Vector<Map.Entry> lista = new Vector<Map.Entry>(cuentaPalabras.entrySet());

        String palabraMasUsada = "";
        int numVecesPalabraMasUsada = 0;
        // Calcula la palabra mas usada.
        for (int i = 0; i < lista.size(); i++) {
            String palabra = (String) lista.get(i).getKey();
            AtomicInteger numVecesAtomica = (AtomicInteger) lista.get(i).getValue();
            int numVeces = numVecesAtomica.get();
            if (i == 0) {
                palabraMasUsada = palabra;
                numVecesPalabraMasUsada = numVeces;
            } else if (numVecesPalabraMasUsada < numVeces) {
                palabraMasUsada = palabra;
                numVecesPalabraMasUsada = numVeces;
            }
        }
        // Imprime resultado.
        System.out.print("( Palabra: '" + palabraMasUsada + "' " +
                "veces: " + numVecesPalabraMasUsada + " )");
    }

    // --------------------------------------------------------------------------
    static void printCuentaPalabrasOrdenadas(
            HashMap<String, Integer> cuentaPalabras) {
        int i, numVeces;
        List<Map.Entry> list = new Vector<Map.Entry>(cuentaPalabras.entrySet());

        // Ordena por valor.
        Collections.sort(
                list,
                new Comparator<Map.Entry>() {
                    public int compare(Map.Entry e1, Map.Entry e2) {
                        Integer i1 = (Integer) e1.getValue();
                        Integer i2 = (Integer) e2.getValue();
                        return i2.compareTo(i1);
                    }
                }
        );
        // Muestra contenido.
        i = 1;
        System.out.println("Veces Palabra");
        System.out.println("-----------------");
        for (Map.Entry e : list) {
            numVeces = ((Integer) e.getValue()).intValue();
            System.out.println(i + " " + e.getKey() + " " + numVeces);
            i++;
        }
        System.out.println("-----------------");
    }
}


