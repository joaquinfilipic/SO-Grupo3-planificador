import java.util.ArrayList;

/**
 * Created by joaquin on 10/05/17.
 */

public class Scheduler {
    public enum ALGORITHM { FIFO, RR, SPR, SRT, HRRN };
    private ALGORITHM algorithm;
    private ArrayList<Thing> thingsArray;

    public Scheduler(ALGORITHM alg, ArrayList<Thing> TA){
        algorithm = alg;
        thingsArray = TA;
    }

    public Thing schedule(ALGORITHM alg, ArrayList<Thing> thingsArray){
        if(thingsArray == null)
            return null;
        if(alg == ALGORITHM.FIFO){
            if(thingsArray.isEmpty()){
                return null;
            }
            return thingsArray.get(0);
        }
        else if(alg == ALGORITHM.SRT){
            return null;
        }
        else return null;
    }
}
