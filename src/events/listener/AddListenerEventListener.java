package events.listener;

import events.addListenerEvent.AddListenerEvent;

public interface AddListenerEventListener extends java.util.EventListener {

    void onAddListenerEvent(AddListenerEvent event);

}
