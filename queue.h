/* información de cada proceso */
typedef struct {
		int pNum;	/*process Number*/
        int aT;     /*arrival Time*/
        int cpuT;   /*cpu Time*/
    }Process;

typedef struct Node_t {
    Process p;
    struct Node_t *prev;
} NODE;

/* the HEAD of the Queue, hold the amount of node's that are in the queue*/
typedef struct Queue {
    NODE *head;
    NODE *tail;
    int size;
    int limit;
} Queue;

Queue *ConstructQueue(int limit);
void DestructQueue(Queue *queue);
int Enqueue(Queue *pQueue, NODE *item);
NODE * Dequeue(Queue *pQueue);
int isEmpty(Queue* pQueue);
/*devuelve un proceso que haya llegado en el tiempo indicado como 'key'*/
NODE * retrieve(Queue* pQueue, int key);