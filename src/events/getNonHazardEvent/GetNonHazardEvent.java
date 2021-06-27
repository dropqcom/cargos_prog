package events.getNonHazardEvent;

public class GetNonHazardEvent extends java.util.EventObject {

    private String nonHazardStr;

    public GetNonHazardEvent(Object source, String nonHazardStr) {
        super(source);
        this.nonHazardStr = nonHazardStr;
    }

    public String getNonHazardStr() {
        return this.nonHazardStr;
    }
}
