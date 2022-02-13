public class Room implements Runnable{


    private double currentTemp;
    private double currentHum;
    //private HeaterAndFan heaterAndFan;

    public Room( double h, double t){
        //heaterAndFan = heater;
        currentHum = h;
        currentTemp = t;
    }

    public void printCurrentIndicators(){
        System.out.println("Температура в комнате = " + currentTemp +
                "\nВлажность в комнате = " + currentHum);
    }

    public void changeCurrentValues(){
        currentHum = currentHum+Math.random()-0.5;
        currentTemp = currentTemp+Math.random()-0.5;
    }

    public double getCurrentTemp(){ return currentTemp; }

    public double getCurrentHum() { return currentHum; }

    public void setCurrentValues(double t, double h){
        currentHum = h;
        currentTemp = t;
    }

    @Override
    public void run() {
        while (true){
            changeCurrentValues();
            printCurrentIndicators();
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            printCurrentIndicators();
        }
    }
}