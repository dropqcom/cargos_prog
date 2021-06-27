package events.getHazardEvent;

public class GetHazardEvent extends java.util.EventObject {

    private String hazardStr;

    public GetHazardEvent(Object source, String hazardStr) {
        super(source);
        this.hazardStr = hazardStr;
    }

    public String getHazardStr(){
        return this.hazardStr;
    }
}
