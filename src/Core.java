/**
 * Created by joaquin on 10/05/17.
 */
public class Core {
    private int ID;
    private KLT runningKLT;
    private KLT prevRunning;

    public Core(int id){
        ID = id;
    }

    public int getID() {
        return ID;
    }
    public KLT getRunningKLT(){ return runningKLT; }
    public KLT getPrevRunning(){ return prevRunning; }
    public void assignRunningKLT(KLT klt){
        runningKLT = klt;
    }
    public void assignPrevRunning(KLT klt) { prevRunning = klt; }
    public void setRunningKLTNull(){ runningKLT = null; }
    public void setPrevRunningNull(){ prevRunning = null; }
    public boolean isFree(){
        if(runningKLT == null)
            return true;
        return false;
    }
}
