
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Settings implements Runnable{
    static int rooms;
    static double[][] tempHumi;
    Sensor sensor;
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество комнат:");
        rooms = in.nextInt();
        tempHumi = new double[rooms][2];
        System.out.println("Укажите для каждой комнаты предпочтительные настройки температуры и влажности:");
        for(int i = 0; i<rooms; i++) {
            System.out.printf("Комната  %d \n", i + 1);
            System.out.print("Температура: ");
            tempHumi[i][0] = in.nextDouble();
            System.out.print("Влажность: ");
            tempHumi[i][1] = in.nextDouble();
        }
        sensor = new Sensor();
        Thread sensorThread = new Thread(sensor);
        sensorThread.start();
        return;
    }
}
class Sensor  implements Runnable {

   static boolean[][] heatCold = new boolean[Settings.rooms][2];
   static double[][] sensors = new double[Settings.rooms][2];

    Controller controller;

    boolean check = false;
    @Override
    public void run() {
        while(true){
            for(int i = 0 ; i <Settings.rooms ; i++){
                heatCold[i][0]=false;
                heatCold[i][1]=false;
            }
            Random random = new Random();
            for(int i = 0; i<Settings.rooms; i++){
                System.out.printf("Комната  %d \n", i + 1);
                sensors[i][0]=Settings.tempHumi[i][0]+random.nextDouble(2)-1;
                System.out.printf("Температура: %f \n",sensors[i][0]);
                sensors[i][1]=Settings.tempHumi[i][1]+random.nextDouble(2)-1;
                System.out.printf("Влажность: %f \n", sensors[i][1]);
                if(sensors[i][0]> Settings.tempHumi[i][0]*1.01 || sensors[i][1]>Settings.tempHumi[i][1]*1.01){
                    heatCold[i][0]=true;
                    heatCold[i][1]=true;
                    check = true;
                }
                else if(sensors[i][0]< Settings.tempHumi[i][0]*0.99 || sensors[i][1]<Settings.tempHumi[i][1]*0.99){
                    check = true;
                    heatCold[i][0]=true;
                    heatCold[i][1]=false;
                }
            }
            if(check){
                controller = new Controller();
                Thread controllerThread = new Thread(controller);
                controllerThread.start();
            }



            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}

        }

    }

}

class Controller  implements Runnable {

Thread[] fanThreads = new Thread[Settings.rooms];
Thread[] heaterThreads = new Thread[Settings.rooms];
    @Override
    public void run(){
        System.out.println("Контроллер активен");
            for(int i = 0 ; i<Settings.rooms;i++){
                if(Sensor.heatCold[i][0]==true && Sensor.heatCold[i][1]==true) {
                    fanThreads[i]= new Thread(new Fan(i+1));
                    fanThreads[i].start();
                    continue;
                }
                if (Sensor.heatCold[i][0]==true && Sensor.heatCold[i][1]==false){
                    heaterThreads[i]=new Thread(new Heater(i+1));
                    heaterThreads[i].start();
                    continue;
                }

            }



    }
}
class Heater implements Runnable{

   private int m_numRoom;
   Heater(int numRoom){
       this.m_numRoom=numRoom;
   }
    @Override
    public void run() {
       System.out.printf("Обогреватель работает в комнате %d \n", m_numRoom);
       Sensor.sensors[m_numRoom-1][0]= Settings.tempHumi[m_numRoom-1][0];
        Sensor.sensors[m_numRoom-1][1]= Settings.tempHumi[m_numRoom-1][1];
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
        System.out.printf("Температура=%f и влажность=%f восстановлены  в %d комнате\n",Sensor.sensors[m_numRoom-1][0], Sensor.sensors[m_numRoom-1][1], m_numRoom);

    }
}
class Fan implements Runnable{
    private int m_numRoom;
    Fan(int numRoom){
        this.m_numRoom=numRoom;
    }
    @Override
    public void run() {
        System.out.printf("Вентилятор работает в комнате %d \n", m_numRoom);
        Sensor.sensors[m_numRoom-1][0]= Settings.tempHumi[m_numRoom-1][0];
        Sensor.sensors[m_numRoom-1][1]= Settings.tempHumi[m_numRoom-1][1];
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
        System.out.printf("Температура=%f и влажность=%f восстановлены в %d комнате\n",Sensor.sensors[m_numRoom-1][0],Sensor.sensors[m_numRoom-1][1], m_numRoom);
    }


}

public class Run {

    static Settings settings;


    public static void main(String[] args) {
        settings= new Settings();
        Thread settingThread = new Thread(settings);
        settingThread.start();





    }


}
