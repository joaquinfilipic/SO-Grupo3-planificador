/**
 * Created by joaquin on 10/05/17.
 */

public class Task {
    public enum TASKTYPE{ CPU, IO1, IO2, IO3 };
    private TASKTYPE taskType;
    private int taskTime;

    public Task(TASKTYPE type, int time){
        this.taskType = type;
        this.taskTime = time;
    }

    public TASKTYPE getTaskType(){
        return taskType;
    }
    public int getTaskTime(){
        return taskTime;
    }

    public void decreaseTaskTime(){
        taskTime--;
    }
    public boolean isTaskTimeCero(){
        if(taskTime == 0)
            return true;
        return false;
    }
}
