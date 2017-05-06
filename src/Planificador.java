/**
 * Created by joaquin on 01/05/17.
 */
import java.util.*;

import static javax.management.openmbean.SimpleType.CHARACTER;

public class Planificador {

    private static Queue<Proceso> newQ = new LinkedList<>();
    private static Queue<Proceso> readyQ = new LinkedList<>();
    private static Queue<Proceso> runningQ = new LinkedList<>();
    private static Queue<Proceso> blockedQ1 = new LinkedList<>();
    private static Queue<Proceso> blockedQ2 = new LinkedList<>();
    private static Queue<Proceso> blockedQ3 = new LinkedList<>();

    private static Queue<Tarea> p1TaskQ = new LinkedList<>();
    private static Queue<Tarea> p2TaskQ = new LinkedList<>();
    private static Queue<Tarea> p3TaskQ = new LinkedList<>();
    private static Queue<Tarea> p4TaskQ = new LinkedList<>();
    private static Queue<Tarea> p5TaskQ = new LinkedList<>();

    private static Proceso p1;
    private static Proceso p2;
    private static Proceso p3;
    private static Proceso p4;
    private static Proceso p5;

    private static char[][] matrix = new char[8][50];
    private static int time;


    private static void crearProcesos(){
        createTasks();
        p1 = new Proceso(1, 1, p1TaskQ);
        p2 = new Proceso(2, 0, p2TaskQ);
        p3 = new Proceso(3, 20, p3TaskQ);
        p4 = new Proceso(4, 8, p4TaskQ);
        p5 = new Proceso(5, 14, p5TaskQ);

        newQ.add(p1);
        newQ.add(p2);
        newQ.add(p3);
        newQ.add(p4);
        newQ.add(p5);
        return;
    }

    private static void createTasks(){
        Tarea t1P1 = new Tarea(Tarea.TIPOTAREA.CPU, 2);
        Tarea t2P1 = new Tarea(Tarea.TIPOTAREA.IO2, 5);
        Tarea t3P1 = new Tarea(Tarea.TIPOTAREA.CPU, 1);
        Tarea t4P1 = new Tarea(Tarea.TIPOTAREA.IO1, 2);
        Tarea t5P1 = new Tarea(Tarea.TIPOTAREA.CPU, 1);
        p1TaskQ.add(t1P1);
        p1TaskQ.add(t2P1);
        p1TaskQ.add(t3P1);
        p1TaskQ.add(t4P1);
        p1TaskQ.add(t5P1);
        Tarea t1P2 = new Tarea(Tarea.TIPOTAREA.CPU, 3);
        Tarea t2P2 = new Tarea(Tarea.TIPOTAREA.IO2, 3);
        Tarea t3P2 = new Tarea(Tarea.TIPOTAREA.CPU, 2);
        Tarea t4P2 = new Tarea(Tarea.TIPOTAREA.IO1, 3);
        Tarea t5P2 = new Tarea(Tarea.TIPOTAREA.CPU, 4);
        p2TaskQ.add(t1P2);
        p2TaskQ.add(t2P2);
        p2TaskQ.add(t3P2);
        p2TaskQ.add(t4P2);
        p2TaskQ.add(t5P2);
        Tarea t1P3 = new Tarea(Tarea.TIPOTAREA.CPU, 4);
        Tarea t2P3 = new Tarea(Tarea.TIPOTAREA.IO2, 1);
        Tarea t3P3 = new Tarea(Tarea.TIPOTAREA.CPU, 2);
        Tarea t4P3 = new Tarea(Tarea.TIPOTAREA.IO1, 1);
        Tarea t5P3 = new Tarea(Tarea.TIPOTAREA.CPU, 1);
        p3TaskQ.add(t1P3);
        p3TaskQ.add(t2P3);
        p3TaskQ.add(t3P3);
        p3TaskQ.add(t4P3);
        p3TaskQ.add(t5P3);
        Tarea t1P4 = new Tarea(Tarea.TIPOTAREA.CPU, 5);
        Tarea t2P4 = new Tarea(Tarea.TIPOTAREA.IO2, 2);
        Tarea t3P4 = new Tarea(Tarea.TIPOTAREA.CPU, 1);
        Tarea t4P4 = new Tarea(Tarea.TIPOTAREA.IO1, 3);
        Tarea t5P4 = new Tarea(Tarea.TIPOTAREA.CPU, 3);
        p4TaskQ.add(t1P4);
        p4TaskQ.add(t2P4);
        p4TaskQ.add(t3P4);
        p4TaskQ.add(t4P4);
        p4TaskQ.add(t5P4);
        Tarea t1P5 = new Tarea(Tarea.TIPOTAREA.CPU, 2);
        Tarea t2P5 = new Tarea(Tarea.TIPOTAREA.IO2, 1);
        Tarea t3P5 = new Tarea(Tarea.TIPOTAREA.CPU, 1);
        Tarea t4P5 = new Tarea(Tarea.TIPOTAREA.IO1, 1);
        Tarea t5P5 = new Tarea(Tarea.TIPOTAREA.CPU, 2);
        p5TaskQ.add(t1P5);
        p5TaskQ.add(t2P5);
        p5TaskQ.add(t3P5);
        p5TaskQ.add(t4P5);
        p5TaskQ.add(t5P5);

    }

    private static void chequearLlegadasProcesos(){
        if(!blockedQ1.isEmpty()){
            if (blockedQ1.peek().blockedTimeFinish()){
                blockedQ1.peek().getTaskQ().poll();
                readyQ.add(blockedQ1.poll());
            }
        }
        if(!blockedQ2.isEmpty()){
            if (blockedQ2.peek().blockedTimeFinish()){
                blockedQ2.peek().getTaskQ().poll();
                readyQ.add(blockedQ2.poll());
            }
        }
        if(!blockedQ3.isEmpty()){
            if (blockedQ3.peek().blockedTimeFinish()){
                blockedQ3.peek().getTaskQ().poll();
                readyQ.add(blockedQ3.poll());
            }
        }
        /*
        if(p5.blockedTimeFinish()){
            p5.getTaskQ().poll();
            readyQ.add(p5);
        }*/

        if (time == p1.getAT())
            readyQ.add(p1);
        else if(time == p2.getAT())
            readyQ.add(p2);
        else if(time == p3.getAT())
            readyQ.add(p3);
        else if(time == p4.getAT())
            readyQ.add(p4);
        else if(time == p5.getAT())
            readyQ.add(p5);
    }

    public static void main(String[] args){

        //Matriz vacía
        for (int i=0; i<8; i++){
            for (int j=0; j<50; j++){
                matrix[i][j] = ' ';
            }
        }

        crearProcesos();

        //Llenar matriz con datos de procesos. FIFO. Colas de bloqueo tienen prioridad sobre la de listo
        for(time = 0; time < 50; time++){
            //Obviamente hay que hacer de nuevo porque es horrible...
            chequearLlegadasProcesos();
            //Veo cola de bloqueo 1, 2, 3 y cola de listo, en ese orden
            if(runningQ.isEmpty() && !readyQ.isEmpty()) {
                runningQ.add(readyQ.poll());
            }

            if(!blockedQ1.isEmpty()){
                matrix[5][time] = (char) (blockedQ1.peek().getPNum() + 48);
                blockedQ1.peek().decreaseTaskT();
            }
            if(!blockedQ2.isEmpty()){
                matrix[6][time] = (char) (blockedQ2.peek().getPNum() + 48);
                blockedQ2.peek().decreaseTaskT();
            }
            if(!blockedQ3.isEmpty()){
                matrix[7][time] = (char) (blockedQ3.peek().getPNum() + 48);
                blockedQ3.peek().decreaseTaskT();
            }

            if(!runningQ.isEmpty()){
                Proceso auxP = runningQ.poll();
                matrix[auxP.getPNum() - 1][time] = 'X';
                auxP.decreaseTaskT();
                if (!auxP.isTaskTCero()) {
                    runningQ.add(auxP);
                }
                else{
                    auxP.getTaskQ().poll();
                    if (auxP.getTaskQ().peek() != null) {
                        Tarea.TIPOTAREA auxTT = auxP.getTaskQ().peek().getTaskTipe();
                        switch (auxTT) {
                            case CPU:
                                readyQ.add(auxP);
                                break;
                            case IO1:
                                blockedQ1.add(auxP);
                                break;
                            case IO2:
                                blockedQ2.add(auxP);
                                break;
                            case IO3:
                                blockedQ3.add(auxP);
                                break;
                        }
                    }
                }
            }
        }

        //Mostrar matriz
        System.out.println("");
        for (int i=0; i<8; i++){
            for (int j=0; j<50; j++){
                System.out.printf("%c", matrix[i][j]);
            }
            System.out.println("");
        }
    }
}
