package events.addCargoEvent;

import events.listener.AddCargoEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddCargoEventHandler {

    private List<AddCargoEventListener> listenerList = new LinkedList<>();

    public void add(AddCargoEventListener listener){
        this.listenerList.add(listener);
    }
    public void remove(AddCargoEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(AddCargoEvent event){
        for(AddCargoEventListener listener : listenerList){
            listener.onAddCargoEvent(event);
        }
    }

    public List<AddCargoEventListener> getListenerList() {
        return listenerList;
    }

}
