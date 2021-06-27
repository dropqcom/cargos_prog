package events.printEvent;

import java.util.EventObject;

public class PrintEvent extends EventObject {

    private String message;

    public PrintEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
