import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class MainClass {
    private static ArrayList<Process> processArray, readyProcessQueue;
    private static ArrayList<Queue<KLT>> blockedQueuesArray; //always FIFO for blocked queues
    private static ArrayList<Core> coresArray;
    private static Scheduler scheduler;
    private static int timer;
    private static char[][] matrix;
    private static int totalThreadsCount;
    private static Scheduler.ALGORITHM processesAlgorithm;
    private static ArrayList<Log> logArrayList;

    //EXAMPLE
    public static void createEverything(){
        readyProcessQueue = new ArrayList<>();
        blockedQueuesArray = new ArrayList<>();
        logArrayList = new ArrayList<>();
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        coresArray = new ArrayList<>();
        Core core1 = new Core(1);
        Core core2 = new Core(2);
        coresArray.add(core1);
        coresArray.add(core2);
        scheduler = new Scheduler();
        processesAlgorithm = Scheduler.ALGORITHM.FIFO; //for processes ONLY FIFO or SRT
        totalThreadsCount = 7; //review later
        matrix = new char[totalThreadsCount+3+coresArray.size()][50]; //review max amount of time to run
        //Empty matrix -> provisional output
        for (int i=0; i<(totalThreadsCount+3+coresArray.size()); i++){
            for (int j=0; j<50; j++){
                matrix[i][j] = ' ';
            }
        }
        createProcessesKLTsAndTasks(); //provisional input
    }

    public static Integer loadInput() {
        // Create inner structures
        readyProcessQueue = new ArrayList<>();
        blockedQueuesArray = new ArrayList<>();
        logArrayList = new ArrayList<>();
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        scheduler = new Scheduler();

        // Create data from parsed input JSON
        Parser parser = new Parser("/input/input.json");
        parser.parse();
        coresArray = parser.getCores();
        processesAlgorithm = parser.getProcessAlgorithm();
        processArray = parser.getProcesses();
        totalThreadsCount = (int) parser.getTotalThreads();

        // Initialize output matrix
        int rowsAmount = totalThreadsCount+3+coresArray.size();
        matrix = new char[rowsAmount][(int) parser.getTimeline()];
        //Empty matrix -> provisional output
        for (int i=0; i<(rowsAmount); i++){
            for (int j=0; j<parser.getTimeline(); j++){
                matrix[i][j] = ' ';
            }
        }

        return (int) parser.getTimeline();
    }

    public static void createProcessesKLTsAndTasks(){
        //Example with 3 processes, 8 klts, no ults
        //TASKS
        Task t1K1 = new Task(Task.TASKTYPE.CPU, 3);
        Task t2K1 = new Task(Task.TASKTYPE.IO1, 1);
        Task t3K1 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k1TaskQ = new LinkedList<>();
        k1TaskQ.add(t1K1);
        k1TaskQ.add(t2K1);
        k1TaskQ.add(t3K1);

        Task t1K2 = new Task(Task.TASKTYPE.CPU, 2);
        Task t2K2 = new Task(Task.TASKTYPE.IO2, 2);
        Task t3K2 = new Task(Task.TASKTYPE.CPU, 3);
        Queue<Task> k2TaskQ = new LinkedList<>();
        k2TaskQ.add(t1K2);
        k2TaskQ.add(t2K2);
        k2TaskQ.add(t3K2);
        Task t1K3 = new Task(Task.TASKTYPE.CPU, 4);
        Task t2K3 = new Task(Task.TASKTYPE.IO1, 2);
        Task t3K3 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k3TaskQ = new LinkedList<>();
        k3TaskQ.add(t1K3);
        k3TaskQ.add(t2K3);
        k3TaskQ.add(t3K3);

        Task t1K4U4 = new Task(Task.TASKTYPE.CPU, 4);
        Task t2K4U4 = new Task(Task.TASKTYPE.IO2, 1);
        Task t3K4U4 = new Task(Task.TASKTYPE.CPU, 1);
        Queue<Task> k4u4TaskQ = new LinkedList<>();
        k4u4TaskQ.add(t1K4U4);
        k4u4TaskQ.add(t2K4U4);
        k4u4TaskQ.add(t3K4U4);
        Task t1K4U5 = new Task(Task.TASKTYPE.CPU, 2);
        Task t2K4U5 = new Task(Task.TASKTYPE.IO1, 3);
        Task t3K4U5 = new Task(Task.TASKTYPE.CPU, 1);
        Queue<Task> k4u5TaskQ = new LinkedList<>();
        k4u5TaskQ.add(t1K4U5);
        k4u5TaskQ.add(t2K4U5);
        k4u5TaskQ.add(t3K4U5);
        Task t1K6U6 = new Task(Task.TASKTYPE.CPU, 1);
        Task t2K6U6 = new Task(Task.TASKTYPE.IO1, 1);
        Task t3K6U6 = new Task(Task.TASKTYPE.CPU, 3);
        Task t4K6U6 = new Task(Task.TASKTYPE.IO3, 2);
        Task t5K6U6 = new Task(Task.TASKTYPE.CPU, 1);
        Queue<Task> k6u6TaskQ = new LinkedList<>();
        k6u6TaskQ.add(t1K6U6);
        k6u6TaskQ.add(t2K6U6);
        k6u6TaskQ.add(t3K6U6);
        k6u6TaskQ.add(t4K6U6);
        k6u6TaskQ.add(t5K6U6);
        Task t1K6U7 = new Task(Task.TASKTYPE.CPU, 5);
        Task t2K6U7 = new Task(Task.TASKTYPE.IO2, 3);
        Task t3K6U7 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k6u7TaskQ = new LinkedList<>();
        k6u7TaskQ.add(t1K6U7);
        k6u7TaskQ.add(t2K6U7);
        k6u7TaskQ.add(t3K6U7);

        //ULTs
        ULT u4 = new ULT(4, 0, k4u4TaskQ);
        ULT u5 = new ULT(5, 2, k4u5TaskQ);
        ULT u6 = new ULT(6, 4, k6u6TaskQ);
        ULT u7 = new ULT(7, 6, k6u7TaskQ);

        //KLTs
        KLT k1 = new KLT(1, 0, k1TaskQ);
        KLT k2 = new KLT(2, 3, k2TaskQ);
        KLT k3 = new KLT(3, 8, k3TaskQ);

        ArrayList<Thing> k4ArrayT = new ArrayList<>();
        ArrayList<Thing> k6ArrayT = new ArrayList<>();
        k4ArrayT.add(u4);
        k4ArrayT.add(u5);
        k6ArrayT.add(u6);
        k6ArrayT.add(u7);

        KLT k4 = new KLT(4, new Scheduler(), Scheduler.ALGORITHM.RR, k4ArrayT);
        k4.setQuantum(4);
        KLT k6 = new KLT(6, new Scheduler(), Scheduler.ALGORITHM.RR, k6ArrayT);
        k6.setQuantum(4);

        //PROCESSES
        ArrayList<Thing> p1ArrayT = new ArrayList<>();
        p1ArrayT.add(k1);
        ArrayList<Thing> p2ArrayT = new ArrayList<>();
        p2ArrayT.add(k2);
        p2ArrayT.add(k3);
        ArrayList<Thing> p4ArrayT = new ArrayList<>();
        p4ArrayT.add(k4);
        p4ArrayT.add(k6);

        Process p1 = new Process(1, p1ArrayT, new Scheduler());
        Process p2 = new Process(2, p2ArrayT, new Scheduler());
        Process p4 = new Process(4, p4ArrayT, new Scheduler());

        k1.setParentProcess(p1);
        k2.setParentProcess(p2);
        k3.setParentProcess(p2);
        k4.setParentProcess(p4);
        k6.setParentProcess(p4);

        processArray = new ArrayList<>();
        processArray.add(p1);
        processArray.add(p2);
        processArray.add(p4);
    }

    public static void checkArrivals(){
        boolean arrival = false;
        for(Process p: processArray){
            for(Thing klt: p.getKLTArray()){
                if( klt.getArrivalTime() == timer) {
                    ((KLT)klt).changeState(KLT.KLTSTATE.READY);
                    if (!readyProcessQueue.contains(p)) {
                        readyProcessQueue.add(p);
                        arrival = true;
                    }
                    p.getKLTQueue().add(klt);
                }
                if (((KLT)klt).hasULTs()) {
                    ((KLT) klt).setReadyULTs(timer);
                }

            }
        }
        //only for SRT -> preemptive at arrival
        if( arrival && processesAlgorithm == Scheduler.ALGORITHM.SRT){
            for(int i = 0; i < readyProcessQueue.size() - 1; i++){
                for(int j = i + 1; j < readyProcessQueue.size(); j++){
                    if(readyProcessQueue.get(i).getRemainingTime() >= readyProcessQueue.get(j).getRemainingTime()){
                        Process auxP = readyProcessQueue.get(i);
                        Process auxP2 = readyProcessQueue.get(j);
                        readyProcessQueue.set(i, auxP2);
                        readyProcessQueue.set(j, auxP);

                    }
                }
            }
            for(Core c : coresArray) {
                c.setPrevRunningNull();
            }
        }

    }
    public static void checkBlockedQueues(){
        if(blockedQueuesArray == null)
            return;
        for(Queue<KLT> blockedQ: blockedQueuesArray){
            if(blockedQ != null && !blockedQ.isEmpty()){
                if(blockedQ.peek().isTaskTimeCero()){
                    KLT auxKlt = blockedQ.poll();
                    if(!auxKlt.hasULTs()) {
                        auxKlt.getTaskQueue().poll();
                    }
                    else {
                        auxKlt.getCurrentULT().getTaskQueue().poll();
                        auxKlt.setCurrentULT(null);
                    }
                    auxKlt.changeState(KLT.KLTSTATE.READY);
                    auxKlt.getParentProcess().getKLTQueue().add(auxKlt);
                    //this is for blocked processes to return to ready
                    if(!readyProcessQueue.contains(auxKlt.getParentProcess()))
                        readyProcessQueue.add(auxKlt.getParentProcess());
                }
            }
        }
    }

    public static void main(String[] args){
        Integer timeline = loadInput();
        boolean moreThingsToDo = true;

        try(  PrintWriter out = new PrintWriter( "log.txt" )  ){
            //general timer
            for(timer = 0; timer < timeline && moreThingsToDo; timer++){
                Log l = new Log();
                l.setTime(timer);
                checkBlockedQueues();
                checkArrivals();
                //LOG: Ready Process Queue
                for(Process p : readyProcessQueue){
                    l.getReadyProcessQueue().add(p);
                }
                //LOG: Blocked Queues
                for(int i = 0; i < blockedQueuesArray.size(); i++){
                    for(KLT k : blockedQueuesArray.get(i)){
                        l.getBlockedQueuesArray().get(i).add(k);
                    }
                }
                //Run algorithm of selection for each core
                for(int n = 0; n < coresArray.size() && coresArray.get(n).isFree(); n++) {
                    ArrayList<Thing> pArray = new ArrayList<>();
                    boolean assigned = false;
                    KLT aux = coresArray.get(n).getPrevRunning();
                    if( aux != null ){
                        aux.changeState(KLT.KLTSTATE.RUNNING);
                        coresArray.get(n).assignRunningKLT(aux);
                        aux.getParentProcess().getKLTQueue().remove(aux);
                    }
                    else {
                        //pArray contains all possible processes to select
                        for (Process p : readyProcessQueue) {
                            pArray.add(p);
                        }
                        boolean MoreProcesses = false;
                        if (pArray.size() != 0)
                            MoreProcesses = true;
                        while (MoreProcesses && assigned == false) {
                            ArrayList<Thing> kltArray = new ArrayList<>();
                            Process auxP;
                            KLT auxKLT;
                            //scheduler selects process depending on the algorithm
                            auxP = (Process) scheduler.schedule(processesAlgorithm, pArray);
                            if (auxP == null)
                                MoreProcesses = false;
                            else {
                                pArray.remove(auxP);
                                //kltArray contains all posible klts to select
                                for (Thing klt : auxP.getKLTQueue()) {
                                    if (((KLT) klt).getKltstate() == KLT.KLTSTATE.READY) {
                                        kltArray.add(klt);
                                    }
                                }
                                boolean MoreKLTs = false;
                                if (kltArray.size() != 0)
                                    MoreKLTs = true;
                                while (MoreKLTs && assigned == false) {
                                    //scheduler selects klt depending on the algorithm -> FIFO for klts
                                    auxKLT = (KLT) auxP.getScheduler().schedule(auxP.getAlgorithm(), kltArray);
                                    if (auxKLT == null) {
                                        MoreKLTs = false;
                                    } else {
                                        kltArray.remove(auxKLT);
                                        //if selected klt is compatible with this core -> assign
                                        if (auxKLT.checkCore(coresArray.get(n))) {
                                            auxKLT.changeState(KLT.KLTSTATE.RUNNING);
                                            coresArray.get(n).assignRunningKLT(auxKLT);
                                            auxKLT.getParentProcess().getKLTQueue().remove(auxKLT);
                                            assigned = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //LOG: Running KLTs
                for(Core c : coresArray){
                    if(!c.isFree()){
                        l.getRunningKLTsArray().add(c.getRunningKLT());
                    }
                }
                //LOG: Ready Process Queue
                for(Process p : readyProcessQueue){
                    boolean readyKLTs = false;
                    for(int j = 0; j < p.getKLTQueue().size() && !readyKLTs; j++){
                        if( ((KLT)p.getKLTQueue().get(j)).getKltstate() == KLT.KLTSTATE.READY ){
                            readyKLTs = true;
                        }
                    }
                    if(readyKLTs){
                        l.getReadyProcessQueue().add(p);
                    }
                }
                //Decrease time from klts in blocked queues
                int auxCounter = 0;
                for (Queue<KLT> blockedQ : blockedQueuesArray) {
                    if (!blockedQ.isEmpty()) {
                        //fill matrix
                        matrix[auxCounter + totalThreadsCount][timer] = (char) (blockedQ.peek().getID() + 48);
                            blockedQ.peek().decreaseTaskTime();
                    }
                    auxCounter++;
                }
                //fill matrix if any process is running, decrease CPU time and poll if necessary->send to blockedQ
                for (int k = 0; k < coresArray.size(); k++) {
                    if (!coresArray.get(k).isFree()) {
                        KLT auxKLT2 = coresArray.get(k).getRunningKLT();
                        ULT auxULT;
                        coresArray.get(k).setRunningKLTNull();
                        //fill matrix
                        if(!auxKLT2.hasULTs()) {
                            matrix[auxKLT2.getID() - 1][timer] = (char) (auxKLT2.getAssignedCore().getID() + 64);
                        }
                        else{
                            ArrayList<Thing> uArray = new ArrayList<>();
                            for (Thing u : auxKLT2.getULTQueue()){
                                uArray.add(u);
                            }
                            auxULT = (ULT) auxKLT2.getScheduler().schedule(auxKLT2);
                            auxKLT2.setCurrentULT(auxULT);
                            //fill matrix... review later
                            matrix[auxULT.getID() - 1][timer] = (char) (auxKLT2.getAssignedCore().getID() + 64);
                            auxKLT2.setPrevULTRunning(auxULT);
                            auxKLT2.increaseWaitingTime();
                            auxKLT2.decreaseRemainingQ();
                        }
                        auxKLT2.decreaseTaskTime();
                        //if remaining task time is not 0, that klt remains on running state
                        if (!auxKLT2.isTaskTimeCero()) {
                            coresArray.get(k).assignPrevRunning(auxKLT2);
                            auxKLT2.changeState(KLT.KLTSTATE.READY);
                            auxKLT2.getParentProcess().getKLTQueue().add(0, auxKLT2);
                        } else {
                            coresArray.get(k).setPrevRunningNull();
                            boolean nextTask;
                            Task.TASKTYPE auxTT = null;
                            //check if klt has ults
                            if(!auxKLT2.hasULTs()) {
                                auxKLT2.getTaskQueue().poll();
                                nextTask = auxKLT2.getTaskQueue().peek() != null;
                                if(nextTask){
                                    auxTT = auxKLT2.getTaskQueue().peek().getTaskType();
                                }
                            }
                            else {
                                auxKLT2.getCurrentULT().getTaskQueue().poll();
                                nextTask = auxKLT2.getCurrentULT().getTaskQueue().peek() != null;
                                if(nextTask){
                                    auxTT = auxKLT2.getCurrentULT().getTaskQueue().peek().getTaskType();
                                }
                            }
                            if (auxTT != null) {
                                //checks next task type
                                switch (auxTT) {
                                    case CPU:
                                        auxKLT2.changeState(KLT.KLTSTATE.READY);
                                        auxKLT2.getParentProcess().getKLTQueue().add(0, auxKLT2);
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
                                if(auxKLT2.getParentProcess().allKLTsBlocked()){
                                    readyProcessQueue.remove(auxKLT2.getParentProcess());
                                }
                            }
                            else{
                                if(!auxKLT2.hasULTs()) {
                                    auxKLT2.changeState(KLT.KLTSTATE.BLOCKED); //as finalized
                                    if (auxKLT2.getParentProcess().allKLTsBlocked()) {
                                        readyProcessQueue.remove(auxKLT2.getParentProcess());
                                    }
                                }
                                else {
                                    auxKLT2.setPrevULTRunning(null);
                                    auxKLT2.getULTQueue().remove(auxKLT2.getCurrentULT());
                                    auxKLT2.setRemainingQ(auxKLT2.getQuantum());
                                    if(auxKLT2.getULTQueue().size() != 0) {
                                        coresArray.get(k).assignPrevRunning(auxKLT2);
                                        auxKLT2.changeState(KLT.KLTSTATE.READY);
                                        auxKLT2.getParentProcess().getKLTQueue().add(0, auxKLT2);
                                    }
                                    else{
                                        auxKLT2.changeState(KLT.KLTSTATE.BLOCKED); //as finalized
                                        if (auxKLT2.getParentProcess().allKLTsBlocked()) {
                                            readyProcessQueue.remove(auxKLT2.getParentProcess());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        //Core is free -> SO running
                        int aux = totalThreadsCount + blockedQueuesArray.size() +coresArray.get(k).getID()-1;
                        matrix[aux][timer] = 'X';
                    }
                }
                //if all klts in one process are blocked, then that process is blocked
                for(int m = 0; m < processArray.size(); m++){
                    if(processArray.get(m).allKLTsBlocked()){
                        readyProcessQueue.remove(processArray.get(m));
                    }
                }
                //LOG
                logArrayList.add(l);

                logArrayList.get(timer).showLog(out);


                if(!l.checkRemainingThings()){
                    moreThingsToDo = false;
                }
            }
        } catch (Throwable e) {
        }



        //Show matrix
        System.out.println("");
        for (int i=0; i<(totalThreadsCount+3+coresArray.size()); i++){
            for (int j=0; j<50; j++){
                System.out.printf("%c", matrix[i][j]);
            }
            System.out.println("");
        }
    }
}
