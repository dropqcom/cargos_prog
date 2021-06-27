package events.addCargoEvent;

public class AddCargoEvent extends java.util.EventObject {

    private String[] cargoStr;

    public AddCargoEvent(Object source, String[] cargoStr) {
        super(source);
        this.cargoStr = cargoStr;
    }

    public String[] getCargoStr(){
        return this.cargoStr;
    }
}
