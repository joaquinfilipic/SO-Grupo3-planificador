/**
 * Created by joaquin on 10/05/17.
 */
public class Core {
    private int ID;
    private KLT runningKLT;

    public Core(int id){
        ID = id;
    }

    public int getID() {
        return ID;
    }
    public KLT getRunningKLT(){ return runningKLT; }
    public void assignRunningKLT(KLT klt){
        runningKLT = klt;
    }
}
