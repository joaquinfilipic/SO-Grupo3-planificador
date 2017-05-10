import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class ULT {
    private int ULTID;
    private int arrivalTime;
    private Queue<Task> taskQueue;

    public ULT(int ID, int AT, Queue<Task> TQ){
        this.ULTID = ID;
        this.arrivalTime = AT;
        this.taskQueue = TQ;
    }

    public int getULTID(){ return ULTID; }
    public int getArrivalTime(){ return arrivalTime; }
    public Queue<Task> getTaskQueue(){ return taskQueue; }
}
