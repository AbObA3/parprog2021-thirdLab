

public class Controller{
    private double hum;                     //влажность
    private double temp;                    //температура
    private HeaterAndFan heaterAndFan;      //обогреватель и вентилятор


    public Controller(double h, double t, HeaterAndFan heater){
        hum = h;
        temp = t;
        heaterAndFan = heater;
    }

    synchronized void checkCooling(){
        try {
            while (heaterAndFan.room.getCurrentTemp()/temp > 1.01)
                wait();
            System.out.println("Температура ниже заданной на " +
                    (temp - heaterAndFan.room.getCurrentTemp()) + " градусов");
            heaterAndFan.heater();

            notify();
        }catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

    }


    synchronized void checkWarming(){
        try {
            while (heaterAndFan.room.getCurrentTemp()/temp < 0.99)
                wait();
            System.out.println("Температура выше заданной на " +
                    (heaterAndFan.room.getCurrentTemp() - temp) + " градусов");
            heaterAndFan.fun();
            notify();
        }catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

    }


}