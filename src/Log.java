import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.PrintWriter;

/**
 * Created by joaquin on 28/05/17.
 */
public class Log {
    private static int time;
    private static ArrayList<Queue<KLT>> blockedQueuesArray; //always FIFO for blocked queues. 3 blocked queues.
    private static ArrayList<Process> readyProcessQueue;
    private static ArrayList<KLT> runningKLTsArray;

    public Log (){
        blockedQueuesArray = new ArrayList<>();
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        readyProcessQueue = new ArrayList<>();
        runningKLTsArray = new ArrayList<>();
    }

    public ArrayList<KLT> getRunningKLTsArray() {
        return runningKLTsArray;
    }

    public ArrayList<Process> getReadyProcessQueue() {
        return readyProcessQueue;
    }

    public ArrayList<Queue<KLT>> getBlockedQueuesArray() {
        return blockedQueuesArray;
    }

    public void setTime(int t){
        time = t;
    }

    public boolean checkRemainingThings(){
        if(readyProcessQueue.size() != 0){
            return true;
        }
        if(runningKLTsArray.size()!= 0){
            return true;
        }
        for(Queue<KLT> q : blockedQueuesArray){
            if(q.size() != 0){
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return ((Integer) time).toString();
    }

    public void showLog(PrintWriter file){
        file.printf("Time: %d\n", time);
        file.printf("  READY PROCESS QUEUE:\n");
        for(Process p : readyProcessQueue){
            file.printf("    Process ID: %d\n",p.getID());
        }
        for(int i = 0; i < blockedQueuesArray.size(); i++){
            file.printf("  BLOCKED QUEUE %d:\n", i+1);
            for(KLT k : blockedQueuesArray.get(i)){
                file.printf("    KLT ID: %d\n",k.getID());
            }
        }
        for(KLT k : runningKLTsArray){
            file.printf("  RUNNING IN CORE %d:\n", k.getAssignedCore().getID());
            file.printf("    KLT ID: %d\n",k.getID());
        }
        file.printf("-----\n");
    }
}
