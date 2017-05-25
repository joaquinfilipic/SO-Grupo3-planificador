import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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

    //EXAMPLE
    public static void createEverything(){
        readyProcessQueue = new ArrayList<>();
        blockedQueuesArray = new ArrayList<>();
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        blockedQueuesArray.add(new LinkedList<>());
        coresArray = new ArrayList<>();
        Core core1 = new Core(1);
        Core core2 = new Core(2);
        coresArray.add(core1);
        //coresArray.add(core2);
        scheduler = new Scheduler();
        processesAlgorithm = Scheduler.ALGORITHM.FIFO; //for processes ONLY FIFO or SRT
        totalThreadsCount = 5; //review later
        matrix = new char[totalThreadsCount+3][50]; //review max amount of time to run
        //Empty matrix -> provisional output
        for (int i=0; i<(totalThreadsCount+3); i++){
            for (int j=0; j<50; j++){
                matrix[i][j] = ' ';
            }
        }
        createProcessesKLTsAndTasks(); //provisional input

    }

    public static void createProcessesKLTsAndTasks(){
        //Example with 3 processes, 8 klts, no ults
        //TASKS
        Task t1K1 = new Task(Task.TASKTYPE.CPU, 3);
        Queue<Task> k1TaskQ = new LinkedList<>();
        k1TaskQ.add(t1K1);
        Task t1K2 = new Task(Task.TASKTYPE.CPU, 6);
        Queue<Task> k2TaskQ = new LinkedList<>();
        k2TaskQ.add(t1K2);
        Task t1K3 = new Task(Task.TASKTYPE.CPU, 4);
        Queue<Task> k3TaskQ = new LinkedList<>();
        k3TaskQ.add(t1K3);
        Task t1K4 = new Task(Task.TASKTYPE.CPU, 5);
        Queue<Task> k4TaskQ = new LinkedList<>();
        k4TaskQ.add(t1K4);
        Task t1K5 = new Task(Task.TASKTYPE.CPU, 2);
        Queue<Task> k5TaskQ = new LinkedList<>();
        k5TaskQ.add(t1K5);


        //ULTs
        ULT u1 = new ULT(1, 0, k1TaskQ);
        ULT u2 = new ULT(2, 2, k2TaskQ);
        ULT u3 = new ULT(3, 4, k3TaskQ);
        ULT u4 = new ULT(4, 6, k4TaskQ);
        ULT u5 = new ULT(5, 8, k5TaskQ);

        //KLTs
        ArrayList<Thing> k1ArrayT = new ArrayList<>();
        k1ArrayT.add(u1);
        k1ArrayT.add(u2);
        k1ArrayT.add(u3);
        k1ArrayT.add(u4);
        k1ArrayT.add(u5);

        KLT k1 = new KLT(1, new Scheduler(), Scheduler.ALGORITHM.RR, k1ArrayT);
        k1.setQuantum(4);

        //PROCESSES
        ArrayList<Thing> p1ArrayT = new ArrayList<>();
        p1ArrayT.add(k1);

        Process p1 = new Process(1, p1ArrayT, new Scheduler());

        k1.setParentProcess(p1);
        processArray = new ArrayList<>();
        processArray.add(p1);
    }

    public static void readFile() {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        String line;
        Integer lineNum = 0;
        Integer ULTid = 0;

        Map<String, Scheduler.ALGORITHM> algorithms = new HashMap<>();
        algorithms.put('FIFO', Scheduler.ALGORITHM.FIFO);
        algorithms.put('SRT', Scheduler.ALGORITHM.SRT);
        algorithms.put('RR', Scheduler.ALGORITHM.RR);
        algorithms.put('SPN', Scheduler.ALGORITHM.SPN);
        algorithms.put('HRRN', Scheduler.ALGORITHM.HRRN);

        Scheduler.ALGORITHM[3] algorithmsList;
        Integer[3] quantumsList;

        Map<String, Task.TASKTYPE> tasktypes = new HashMap<>();
        algorithms.put('CPU', Scheduler.ALGORITHM.CPU);
        algorithms.put('IO1', Scheduler.ALGORITHM.IO1);
        algorithms.put('IO2', Scheduler.ALGORITHM.IO2);
        algorithms.put('IO3', Scheduler.ALGORITHM.IO3);

        ULT u5 = new ULT(5, 8, k5TaskQ);

        //KLTs
        ArrayList<Thing> k1ArrayT = new ArrayList<>();
        k1ArrayT.add(u1);
        k1ArrayT.add(u2);
        k1ArrayT.add(u3);
        k1ArrayT.add(u4);
        k1ArrayT.add(u5);

        KLT k1 = new KLT(1, new Scheduler(), Scheduler.ALGORITHM.RR, k1ArrayT);
        k1.setQuantum(4);

        //PROCESSES
        ArrayList<Thing> p1ArrayT = new ArrayList<>();
        p1ArrayT.add(k1);

        Process p1 = new Process(1, p1ArrayT, new Scheduler());

        k1.setParentProcess(p1);

        processArray = new ArrayList<>();
        

        processArray.add(p1);

        // Loop through lines
        while((line = in.readLine()) != null) {

            // Detect algorithms and quantum for executables
            if ( lineNum == 0 || lineNum == 1 || lineNum == 2 ) {
                algorithms.forEach((k,v) -> line.contains(k) ? algorithmsList[lineNum] = v : null);
                String quantum = line.split("Q")[1];
                if (quantum != null) quantumsList[lineNum] = Integer.parseInt(quantum);

                lineNum = lineNum + 1;
                continue;
            }
            
            if (line[0] == 'U') {
                Queue<Task> taskQueue = new LinkedList<>();
                Integer idx = 0;
                Integer arrival = 0;

                line.split(",").forEach((value) -> {
                    // Load arrival from first field
                    String field = value.trim();
                    if (idx == 0) {
                        return arrival = Integer.parse(field.split(" ")[1]);
                    }

                    // Parse line into task queue                    
                    String[] raw = field.split(" ");
                    Integer amount = Integer.parseInt(raw[0]);
                    String strType = raw[1];
                    Task.TASKTYPE tasktype;
                    tasktypes.forEach((k,v) -> strType == k ? taskType = v : null );

                    // Add task to task queue
                    taskQueue.add(new Task(tasktype, amount));

                    idx = idx + 1;
                });
                
                ULT u = new ULT(ULTid, arrival, taskQueue);
                ULTid = ULTid + 1;
            }






            lineNum = lineNum + 1;
        }

        // Close file
        in.close();
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
        createEverything();

        //general timer
        for(timer = 0; timer < 50; timer++){
            checkBlockedQueues();
            checkArrivals();

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
            }
            //if all klts in one process are blocked, then that process is blocked
            for(int m = 0; m < processArray.size(); m++){
                if(processArray.get(m).allKLTsBlocked()){
                    readyProcessQueue.remove(processArray.get(m));
                }
            }
        }
        //Show matrix
        System.out.println("");
        for (int i=0; i<(totalThreadsCount+3); i++){
            for (int j=0; j<50; j++){
                System.out.printf("%c", matrix[i][j]);
            }
            System.out.println("");
        }
    }
}
