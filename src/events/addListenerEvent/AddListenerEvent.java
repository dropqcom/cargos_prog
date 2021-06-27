package events.addListenerEvent;

import events.addCargoEvent.AddCargoEventHandler;

public class AddListenerEvent extends java.util.EventObject {

    AddCargoEventHandler addCargoEventHandler;

    public AddListenerEvent(Object source,AddCargoEventHandler addCargoEventHandler){
        super(source);
        this.addCargoEventHandler = addCargoEventHandler;
    }

    public AddCargoEventHandler getAddCargoEventHandler() {
        return addCargoEventHandler;
    }
}
