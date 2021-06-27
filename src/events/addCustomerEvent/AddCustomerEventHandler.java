package events.addCustomerEvent;


import events.listener.AddCustomerEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddCustomerEventHandler {

    private List<AddCustomerEventListener> listenerList = new LinkedList<>();

    public void add(AddCustomerEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(AddCustomerEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(AddCustomerEvent event){
        for(AddCustomerEventListener listener : listenerList){
            listener.onAddCustomerEvent(event);
        }
    }

}
