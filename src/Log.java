import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    public void showLog(){
        System.out.printf("Time: %d\n", time);
        System.out.printf("  READY PROCESS QUEUE:\n");
        for(Process p : readyProcessQueue){
            System.out.printf("    Process ID: %d\n",p.getID());
        }
        for(int i = 0; i < blockedQueuesArray.size(); i++){
            System.out.printf("  BLOCKED QUEUE %d:\n", i+1);
            for(KLT k : blockedQueuesArray.get(i)){
                System.out.printf("    KLT ID: %d\n",k.getID());
            }
        }
        for(KLT k : runningKLTsArray){
            System.out.printf("  RUNNING IN CORE %d:\n", k.getAssignedCore().getID());
            System.out.printf("    KLT ID: %d\n",k.getID());
        }
    }
}
