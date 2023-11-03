#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"
#include "math.h"

void main(int argc, char * argv[]){
    MPI_Status s;
    int miId, dato, numProcs, total;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    MPI_Comm_rank(MPI_COMM_WORLD, &miId);

    //Eje 1.1
    dato = numProcs - miId - 1;
    printf("Proceso %d -- Dato: %d\n", miId, dato);

    if (miId == 0){
        total = dato;
        for (int i = 1; i < numProcs; i++){
            MPI_Recv(&dato, 1, MPI_INT, MPI_ANY_SOURCE,88, MPI_COMM_WORLD, &s);
            total += dato;
        }
        printf("Eje 1.1 -- Suma total: %d\n", total);
    }
    else{
        MPI_Send(&dato, 1, MPI_INT, 0,88, MPI_COMM_WORLD);
    }

    MPI_Finalize();
}