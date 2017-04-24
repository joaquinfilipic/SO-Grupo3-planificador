#include <stdlib.h>
#include <stdio.h>

#define TRUE  1
#define FALSE	0

/* información de cada proceso */
typedef struct {
        int pNum;   /*process Number*/
        int aT;     /*arrival Time*/
        int cpuT;   /*cpu Time*/
    } Process;

typedef struct Node_t {
    Process p;
    struct Node_t *next;
} NODE;

typedef struct Queue {
    NODE *head;
    NODE *tail;
    int size;
    int limit;
} Queue;


Queue *ConstructQueue(int limit);
void DestructQueue(Queue *queue);
int Enqueue(Queue *pQueue, NODE *item);
NODE *Dequeue(Queue *pQueue);
int isEmpty(Queue* pQueue);
/*devuelve un proceso que haya llegado en el tiempo indicado como 'key'*/
NODE * retrieve(Queue* pQueue, int key);

Queue *ConstructQueue(int limit) {
    Queue *queue = (Queue*) malloc(sizeof (Queue));
    if (queue == NULL) {
        return NULL;
    }
    if (limit <= 0) {
        limit = 65535;
    }
    queue->limit = limit;
    queue->size = 0;
    queue->head = NULL;
    queue->tail = NULL;

    return queue;
}

void DestructQueue(Queue *queue) {
    NODE *pN;
    while (!isEmpty(queue)) {
        pN = Dequeue(queue);
        free(pN);
    }
    free(queue);
}

int Enqueue(Queue *pQueue, NODE *item) {
    /* Parámetro erróneo */
    if ((pQueue == NULL) || (item == NULL)) {
        return FALSE;
    }
    /* if(pQueue->limit != 0)*/
    if (pQueue->size >= pQueue->limit) {
        return FALSE;
    }
    /*cola vacía*/
    item->next = NULL;
    if (pQueue->size == 0) {
        pQueue->head = item;
        pQueue->tail = item;

    } else {
        /*agregar al final de la cola*/
        pQueue->tail->next = item;
        pQueue->tail = item;
    }
    pQueue->size++;
    return TRUE;
}

NODE * Dequeue(Queue *pQueue) {
    /*cola vacía o parámetro erróneo*/
    NODE *item;
    if (isEmpty(pQueue))
        return NULL;
    item = pQueue->head;
    pQueue->head = (pQueue->head)->next;
    pQueue->size--;
    return item;
}

int isEmpty(Queue* pQueue) {
    if (pQueue == NULL) {
        return FALSE;
    }
    if (pQueue->size == 0) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/*devuelve un proceso que haya llegado en el tiempo indicado como 'key'*/
NODE * retrieve(Queue* pQueue, int key) {
    if (pQueue == NULL) {
        return NULL;
    }
   NODE *current = pQueue->head;
   NODE *previous = NULL;
   if(pQueue->head == NULL) {
      return NULL;
   }
   while(current->p.aT != key) {
      if(current->next == NULL) {
         return NULL;
      } else {
         previous = current;
         current = current->next;
      }
   }
   /*proceso encontrado*/
   if(current == pQueue->head) {
      pQueue->head = pQueue->head->next;
   } else {
      previous->next = current->next;
   } 
   pQueue->size--;   
   return current;
}