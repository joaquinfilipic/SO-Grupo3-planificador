import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class MainClass {
    private static ArrayList<Process> processArray;
    private static ArrayList<KLT> readyQueue;
    private static ArrayList<Queue<KLT>> blockedQueuesArray; //always FIFO for blocked queues
    private static ArrayList<Core> coresArray;
    private static Scheduler scheduler;
    private static int timer;
    private static char[][] matrix;

    public static void checkArrivals(){
        for(Process p: processArray){
            for(Thing klt: p.getKLTArray()){
                if(klt.getArrivalTime() == timer)
                    readyQueue.add((KLT) klt);
            }
        }
    }
    public static void checkBlockedQueues(){
        if(blockedQueuesArray == null)
            return;
        for(Queue<KLT> blockedQ: blockedQueuesArray){
            if(blockedQ != null && !blockedQ.isEmpty()){
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
            //Auxiliary variables
            ArrayList<Thing> pArray = new ArrayList<>();
            ArrayList<Thing> kltArray = new ArrayList<>();
            Process auxP;
            KLT auxKLT;

            checkBlockedQueues();
            checkArrivals();

            //////////////////////////////////////////////////////////////////////
            //BEGINNING
            // scheduling
            //looks for the next process to run.
            for(KLT klt: readyQueue){
                if(!pArray.contains(klt.getParentProcess()))
                    pArray.add(klt.getParentProcess());
            }
            int i = 0;
            int size = pArray.size();
            int freeCores = coresArray.size();
            while ( (i < size) && (i < freeCores)){
                auxP = (Process) scheduler.schedule(Scheduler.ALGORITHM.FIFO, pArray);
                pArray.remove(auxP);
                for(Thing klt: auxP.getKLTArray()){
                    kltArray.add((KLT)klt);
                }
                while( i < freeCores ){
                    auxKLT = (KLT) auxP.getScheduler().schedule(Scheduler.ALGORITHM.FIFO, auxP.getKLTArray());
                    kltArray.remove(auxKLT);
                    int j = 0;
                    while(j < coresArray.size() && i < freeCores) {
                        if (auxKLT.checkCore(coresArray.get(j))) {
                            coresArray.get(j).assignRunningKLT(auxKLT);
                            i++;
                        }
                        j++;
                    }
                }
            }
            //END
            ///////////////////////////////////////////////////////////////////////////

            for(Queue<KLT> blockedQ: blockedQueuesArray){
               if(!blockedQ.isEmpty()){
                   //fill matrix
                   blockedQ.peek().decreaseTaskTime();
               }
            }
            //fill matrix if any process is running, decrease CPU time and poll if necessary->send to blockedQ
            for(int k = 0; k < coresArray.size(); k++) {
                if (!coresArray.get(k).isFree()) {
                    KLT auxKLT2 = coresArray.get(k).getRunningKLT();
                    //fill matrix
                    auxKLT2.decreaseTaskTime();
                    if (!auxKLT2.isTaskTimeCero()) {
                        readyQueue.add(0, auxKLT2);
                    } else {
                        auxKLT2.getTaskQueue().poll();
                        if (auxKLT2.getTaskQueue().peek() != null) {
                            Task.TASKTYPE auxTT = auxKLT2.getTaskQueue().peek().getTaskType();
                            switch (auxTT) {
                                case CPU:
                                    readyQueue.add(0, auxKLT2);
                                    break;
                                case IO1:
                                    blockedQueuesArray.get(0).add(auxKLT2);
                                    break;
                                case IO2:
                                    blockedQueuesArray.get(1).add(auxKLT2);
                                    break;
                                case IO3:
                                    blockedQueuesArray.get(2).add(auxKLT2);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}
