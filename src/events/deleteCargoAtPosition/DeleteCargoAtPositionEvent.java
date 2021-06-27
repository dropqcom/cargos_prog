package events.deleteCargoAtPosition;

public class DeleteCargoAtPositionEvent extends java.util.EventObject {

    private String position;

    public DeleteCargoAtPositionEvent(Object source, String position) {
        super(source);
        this.position = position;
    }

    public String getPosition(){
        return this.position;
    }
}
