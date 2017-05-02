/**
 * Created by joaquin on 01/05/17.
 */
import java.util.*;

public class Planificador {

    private static Queue<Proceso> newQ = new LinkedList<>();
    private static Queue<Proceso> readyQ = new LinkedList<>();
    private static Queue<Proceso> runningQ = new LinkedList<>();
    private static Queue<Proceso> blockedQ = new LinkedList<>();

    private static Proceso p1;
    private static Proceso p2;
    private static Proceso p3;

    private static char[][] matriz = new char[3][20];
    private static int time;


    private static void crearProcesos(){
        p1 = new Proceso(1, 1, 3);
        p2 = new Proceso(2, 0, 4);
        p3 = new Proceso(3, 10, 2);

        newQ.add(p1);
        newQ.add(p2);
        newQ.add(p3);
        return;
    }

    private static void chequearLlegadasProcesos(){
        if (time == 0)
            readyQ.add(p2);
        else if(time == 1)
            readyQ.add(p1);
        else if(time == 10)
            readyQ.add(p3);
    }

    public static void main(String[] args){

        //Matriz vacía
        for (int i=0; i<3; i++){
            for (int j=0; j<20; j++){
                matriz[i][j] = ' ';
            }
        }

        crearProcesos();

        //Llenar matriz con datos de procesos
        for(time = 0; time < 20; time++){
            //Chequear llegadas de procesos, override asqueroso de equals de Proceso que mira sólo el arrivalTime
            //Obviamente hacer de nuevo porque es horrible...
            chequearLlegadasProcesos();

            if(runningQ.isEmpty() && !readyQ.isEmpty()){
                runningQ.add(readyQ.poll());
            }
            if(!runningQ.isEmpty()){
                Proceso auxP = runningQ.poll();
                matriz[auxP.getPNum() - 1][time] = 'X';
                auxP.decreaseCPUT();
                if (!auxP.isCPUTCero()) {
                    runningQ.add(auxP);
                }
            }
        }

        //Mostrar matriz
        System.out.println("");
        for (int i=0; i<3; i++){
            for (int j=0; j<20; j++){
                System.out.printf("%c", matriz[i][j]);
            }
            System.out.println("");
        }
    }
}
