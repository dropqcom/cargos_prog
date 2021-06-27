package events.showSpecificCargoTypEvent;

public class ShowSpecificCargoTypEvent extends java.util.EventObject {


    private String cargoTyp;

    public ShowSpecificCargoTypEvent(Object source, String cargoTyp) {
        super(source);
        this.cargoTyp = cargoTyp;
    }

    public String getCargoTyp() {
        return cargoTyp;
    }
}
