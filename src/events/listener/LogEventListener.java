package events.listener;

import events.logEvent.LogEvent;
import events.logEvent.LogEventUserInput;

public interface LogEventListener extends java.util.EventListener {

    void onLogEvent(LogEvent event);

    void onLogEventInsideEvent(LogEventUserInput event);

}
