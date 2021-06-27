package events.setInspectionDateEvent;

public class SetInspectionDateEvent extends java.util.EventObject {

    private String pos;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SetInspectionDateEvent(Object source, String pos) {
        super(source);
        this.pos = pos;
    }

    public String getPos(){
        return this.pos;
    }
}
