import java.util.ArrayList;

/**
 * Created by joaquin on 10/05/17.
 */

public class Scheduler {
    public enum ALGORITHM { FIFO, RR, SPN, SRT, HRRN };

    public Scheduler(){}

    //For processes and klts
    public Thing schedule(ALGORITHM alg, ArrayList<Thing> thingsArray){
        if(thingsArray == null || thingsArray.size() == 0)
            return null;
        if(alg == ALGORITHM.FIFO || alg == ALGORITHM.SRT){
            if(thingsArray.isEmpty()){
                return null;
            }
            return thingsArray.get(0);
        }
        else return null;
    }

    //For ULTs Library
    public Thing schedule(KLT klt){
        if(klt.getULTQueue() == null || klt.getULTQueue().size() == 0)
            return null;
        if(klt.getAlgorithm() == ALGORITHM.FIFO || klt.getAlgorithm() == ALGORITHM.SRT){
            if(klt.getULTQueue().isEmpty()){
                return null;
            }
            return klt.getULTQueue().get(0);
        }
        else if(klt.getAlgorithm() == ALGORITHM.SPN){
            if(klt.getULTQueue().isEmpty()){
                return null;
            }
            if(klt.getPrevULTRunning() != null) {
                return klt.getPrevULTRunning();
            }
            else {
                ULT retULT = (ULT)klt.getULTQueue().get(0);
                for (Thing u : klt.getULTQueue()){
                    if(retULT.getRemainingTime() > ((ULT)u).getRemainingTime()){
                        retULT = (ULT)u;
                    }
                }
                return retULT;
            }
        }
        else if(klt.getAlgorithm() == ALGORITHM.HRRN){
            if(klt.getULTQueue().isEmpty()){
                return null;
            }
            if(klt.getPrevULTRunning() != null) {
                return klt.getPrevULTRunning();
            }
            else {
                ULT retULT = (ULT)klt.getULTQueue().get(0);
                float w1 = (float)retULT.getWaitingTime();
                float s1 = (float)retULT.getRemainingTime();
                float ResponseRatio1 = (w1 + s1) / s1;
                for (Thing u : klt.getULTQueue()){
                    float w2 = (float) ((ULT)u).getWaitingTime();
                    float s2 = (float) ((ULT)u).getRemainingTime();
                    float ResponseRatio2 = (w2 + s2) / s2;
                    if( ResponseRatio1 < ResponseRatio2 ){
                        retULT = (ULT)u;
                    }
                }
                return retULT;
            }
        }
        else if(klt.getAlgorithm() == ALGORITHM.RR){
            if(klt.getULTQueue().isEmpty()){
                return null;
            }
            if(klt.getPrevULTRunning() == null){
                return klt.getULTQueue().get(0);
            }
            if(klt.getRemainingQ() > 0){
                return klt.getPrevULTRunning();
            }
            else {
                klt.getULTQueue().add( klt.getULTQueue().remove(0) );
                klt.setRemainingQ(klt.getQuantum());
                return klt.getULTQueue().get(0);
            }
        }
        else return null;
    }
}
