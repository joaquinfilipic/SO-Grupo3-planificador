import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class ULT extends Thing {
    private Queue<Task> taskQueue;

    public ULT(int id, int AT, Queue<Task> TQ){
        ID = id;
        arrivalTime = AT;
        taskQueue = TQ;
    }

    public Queue<Task> getTaskQueue(){
        return taskQueue;
    }
}
