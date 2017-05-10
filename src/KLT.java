import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class KLT {
    private int KLTID;
    private int arrivalTime;
    private ThreadLibrary threadLibrary;
    private Queue<Task> taskQueue;

    public KLT(int ID, int AT, ThreadLibrary TL){
        this.KLTID = ID;
        this. arrivalTime = AT;
        this.threadLibrary = TL;
        this.taskQueue = null;
    }
    public KLT(int ID, int AT, Queue<Task> TQ){
        this.KLTID = ID;
        this.arrivalTime = AT;
        this.taskQueue = TQ;
        this.threadLibrary = null;
    }

    public int getKLTID(){ return KLTID; }
    public int getArrivalTime(){ return arrivalTime; }
    public Queue<Task> getTaskQueue(){ return taskQueue; }
}
