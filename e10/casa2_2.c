#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

void main(int argc, char * argv[]){
    MPI_Status s;
    int miId, dato, numProcs, total;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    MPI_Comm_rank(MPI_COMM_WORLD, &miId);

    // Comprobacion del numero de procesos.
    if( numProcs < 2 ) {
        if ( miId == 0 ) {
            fprintf( stderr, "\nError: Al menos se deben iniciar dos procesos\n\n" );
        }
        MPI_Finalize();
    }

    //Eje 2.2
    dato = numProcs - miId + 1;
    printf("Proceso %d -- Dato: %d\n", miId, dato);

    if(miId == 0){
        MPI_Send(&dato, 1, MPI_INT, 1, 88, MPI_COMM_WORLD);
        MPI_Recv(&dato, 1, MPI_INT, numProcs-1,88, MPI_COMM_WORLD, &s);
        printf("Eje 2.2 -- Suma total: %d\n", dato);
    }
    else{
        if (miId % 2 != 0) {
            dato = 0;
        }

        total = dato;
        MPI_Recv(&dato, 1, MPI_INT, miId-1,88, MPI_COMM_WORLD, &s);

        total += dato;
        dato = total;

        if(miId != numProcs-1){
            MPI_Send(&dato, 1, MPI_INT, miId+1, 88, MPI_COMM_WORLD);
        }
        else{
            MPI_Send(&dato, 1, MPI_INT, 0, 88, MPI_COMM_WORLD);
        }
    }

    MPI_Finalize();
}