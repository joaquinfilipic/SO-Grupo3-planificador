import java.util.ArrayList;

/**
 * Created by joaquin on 10/05/17.
 */

public class Process {
    private int processID;
    private ArrayList<KLT> KLTArray;

    public Process(int ID, ArrayList<KLT> array){
        this.processID = ID;
        this.KLTArray = array;
    }

    public int getProcessID(){ return processID; }
    public ArrayList<KLT> getKLTArray(){ return KLTArray; }
}
