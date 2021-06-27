package events.listener;

import events.addObserverEvent.AddObserverEvent;

public interface AddObserverEventListener extends java.util.EventListener {

    void onAddObserverEvent(AddObserverEvent event);

}
