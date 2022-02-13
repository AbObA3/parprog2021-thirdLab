
    public class SensorH implements Runnable{
        Controller controller;

        public SensorH(Controller c){ controller = c;}

        @Override
        public void run() {
            while (true) {
                controller.checkWarming();
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }

