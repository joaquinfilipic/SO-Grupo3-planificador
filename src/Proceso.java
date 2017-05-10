import java.util.Queue;

/**
 * Created by joaquin on 01/05/17.
 */

public class Proceso {
    private int pNum;   //Process Number
    private int aT;     //Arrival Time
    private Queue<Tarea> taskQ;
    //private int cpuT;   //CPU Time

    public Proceso(int pNum, int aT, Queue<Tarea> q){
        this.pNum = pNum;
        this.aT = aT;
        this.taskQ = q;
    }

    public int getPNum(){
        return pNum;
    }
    public int getAT(){
        return aT;
    }
    public Queue<Tarea> getTaskQ(){ return taskQ; }

    public boolean blockedTimeFinish(){
        if (taskQ.peek() != null && taskQ.peek().getTaskType() != Tarea.TIPOTAREA.CPU && isTaskTCero()){
            return true;
        }
        else return false;
    }
    /*public int getCPUT(){
        return cpuT;
    }*/
    public void decreaseTaskT(){
            taskQ.peek().decreaseTaskTime();
    }
    public boolean isTaskTCero(){
            if (taskQ.peek().isTaskTimeCero())
                return true;
            return false;
    }
}
