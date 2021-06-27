package events.logEvent;

import log.LogTyp;

public class LogEvent extends java.util.EventObject {

    private LogTyp logTyp;



    public LogEvent(Object source, LogTyp logTyp) {
        super(source);
        this.logTyp = logTyp;
    }



    public LogTyp getLogTyp() {
        return logTyp;
    }


}
