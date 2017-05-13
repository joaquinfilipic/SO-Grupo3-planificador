import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class Process extends Thing {
    private Queue<KLT> KLTQueue;
    private Scheduler scheduler;

    public Process(int id, int AT, Queue<KLT> q, Scheduler s){
        ID = id;
        arrivalTime = AT;
        KLTQueue = q;
        scheduler = s;
    }

    public Queue<KLT> getKLTQueue(){
        return KLTQueue;
    }
    public Scheduler getScheduler() {
        return scheduler;
    }

    public void schedule(){
        scheduler.schedule();
    }
}
