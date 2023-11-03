#include <stdio.h>
#include "mpi.h"

void main(int argc, char * argv[]){
    MPI_Status s;
    int numProcs, miId;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    MPI_Comm_rank(MPI_COMM_WORLD, &miId);

    printf("Hola, soy el proceso %d de los %d \n",miId, numProcs);

    MPI_Finalize () ;
}
