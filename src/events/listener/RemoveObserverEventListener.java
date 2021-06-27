package events.listener;

import events.removeObserverEvent.RemoveObserverEvent;

public interface RemoveObserverEventListener extends java.util.EventListener {

    void onRemoveObserverEvent(RemoveObserverEvent event);
}
