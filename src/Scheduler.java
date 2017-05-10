import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */
public class Scheduler {
    public enum ALGORITHM { FIFO, SRT };
    private ALGORITHM algorithm;
    private Queue<Process> readyQueue;
    private ArrayList<Queue<Process>> blockedQueuesArray;
    private ArrayList<Core> coresArray;
    private int timer;
}
