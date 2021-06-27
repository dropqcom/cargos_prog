package events.removeObserverEvent;

import events.addObserverEvent.AddObserverEvent;
import events.listener.AddObserverEventListener;
import events.listener.RemoveObserverEventListener;

import java.util.LinkedList;
import java.util.List;

public class RemoveObserverEventHandler {


    private List<RemoveObserverEventListener> listenerList = new LinkedList<>();

    public void add(RemoveObserverEventListener listener){
        this.listenerList.add(listener);
    }
    public void remove(RemoveObserverEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(RemoveObserverEvent event){
        for(RemoveObserverEventListener listener : listenerList){
            listener.onRemoveObserverEvent(event);
        }
    }
}
