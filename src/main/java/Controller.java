

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
            while(heaterAndFan.room.getCurrentTemp()/temp > 1.01 && heaterAndFan.room.getCurrentHum()/hum < 0.99 || (heaterAndFan.room.getCurrentTemp()/temp > 1.01 && heaterAndFan.room.getCurrentHum()/hum==1) ){
                wait();
            }

                System.out.println("Температура в комнате ниже рекомендуемой на " +
                        (temp-heaterAndFan.room.getCurrentTemp() ) + " градусов\n" + "влажность в комнате выше рекомендуемой на " + (heaterAndFan.room.getCurrentHum()-hum) + " процентов");
                heaterAndFan.heater();

                notify();

        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    synchronized void checkWarming(){
        try {
            while ((heaterAndFan.room.getCurrentTemp()/temp < 0.99 && heaterAndFan.room.getCurrentHum()/hum > 1.01) || (heaterAndFan.room.getCurrentTemp()/temp < 0.99 && heaterAndFan.room.getCurrentHum()/hum==1)  ){
                wait();
            }

                System.out.println("Температура в комнате выше рекомендуемой на " +
                        (heaterAndFan.room.getCurrentTemp()-temp ) + " градусов\n" + "влажность в комнате ниже рекомендуемой на " + (heaterAndFan.room.getCurrentHum()-hum) + " процентов");
                heaterAndFan.fun();
                notify();

        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }


}