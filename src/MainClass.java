import java.util.ArrayList;
import java.util.LinkedList;
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

    //EXAMPLE
    public static void createEverything(){
        readyQueue = new ArrayList<>();
        blockedQueuesArray = new ArrayList<>();
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        coresArray = new ArrayList<>();
        Core core1 = new Core(1);
        Core core2 = new Core(2);
        coresArray.add(core1);
        coresArray.add(core2);
        scheduler = new Scheduler();
        matrix = new char[8][50];
        //Matriz vacía
        for (int i=0; i<8; i++){
            for (int j=0; j<50; j++){
                matrix[i][j] = ' ';
            }
        }
        createProcessesKLTsAndTasks();

    }

    public static void createProcessesKLTsAndTasks(){
        //TASKS
        Task t1K1 = new Task(Task.TASKTYPE.CPU, 3);
        Task t2K1 = new Task(Task.TASKTYPE.IO1, 1);
        Task t3K1 = new Task(Task.TASKTYPE.CPU, 2);
        Task t4K1 = new Task(Task.TASKTYPE.IO2, 2);
        Task t5K1 = new Task(Task.TASKTYPE.CPU, 1);
        Queue<Task> k1TaskQ = new LinkedList<>();
        k1TaskQ.add(t1K1);
        k1TaskQ.add(t2K1);
        k1TaskQ.add(t3K1);
        k1TaskQ.add(t4K1);
        k1TaskQ.add(t5K1);
        Task t1K2 = new Task(Task.TASKTYPE.CPU, 2);
        Task t2K2 = new Task(Task.TASKTYPE.IO1, 2);
        Task t3K2 = new Task(Task.TASKTYPE.CPU, 2);
        Task t4K2 = new Task(Task.TASKTYPE.IO2, 1);
        Task t5K2 = new Task(Task.TASKTYPE.CPU, 3);
        Queue<Task> k2TaskQ = new LinkedList<>();
        k2TaskQ.add(t1K2);
        k2TaskQ.add(t2K2);
        k2TaskQ.add(t3K2);
        k2TaskQ.add(t4K2);
        k2TaskQ.add(t5K2);
        Task t1K3 = new Task(Task.TASKTYPE.CPU, 1);
        Task t2K3 = new Task(Task.TASKTYPE.IO1, 2);
        Task t3K3 = new Task(Task.TASKTYPE.CPU, 1);
        Task t4K3 = new Task(Task.TASKTYPE.IO2, 2);
        Task t5K3 = new Task(Task.TASKTYPE.CPU, 4);
        Queue<Task> k3TaskQ = new LinkedList<>();
        k3TaskQ.add(t1K3);
        k3TaskQ.add(t2K3);
        k3TaskQ.add(t3K3);
        k3TaskQ.add(t4K3);
        k3TaskQ.add(t5K3);
        Task t1K4 = new Task(Task.TASKTYPE.CPU, 2);
        Task t2K4 = new Task(Task.TASKTYPE.IO1, 1);
        Task t3K4 = new Task(Task.TASKTYPE.CPU, 3);
        Task t4K4 = new Task(Task.TASKTYPE.IO2, 2);
        Task t5K4 = new Task(Task.TASKTYPE.CPU, 1);
        Queue<Task> k4TaskQ = new LinkedList<>();
        k4TaskQ.add(t1K4);
        k4TaskQ.add(t2K4);
        k4TaskQ.add(t3K4);
        k4TaskQ.add(t4K4);
        k4TaskQ.add(t5K4);
        Task t1K5 = new Task(Task.TASKTYPE.CPU, 2);
        Task t2K5 = new Task(Task.TASKTYPE.IO1, 3);
        Task t3K5 = new Task(Task.TASKTYPE.CPU, 1);
        Task t4K5 = new Task(Task.TASKTYPE.IO2, 1);
        Task t5K5 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k5TaskQ = new LinkedList<>();
        k5TaskQ.add(t1K5);
        k5TaskQ.add(t2K5);
        k5TaskQ.add(t3K5);
        k5TaskQ.add(t4K5);
        k5TaskQ.add(t5K5);
        Task t1K6 = new Task(Task.TASKTYPE.CPU, 3);
        Task t2K6 = new Task(Task.TASKTYPE.IO1, 1);
        Task t3K6 = new Task(Task.TASKTYPE.CPU, 1);
        Task t4K6 = new Task(Task.TASKTYPE.IO2, 2);
        Task t5K6 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k6TaskQ = new LinkedList<>();
        k6TaskQ.add(t1K6);
        k6TaskQ.add(t2K6);
        k6TaskQ.add(t3K6);
        k6TaskQ.add(t4K6);
        k6TaskQ.add(t5K6);

        //KLTs
        KLT k1 = new KLT(1, 0, k1TaskQ);
        KLT k2 = new KLT(2, 1, k2TaskQ);
        KLT k3 = new KLT(3, 0, k3TaskQ);
        KLT k4 = new KLT(4, 1, k4TaskQ);
        KLT k5 = new KLT(5, 2, k5TaskQ);
        KLT k6 = new KLT(6, 4, k6TaskQ);

        //PROCESSES
        ArrayList<Thing> p1ArrayT = new ArrayList<>();
        p1ArrayT.add(k1);
        p1ArrayT.add(k2);
        ArrayList<Thing> p2ArrayT = new ArrayList<>();
        p2ArrayT.add(k3);
        p2ArrayT.add(k4);
        p2ArrayT.add(k5);
        ArrayList<Thing> p3ArrayT = new ArrayList<>();
        p3ArrayT.add(k6);
        Process p1 = new Process(1, p1ArrayT, new Scheduler());
        Process p2 = new Process(2, p2ArrayT, new Scheduler());
        Process p3 = new Process(3, p3ArrayT, new Scheduler());

        k1.setParentProcess(p1);
        k2.setParentProcess(p1);
        k3.setParentProcess(p2);
        k4.setParentProcess(p2);
        k5.setParentProcess(p2);
        k6.setParentProcess(p3);

        processArray = new ArrayList<>();
        processArray.add(p1);
        processArray.add(p2);
        processArray.add(p3);
    }

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
                    blockedQ.peek().changeState(KLT.KLTSTATE.READY);
                    readyQueue.add(blockedQ.poll());
                }
            }
        }
    }

    public static void main(String[] args){
        createEverything();


        for(timer = 0; timer < 50; timer++){

            //Auxiliary variables
            ArrayList<Thing> pArray = new ArrayList<>();
            ArrayList<Thing> kltArray = new ArrayList<>();
            Process auxP;
            KLT auxKLT;

            checkBlockedQueues();
            checkArrivals();

            System.out.printf("Time %d, klts in ready %d\n",timer, readyQueue.size());
            //////////////////////////////////////////////////////////////////////
            //BEGINNING
            // scheduling
            //looks for the next process to run.
            for(KLT klt: readyQueue){
                if(!pArray.contains(klt.getParentProcess()))
                    pArray.add(klt.getParentProcess());
            }
            //System.out.printf("Time %d, klts in pArray %d\n",timer, pArray.size());
            int i = 0;
            int freeCores = coresArray.size();
            boolean MoreProcesses = false;
            if(pArray != null)
                MoreProcesses = true;
            while ( (i < freeCores) && MoreProcesses){
                auxP = (Process) scheduler.schedule(Scheduler.ALGORITHM.FIFO, pArray);
                if(auxP == null)
                    MoreProcesses = false;
                else {
                    //System.out.printf("Time %d, selected p %d\n",timer, auxP.getID());
                    pArray.remove(auxP);
                    for (Thing klt : auxP.getKLTArray()) {
                        if (((KLT) klt).getKltstate() == KLT.KLTSTATE.READY && klt.getArrivalTime() <= timer) {
                            kltArray.add((KLT) klt);
                        }
                    }
                    //System.out.printf("Time %d, klts in kltArray %d\n",timer, kltArray.size());
                    boolean MoreKLTs = false;
                    if (kltArray != null)
                        MoreKLTs = true;
                    while (i < freeCores && MoreKLTs) {
                        auxKLT = (KLT) auxP.getScheduler().schedule(Scheduler.ALGORITHM.FIFO, kltArray);
                        if (auxKLT == null) {
                            MoreKLTs = false;
                        }
                        else {
                            //System.out.printf("Time %d, selected klt %d\n",timer, auxKLT.getID());
                            kltArray.remove(auxKLT);
                            int j = 0;
                            boolean alreadyAsigned = false;
                            while (j < coresArray.size() && i < freeCores && !alreadyAsigned) {

                                if ( coresArray.get(j).isFree() && auxKLT.checkCore(coresArray.get(j)) ) {
                                    auxKLT.changeState(KLT.KLTSTATE.RUNNING);
                                    coresArray.get(j).assignRunningKLT(auxKLT);
                                    readyQueue.remove(auxKLT);
                                    alreadyAsigned = true;
                                    i++;
                                    //System.out.printf("core %d\n", auxKLT.getAssignedCore().getID());
                                }
                                j++;
                            }
                        }
                    }
                }
            }
            //END
            ///////////////////////////////////////////////////////////////////////////

            int auxCounter = 0;
            for(Queue<KLT> blockedQ: blockedQueuesArray){
               if(!blockedQ.isEmpty()){
                   //fill matrix
                   matrix[auxCounter+6][timer] = (char) (blockedQ.peek().getID() + 48);

                   blockedQ.peek().decreaseTaskTime();
               }
                auxCounter++;
            }
            //fill matrix if any process is running, decrease CPU time and poll if necessary->send to blockedQ
            for(int k = 0; k < coresArray.size(); k++) {
                if (!coresArray.get(k).isFree()) {
                    System.out.printf("core %d\n", coresArray.get(k).getID());
                    KLT auxKLT2 = coresArray.get(k).getRunningKLT();
                    coresArray.get(k).setRunningKLTNull();
                    //fill matrix
                    matrix[auxKLT2.getID() - 1][timer] = (char) (auxKLT2.getAssignedCore().getID() + 64);

                    auxKLT2.decreaseTaskTime();
                    if (!auxKLT2.isTaskTimeCero()) {
                        auxKLT2.changeState(KLT.KLTSTATE.READY);
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
                                    auxKLT2.changeState(KLT.KLTSTATE.BLOCKED);
                                    blockedQueuesArray.get(0).add(auxKLT2);
                                    break;
                                case IO2:
                                    auxKLT2.changeState(KLT.KLTSTATE.BLOCKED);
                                    blockedQueuesArray.get(1).add(auxKLT2);
                                    break;
                                case IO3:
                                    auxKLT2.changeState(KLT.KLTSTATE.BLOCKED);
                                    blockedQueuesArray.get(2).add(auxKLT2);
                                    break;
                            }
                        }
                    }
                }
            }
        }
        //Mostrar matriz
        System.out.println("");
        for (int i=0; i<8; i++){
            for (int j=0; j<50; j++){
                System.out.printf("%c", matrix[i][j]);
            }
            System.out.println("");
        }
    }
}
