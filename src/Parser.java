import java.io.FileReader;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by rama on 28/05/17.
 */

public class Parser {
    private String filename;
    private ArrayList<Core> cores = new ArrayList<>();
    private Algorithm processAlgorithm;
    private Algorithm KLTAlgorithm;
    private Algorithm ULTAlgorithm;
    private Integer timeline;
    private Integer totalThreads;
    private ArrayList<Process> processes;

    private class Algorithm {
        private Scheduler.ALGORITHM type;
        private Integer quantum;

        public Algorithm(Scheduler.ALGORITHM type, Integer quantum) {
            this.type = type;
            this.quantum = quantum;
        }

        public Scheduler.ALGORITHM getType() {
            return this.type;
        }

        public Integer getQuantum() {
            return this.quantum;
        }

        public Boolean hasQuantum() {
            return quantum != null;
        }
    }

    public Parser (String filename) {
        this.filename = filename;
        this.totalThreads = 0;
    }

    public Integer getTotalThreads() {
        return totalThreads;
    }

    public ArrayList<Core> getCores() {
        return cores;
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public Scheduler.ALGORITHM getProcessAlgorithm() {
        return processAlgorithm.getType();
    }

    public Integer getTimeline() {
        return timeline;
    }

    private void loadCores (Integer cores) {
        for (int i = 1; i<= cores; i++) this.cores.add(new Core(i));
    }

    private Scheduler.ALGORITHM getAlgorithmFromString(String alg) {
        Map<String, Scheduler.ALGORITHM> algorithms = new HashMap<>();
        algorithms.put("FIFO", Scheduler.ALGORITHM.FIFO);
        algorithms.put("SRT", Scheduler.ALGORITHM.SRT);
        algorithms.put("RR", Scheduler.ALGORITHM.RR);
        algorithms.put("SPN", Scheduler.ALGORITHM.SPN);
        algorithms.put("HRRN", Scheduler.ALGORITHM.HRRN);

        return algorithms.get(alg);
    }

    private Task.TASKTYPE getTasktypeFromString(String type) {
        Map<String, Task.TASKTYPE> tasktypes = new HashMap<>();
        tasktypes.put("CPU", Task.TASKTYPE.CPU);
        tasktypes.put("IO1", Task.TASKTYPE.IO1);
        tasktypes.put("IO2", Task.TASKTYPE.IO2);
        tasktypes.put("IO3", Task.TASKTYPE.IO3);

        return tasktypes.get(type);
    }

    private void loadAlgorithms(JSONObject obj) {
        JSONObject rawAlgorithm =(JSONObject) obj.get("processAlgorithm");
        Scheduler.ALGORITHM alg = getAlgorithmFromString((String) rawAlgorithm.get("name"));
        Integer quantum = (Integer) rawAlgorithm.get("quantum");
        this.processAlgorithm = new Algorithm(alg, quantum);

        rawAlgorithm =(JSONObject) obj.get("KLTAlgorithm");
        alg = getAlgorithmFromString((String) rawAlgorithm.get("name"));
        quantum = (Integer) rawAlgorithm.get("quantum");
        this.KLTAlgorithm = new Algorithm(alg, quantum);

        rawAlgorithm =(JSONObject) obj.get("ULTAlgorithm");
        alg = getAlgorithmFromString((String) rawAlgorithm.get("name"));
        quantum = (Integer) rawAlgorithm.get("quantum");
        this.ULTAlgorithm = new Algorithm(alg, quantum);
    }

    private Task parseTask (JSONObject rawTask) {
        Task.TASKTYPE tasktype  = getTasktypeFromString((String) rawTask.get("type"));
        Integer time = (Integer) rawTask.get("time");
        return new Task(tasktype, time);
    }

    private Queue<Task> parseTasks (JSONArray rawTasks) {
        Iterator<JSONObject> tasksIt = rawTasks.iterator();
        Queue<Task> tasks = new LinkedList<>();

        while (tasksIt.hasNext()) {
            JSONObject rawTask = tasksIt.next();
            tasks.add(this.parseTask(rawTask));
        }

        return tasks;
    }

    private ULT parseULT (JSONObject rawULT) {
        Integer arrival = (Integer) rawULT.get("arrival");
        Integer id = (Integer) rawULT.get("id");
        Queue<Task> tasks = this.parseTasks((JSONArray) rawULT.get("tasks"));
        totalThreads = totalThreads + 1;

        return new ULT(id, arrival, tasks);
    }

    private ArrayList<Thing> parseULTs (JSONArray rawULTs) {
        Iterator<JSONObject> ultsIt = rawULTs.iterator();
        ArrayList<Thing> ults = new ArrayList<>();

        while (ultsIt.hasNext()) {
            JSONObject rawULT = ultsIt.next();
            ults.add(this.parseULT(rawULT));
        }

        return ults;
    }

    private KLT parseKLT (JSONObject rawKLT) {
        Integer arrival = (Integer) rawKLT.get("arrival");
        Integer id = (Integer) rawKLT.get("id");
        JSONArray rawULTs = (JSONArray) rawKLT.get("ults");
        KLT klt;

        if (rawULTs == null) {
            totalThreads = totalThreads + 1;
            Queue<Task> tasks = this.parseTasks((JSONArray) rawKLT.get("tasks"));
            klt = new KLT(id, arrival, tasks);
        } else {
            ArrayList<Thing> ults = this.parseULTs(rawULTs);
            klt = new KLT(id, new Scheduler(), this.KLTAlgorithm.getType(), ults);
        }

        if (this.KLTAlgorithm.hasQuantum()) klt.setQuantum(this.KLTAlgorithm.getQuantum());
        return klt;
    }

    private ArrayList<Thing> parseKLTs (JSONArray rawKLTs) {
        Iterator<JSONObject> kltsIt = rawKLTs.iterator();
        ArrayList<Thing> klts = new ArrayList<>();

        while (kltsIt.hasNext()) {
            JSONObject rawKLT = kltsIt.next();
            klts.add(this.parseKLT(rawKLT));
        }

        return klts;
    }

    private Process parseProcess(JSONObject rawProcess) {
        Integer arrival = (Integer) rawProcess.get("arrival");
        Integer id = (Integer) rawProcess.get("id");
        ArrayList<Thing> klts = this.parseKLTs((JSONArray) rawProcess.get("klts"));

        return new Process(id, klts, new Scheduler());
    }

    private ArrayList<Process> parseProcesses(JSONArray tasks) {
        Iterator<JSONObject> processIt = tasks.iterator();
        ArrayList<Process> processes = new ArrayList<>();

        while (processIt.hasNext()) {
            JSONObject rawProcess = processIt.next();
            Process process = this.parseProcess(rawProcess);

            for(Thing thing: process.getKLTArray()) {
                KLT klt = (KLT) thing;
                klt.setParentProcess(process);
            }

            processes.add(process);
        }

        return processes;
    }

    public void parse () {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;

            // Load from root cores, timeline and algorithms.
            this.loadCores((Integer) jsonObject.get("cpu"));
            this.timeline = (Integer) jsonObject.get("timeline");
            this.loadAlgorithms(jsonObject);

            // Parse executables
            this.processes = this.parseProcesses((JSONArray) jsonObject.get("tasks"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
