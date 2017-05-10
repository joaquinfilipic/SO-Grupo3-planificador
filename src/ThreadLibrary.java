import java.util.ArrayList;

/**
 * Created by joaquin on 10/05/17.
 */

public class ThreadLibrary {
    public enum ALGORITHM { FIFO, RR, SPN, SRT, HRRN}
    private ALGORITHM algorithm;
    private ArrayList<ULT> ULTArray;

    public ThreadLibrary(ALGORITHM algorithm, ArrayList<ULT> array){
        this.algorithm = algorithm;
        this.ULTArray = array;
    }
}
