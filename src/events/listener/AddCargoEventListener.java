package events.listener;

import events.addCargoEvent.AddCargoEvent;

public interface AddCargoEventListener extends java.util.EventListener {

    void onAddCargoEvent(AddCargoEvent event);

}
