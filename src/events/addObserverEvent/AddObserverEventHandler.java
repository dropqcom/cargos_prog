package events.addObserverEvent;

import events.listener.AddObserverEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddObserverEventHandler {


    private List<AddObserverEventListener> listenerList = new LinkedList<>();

    public void add(AddObserverEventListener listener){
        this.listenerList.add(listener);
    }
    public void remove(AddObserverEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(AddObserverEvent event){
        for(AddObserverEventListener listener : listenerList){
            listener.onAddObserverEvent(event);
        }
    }

}
