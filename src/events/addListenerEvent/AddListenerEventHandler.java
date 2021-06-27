package events.addListenerEvent;

import events.addCustomerEvent.AddCustomerEvent;
import events.listener.AddCustomerEventListener;
import events.listener.AddListenerEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddListenerEventHandler {

    private List<AddListenerEventListener> listenerList = new LinkedList<>();

    public void add(AddListenerEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(AddListenerEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(AddListenerEvent event){
        for(AddListenerEventListener listener : listenerList){
            listener.onAddListenerEvent(event);
        }
    }

}
