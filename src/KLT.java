import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class KLT extends Thing {
    private Scheduler scheduler;
    private Queue<ULT> ULTQueue;
    private Queue<Task> taskQueue;
    private Core assignedCore;

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

    public Core getAssignedCore(){ return assignedCore; }

    public void assignCore(Core c){
        assignedCore = c;
    }
    public boolean checkCore(Core c){
        if(assignedCore == null){
            assignCore(c);
            return true;
        }
        else if(assignedCore == c)
            return true;
        return false;
    }

    public boolean hasULTs(){
        if(scheduler != null)
            return true;
        return false;
    }

    public void decreaseTaskTime(){
        if(!hasULTs()){
            taskQueue.peek().decreaseTaskTime();
        }
    }
    public boolean isTaskTimeCero(){
        if(!hasULTs()){
            return taskQueue.peek().isTaskTimeCero();
        }
        return false;
    }

    public void schedule() {
        if(scheduler != null){
            scheduler.schedule();
        }
    }
}
