public class SensorC implements Runnable{
    Controller controller;

    public SensorC(Controller c){ controller = c;}

    @Override
    public void run() {
        while (true) {
            controller.checkCooling();
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
