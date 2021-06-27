package events.showAllCargoEvent;

import events.listener.ShowAllCargoEventListener;

import java.util.LinkedList;
import java.util.List;

public class ShowAllCargoEventHandler {

    private List<ShowAllCargoEventListener> listenerList = new LinkedList<>();

    public void add(ShowAllCargoEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(ShowAllCargoEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(ShowAllCargoEvent event){
        for(ShowAllCargoEventListener listener : listenerList){
            listener.onShowAllCargoEvent(event);
        }
    }
}
