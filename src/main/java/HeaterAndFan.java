public class HeaterAndFan {
    Room room;

    public  HeaterAndFan(Room r){
        room = r;
    }

    public void heater(){
        System.out.println("Включен обогреватель\nТекущие показатели");
        room.setCurrentValues(room.getCurrentTemp() + 1, room.getCurrentHum() - 4);
        room.printCurrentIndicators();
    }

    public void fun(){
        System.out.println("Включен вентилятор\nТекущие показатели");
        room.setCurrentValues(room.getCurrentTemp() -1, room.getCurrentHum() + 4);
        room.printCurrentIndicators();
    }

    public Room getRoom() {
        return room;
    }
}

