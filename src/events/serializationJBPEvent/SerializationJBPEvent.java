package events.serializationJBPEvent;

public class SerializationJBPEvent extends java.util.EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SerializationJBPEvent(Object source) {
        super(source);
    }
}
