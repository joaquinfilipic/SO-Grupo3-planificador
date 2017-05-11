import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class Scheduler {
    public enum ALGORITHM { FIFO, RR, SPR, SRT, HRRN };
    private ALGORITHM algorithm;
    private Queue<Thing> thingsQueue;

    public Scheduler(ALGORITHM alg, Queue<Thing> TQ){
        algorithm = alg;
        thingsQueue = TQ;
    }

    public void schedule(){}
}
