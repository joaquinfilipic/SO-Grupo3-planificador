import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class ULT extends Thing {
    public enum ULTSTATE { NEW, READY}
    private ULTSTATE ultstate;
    private Queue<Task> taskQueue;
    private KLT parentKLT;
    private int waitingTime;

    public ULT(int id, int AT, Queue<Task> TQ){
        ID = id;
        arrivalTime = AT;
        ultstate = ULTSTATE.NEW;
        taskQueue = TQ;
        waitingTime = 0;
    }

    public Queue<Task> getTaskQueue(){ return taskQueue; }

    public KLT getParentKLT() { return parentKLT; }

    public ULTSTATE getUltstate() { return ultstate; }

    public int getWaitingTime(){ return waitingTime; }

    public void setParentKLT(KLT k){ parentKLT = k; }

    public void changeULTState(ULTSTATE s) { ultstate = s; }

    public void decreaseTaskTime(){
        taskQueue.peek().decreaseTaskTime();
    }

    public boolean isTaskTimeCero(){
        return taskQueue.peek().isTaskTimeCero();
    }

    public int getRemainingTime(){
        int sum = 0;
        if(ultstate != ULTSTATE.NEW) {
            for (Task t : taskQueue) {
                if (t.getTaskType() == Task.TASKTYPE.CPU) {
                    sum += t.getTaskTime();
                }
            }
        }
        return sum;
    }

    public void increaseWaitingTime(){ waitingTime++;}
}
