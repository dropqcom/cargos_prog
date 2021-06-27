package events.logEvent;

public class LogEventUserInput extends java.util.EventObject{


    private String string;

    public LogEventUserInput(Object source,String string) {
        super(source);
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
