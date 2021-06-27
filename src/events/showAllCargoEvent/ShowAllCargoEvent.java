package events.showAllCargoEvent;

public class ShowAllCargoEvent extends java.util.EventObject {

    private String cargoStr;

    public ShowAllCargoEvent(Object source, String cargoStr) {
        super(source);
        this.cargoStr = cargoStr;
    }

    public String getCargoStr(){
        return this.cargoStr;
    }
}
