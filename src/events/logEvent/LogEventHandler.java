package events.logEvent;

import events.listener.LogEventListener;

import java.util.LinkedList;
import java.util.List;

public class LogEventHandler {
    private List<LogEventListener> eventListenerList = new LinkedList<>();

    public void addListener(LogEventListener listener) {
        this.eventListenerList.add(listener);
    }

    public void removeListener(LogEventListener listener) {
        this.eventListenerList.remove(listener);
    }

    public void handle2(LogEvent event) {
        for (LogEventListener listener : eventListenerList) {
            listener.onLogEvent(event);
        }
    }
    public void handle(LogEventUserInput event) {
        for (LogEventListener listener : eventListenerList) {
            listener.onLogEventInsideEvent(event);
        }
    }

}
