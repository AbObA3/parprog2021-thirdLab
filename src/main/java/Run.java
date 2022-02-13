
public class Run
{
    public static void main( String[] args )
    {
        Room room = new Room(70, 4);
        HeaterAndFan heaterAndFan = new HeaterAndFan(room);
        Controller controller = new Controller(80, 6, heaterAndFan);
        SensorC sensorC = new SensorC(controller);
        SensorH sensorH = new SensorH(controller);

        new Thread(room).start();
        new Thread(sensorC).start();
        new Thread(sensorH).start();
    }
}