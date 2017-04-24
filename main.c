#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

int main(){

	Queue *newQ = ConstructQueue(10);
	Queue *readyQ = ConstructQueue(10);
	/*No sé por qué una cola de running, por si se usa cuando haya 2 nucleos*/
	Queue *runningQ = ConstructQueue(2); 
	/* Queue *blockedQ = ConstructQueue(10); */
	NODE *pN1;
	NODE *pN2;
	NODE *pN3;
	int time;
	char matriz[3][20]; 

	/*matriz vacía*/
	for (int i=0; i<3; i++){
    	for (int j=0; j<20; j++){
    		matriz[i][j] = ' ';
    	}
    }

	/*Proceso 1*/
	pN1 = (NODE*) malloc(sizeof (NODE));
    pN1->p.pNum = 1;
    pN1->p.aT = 1;
    pN1->p.cpuT = 3;
    Enqueue(newQ, pN1);

    /*Proceso 2*/
	pN2 = (NODE*) malloc(sizeof (NODE));
	pN2->p.pNum = 2;
	pN2->p.aT = 0;
    pN2->p.cpuT = 4;
    Enqueue(newQ, pN2);

    /*Proceso 3*/
	pN3 = (NODE*) malloc(sizeof (NODE));
	pN3->p.pNum = 3;
	pN3->p.aT = 13;
    pN3->p.cpuT = 2;
    Enqueue(newQ, pN3);


    /*llenar matriz con datos de los procesos (v2)*/
    time = 0;
    for (int time; time<20; time ++){
    	NODE *pN;
    	NODE *auxN;
    	/*checkear llegadas de procesos*/
    	while( (auxN = retrieve(newQ, time))!= NULL ){
    		Enqueue(readyQ,auxN);
    	};

    	if ( isEmpty(runningQ) && !isEmpty(readyQ) ){
    			Enqueue(runningQ, Dequeue(readyQ));
    	}
    	if ( !isEmpty(runningQ) ){
    		pN = Dequeue(runningQ);
    		matriz[(pN->p.pNum)-1][time] = 'X';
    		(pN->p.cpuT)--;
    		if ((pN->p.cpuT)!=0){
    			Enqueue(runningQ, pN);
    		}
    	}
    }

    /*muestro matriz*/
    printf("\n");
    for (int i=0; i<3; i++){
    	for (int j=0; j<20; j++){
    		printf("%c", matriz[i][j]);
    	}
    	printf("\n");
    }
    printf("\n");
    DestructQueue(newQ);
    DestructQueue(readyQ);
    DestructQueue(runningQ);
    return 0;
}