package events.showSpecificCargoTypEvent;

import events.listener.ShowSpecificCargoTypEventListener;


import java.util.LinkedList;
import java.util.List;

public class ShowSpecificCargoTypEventHandler {



    private List<ShowSpecificCargoTypEventListener> listenerList = new LinkedList<>();

    public void add(ShowSpecificCargoTypEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(ShowSpecificCargoTypEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(ShowSpecificCargoTypEvent event){
        for(ShowSpecificCargoTypEventListener listener : listenerList){
            listener.onShowSpecificCargoTypEvent(event);
        }
    }
}
