/**
 * Created by joaquin on 01/05/17.
 */

public class Proceso {
    private int pNum;   //Process Number
    private int aT;     //Arrival Time
    private int cpuT;   //CPU Time

    public Proceso(int pNum, int aT, int cpuT){
        this.pNum = pNum;
        this.aT = aT;
        this.cpuT = cpuT;
    }

    public int getPNum(){
        return pNum;
    }
    public int getAT(){
        return aT;
    }
    public int getCPUT(){
        return cpuT;
    }
    public void decreaseCPUT(){
        cpuT--;
    }
    public boolean isCPUTCero(){
        if(cpuT == 0)
            return true;
        return false;
    }
}
