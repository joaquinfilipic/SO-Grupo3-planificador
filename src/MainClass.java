import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class MainClass {
    private static ArrayList<Process> processArray;
    private static Queue<KLT> readyQueue;
    private static ArrayList<Queue<KLT>> blockedQueuesArray;
    private static ArrayList<KLT> runningArray;
    private static ArrayList<Core> coresArray;
    private static Scheduler scheduler;
    private static int timer;
    private static char[][] matrix;

    public static void checkArrivals(){
        for(Process p: processArray){
            for(KLT klt: p.getKLTQueue()){
                if(klt.getArrivalTime() == timer){
                    readyQueue.add(klt);
                }
            }
        }
    }

    public static void checkBlockedQueues(){
        for(Queue<KLT> blockedQ: blockedQueuesArray){
            if(!blockedQ.isEmpty()){
                if(blockedQ.peek().isTaskTimeCero()){
                    blockedQ.peek().getTaskQueue().poll();
                    readyQueue.add(blockedQ.poll());
                }
            }
        }
    }

    public static void main(String[] args){
        //createEverything();
        for(timer = 0; timer < 100; timer++){
            checkBlockedQueues();
            checkArrivals();
            //schedule (no f****** idea)
            //looks for the next process to run. Returns a KLT (the process will choose it) and sends it to running


            for(Queue<KLT> blockedQ: blockedQueuesArray){
               if(!blockedQ.isEmpty()){
                   //fill matrix
                   blockedQ.peek().decreaseTaskTime();
               }
            }
            //fill matrix if any process is running, decrease CPU time and poll if necessary->send to blockedQ
            while(!runningArray.isEmpty()){
                KLT auxKLT = runningArray.get(0);
                //fill matrix
                auxKLT.decreaseTaskTime();
                if(!auxKLT.isTaskTimeCero()){
                    runningArray.add(auxKLT);
                }
                else{
                    auxKLT.getTaskQueue().poll();
                    if (auxKLT.getTaskQueue().peek() != null) {
                        Task.TASKTYPE auxTT = auxKLT.getTaskQueue().peek().getTaskType();
                        switch (auxTT) {
                            case CPU:
                                readyQueue.add(auxKLT);
                                break;
                            case IO1:
                                blockedQueuesArray.get(0).add(auxKLT);
                                break;
                            case IO2:
                                blockedQueuesArray.get(1).add(auxKLT);
                                break;
                            case IO3:
                                blockedQueuesArray.get(2).add(auxKLT);
                                break;
                        }
                    }
                }
            }
        }
    }
}
