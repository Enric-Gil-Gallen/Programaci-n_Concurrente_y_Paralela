#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

void main(int argc, char * argv[]){
    MPI_Status s;
    int miId, dato, numProcs, total, suma;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    MPI_Comm_rank(MPI_COMM_WORLD, &miId);

    //Eje 2.3
    dato = numProcs - miId + 1;
    printf("Proceso %d -- Dato: %d\n", miId, dato);

    total = (miId % 2 == 0 ? dato : 0);
    MPI_Reduce(&total,&suma, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    if (miId == 0){
        printf("Eje 2.3 -- Suma total: %d\n", suma);
    }
    MPI_Finalize();
}