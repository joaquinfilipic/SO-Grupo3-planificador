/**
 * Created by joaquin on 06/05/17.
 */

public class Tarea {
    public enum TIPOTAREA{ CPU, IO1, IO2, IO3 };
    private TIPOTAREA taskTipe;
    private int taskTime;

    public Tarea (TIPOTAREA type, int time){
        this.taskTipe = type;
        this.taskTime = time;
    }

    public TIPOTAREA getTaskTipe(){ return taskTipe; }
    public int getTaskTime(){ return taskTime; }
    public void decreaseTaskTime(){
        taskTime--;
    }
    public boolean isTaskTimeCero(){
        if(taskTime == 0)
            return true;
        return false;
    }
}
