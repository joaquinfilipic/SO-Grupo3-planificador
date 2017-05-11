import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class KLT extends Thing {
    private Scheduler scheduler;
    private Queue<ULT> ULTQueue;
    private Queue<Task> taskQueue;

    public KLT(int id, int AT, Scheduler s){
        ID = id;
        arrivalTime = AT;
        scheduler = s;
        taskQueue = null;
    }
    public KLT(int id, int AT, Queue<Task> TQ){
        ID = id;
        arrivalTime = AT;
        taskQueue = TQ;
        scheduler = null;
    }

    public Queue<Task> getTaskQueue(){
        return taskQueue;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
