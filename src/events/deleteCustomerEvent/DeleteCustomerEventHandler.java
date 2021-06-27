package events.deleteCustomerEvent;

import events.listener.DeleteCustomerEventListener;

import java.util.LinkedList;
import java.util.List;

public class DeleteCustomerEventHandler {


    private List<DeleteCustomerEventListener> listenerList = new LinkedList<>();

    public void add(DeleteCustomerEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(DeleteCustomerEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(DeleteCustomerEvent event){
        for(DeleteCustomerEventListener listener : listenerList){
            listener.onDeleteCustomerEvent(event);
        }
    }



}
