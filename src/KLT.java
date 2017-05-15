import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class KLT extends Thing {
    public enum KLTSTATE { BLOCKED, READY, RUNNING}
    private KLTSTATE kltstate;
    private Process parentProcess;
    private Scheduler scheduler;
    private ArrayList<Thing> ULTArray;
    private Queue<Task> taskQueue;
    private Core assignedCore;

    public KLT(int id, int AT, Scheduler s, ArrayList<Thing> a){
        ID = id;
        kltstate = KLTSTATE.READY;
        arrivalTime = AT;
        scheduler = s;
        ULTArray = a;
        taskQueue = null;
    }
    public KLT(int id, int AT, Queue<Task> TQ){
        ID = id;
        kltstate = KLTSTATE.READY;
        arrivalTime = AT;
        taskQueue = TQ;
        scheduler = null;
        ULTArray = null;
    }

    public void setParentProcess(Process p){
        parentProcess = p;
    }

    public Process getParentProcess(){ return parentProcess; }

    public Queue<Task> getTaskQueue(){
        return taskQueue;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Core getAssignedCore(){ return assignedCore; }

    public KLTSTATE getKltstate(){ return kltstate; }

    public void changeState(KLTSTATE s){
        kltstate = s;
    }

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

    public void schedule(Scheduler.ALGORITHM alg) {
        if(scheduler != null){
            scheduler.schedule(Scheduler.ALGORITHM.FIFO, ULTArray);
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        KLT klt = (KLT) o;
        return ID == klt.getID();
    }
}
