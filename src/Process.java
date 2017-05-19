import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joaquin on 10/05/17.
 */

public class Process extends Thing {
    private ArrayList<Thing> KLTArray;
    private ArrayList<Thing> KLTQueue;
    private Scheduler scheduler;

    public Process(int id, ArrayList<Thing> a, Scheduler s){
        ID = id;
        KLTArray = a;
        scheduler = s;
        KLTQueue = new ArrayList<>();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ArrayList<Thing> getKLTArray(){ return KLTArray; }

    public ArrayList<Thing> getKLTQueue(){ return KLTQueue; }

    public boolean hasReadyKLT(){
        for(Thing klt: KLTArray){
            if( ((KLT)klt).getKltstate() == KLT.KLTSTATE.READY)
                return true;
        }
        return false;
    }

    public boolean allKLTsBlocked(){
        boolean aux = true;
        for(Thing k : KLTArray){
            if ( ((KLT)k).getKltstate() != KLT.KLTSTATE.BLOCKED)
                aux = false;
        }
        return aux;
    }

    public void schedule(Scheduler.ALGORITHM alg){
        scheduler.schedule(Scheduler.ALGORITHM.FIFO, KLTArray);
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        Process p = (Process) o;
        return ID == p.getID();
    }
}
