package events.showCustomersAndCargosEvent;

import events.listener.ShowCustomersAndCargosEventListener;

import java.util.LinkedList;
import java.util.List;

public class ShowCustomersAndCargosEventHandler {

    private List<ShowCustomersAndCargosEventListener> listenerList = new LinkedList<>();

    public void add(ShowCustomersAndCargosEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(ShowCustomersAndCargosEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(ShowCustomersAndCargosEvent event){
        for(ShowCustomersAndCargosEventListener listener : listenerList){
            listener.onShowCustomersAndCargosEvent(event);
        }
    }
}
