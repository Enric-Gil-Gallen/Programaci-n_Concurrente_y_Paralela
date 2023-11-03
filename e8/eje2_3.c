#include <stdio.h>
#include "mpi.h"

void main(int argc, char * argv[]){
    MPI_Status s;
    int numProcs, miId, i;
    float num = -1;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    MPI_Comm_rank(MPI_COMM_WORLD, &miId);

    if( miId == 0){
        printf ("Dame numero : ") ;
        scanf ("%f" ,&num ) ;
        for (i=1; i < numProcs; i++){
            MPI_Send (&num, 1,MPI_INT,i ,88, MPI_COMM_WORLD);
        }

    } else{
        MPI_Recv(&num, 1,MPI_INT,0 ,88, MPI_COMM_WORLD, &s);
    }

    printf("Proceso %d con n = %f \n",miId, num);

    MPI_Finalize () ;
}
