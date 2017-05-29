import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class KLT extends Thing {
    public enum KLTSTATE { NEW, BLOCKED, READY, RUNNING}
    private KLTSTATE kltstate;
    private Process parentProcess;
    private Scheduler scheduler;
    private Scheduler.ALGORITHM algorithm;
    private ArrayList<Thing> ULTArray, ULTQueue;
    private Queue<Task> taskQueue;
    private Core assignedCore;
    private ULT currentULT, prevULTRunning;
    private int quantum; //default quantum time = 1
    private int remainingQ;

    public KLT(int id, Scheduler s, Scheduler.ALGORITHM alg, ArrayList<Thing> a){
        ID = id;
        kltstate = KLTSTATE.NEW;
        scheduler = s;
        algorithm = alg;
        quantum = 1;
        remainingQ = quantum;
        ULTArray = a;
        ULTQueue = new ArrayList<>();
        taskQueue = null;
        currentULT = null;
        prevULTRunning = null;
        arrivalTime = ULTArray.get(0).getArrivalTime();
        for(Thing u : ULTArray){
            int n = ((ULT)u).getArrivalTime();
            if(n < arrivalTime){
                arrivalTime = n;
            }
        }
    }
    public KLT(int id, int AT, Queue<Task> TQ){
        ID = id;
        kltstate = KLTSTATE.NEW;
        arrivalTime = AT;
        taskQueue = TQ;
        scheduler = null;
        ULTArray = null;
    }


    public Process getParentProcess(){ return parentProcess; }

    public Queue<Task> getTaskQueue(){
        return taskQueue;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ArrayList<Thing> getULTArray() { return ULTArray; }

    public ArrayList<Thing> getULTQueue() { return ULTQueue; }

    public Core getAssignedCore(){ return assignedCore; }

    public KLTSTATE getKltstate(){ return kltstate; }

    public Scheduler.ALGORITHM getAlgorithm() { return algorithm; }

    public ULT getCurrentULT() { return currentULT; }

    public ULT getPrevULTRunning() { return prevULTRunning; }

    public int getQuantum() { return quantum; }

    public int getRemainingQ() { return remainingQ; }

    @Override
    public int getArrivalTime(){
        if(!hasULTs()){
            return arrivalTime;
        }
        else {
            int min = ULTArray.get(0).getArrivalTime();
            for(Thing u : ULTArray){
                if( min > u.getArrivalTime()){
                    min = u.getArrivalTime();
                }
            }
            return min;
        }
    }

    public void setParentProcess(Process p){
        parentProcess = p;
    }

    public void changeState(KLTSTATE s){
        kltstate = s;
    }

    public void assignCore(Core c){
        assignedCore = c;
    }

    public void setCurrentULT(ULT u) { currentULT = u; }

    public void setPrevULTRunning(ULT u) { prevULTRunning = u; }

    public void setQuantum(int i) {
        quantum = i;
        remainingQ = i;
    }

    public void setRemainingQ(int i) { remainingQ = i; }

    public void decreaseRemainingQ() { remainingQ--; }

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
        else {
            currentULT.decreaseTaskTime();
        }
    }
    public boolean isTaskTimeCero(){
        if(!hasULTs()){
            return taskQueue.peek().isTaskTimeCero();
        }
        else {
            return currentULT.isTaskTimeCero();
        }
    }

    public int getRemainingTime(){
        int sum = 0;
        if(kltstate != KLTSTATE.NEW) {
            if (!hasULTs()) {
                for (Task t : taskQueue) {
                    if (t.getTaskType() == Task.TASKTYPE.CPU) {
                        sum += t.getTaskTime();
                    }
                }
            } else {
                for (Thing ult : ULTArray) {
                    sum += ((ULT) ult).getRemainingTime();
                }
            }
        }
        return sum;
    }

    public void increaseWaitingTime(){
        for(Thing ult : ULTQueue){
            if ( !((ULT)ult).equals(currentULT) ){
                ((ULT)ult).increaseWaitingTime();
            }
        }
    }

    public void setReadyULTs(int t){
        for (Thing ult : ULTArray){
            if(ult.getArrivalTime() == t){
                ((ULT)ult).changeULTState(ULT.ULTSTATE.READY);
                ULTQueue.add(ult);
                //Only for SRT -> Preemptive at arrival
                if(algorithm == Scheduler.ALGORITHM.SRT){
                    for(int i = 0; i < ULTQueue.size() - 1; i++){
                        for(int j = i + 1; j < ULTQueue.size(); j++){
                            if(((ULT)ULTQueue.get(i)).getRemainingTime() >= ((ULT)ULTQueue.get(j)).getRemainingTime()){
                                ULT auxU = (ULT)ULTQueue.get(i);
                                ULT auxU2 = (ULT)ULTQueue.get(j);
                                ULTQueue.set(i, auxU2);
                                ULTQueue.set(j, auxU);

                            }
                        }
                    }
                }
            }
        }
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
