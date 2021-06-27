package events.getHazardEvent;

import events.listener.GetHazardEventListener;

import java.util.LinkedList;
import java.util.List;

public class GetHazardEventHandler {

    private List<GetHazardEventListener> listenerList = new LinkedList<>();

    public void add(GetHazardEventListener listener){
        this.listenerList.add(listener);
    }
    public void remove(GetHazardEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(GetHazardEvent event){
        for(GetHazardEventListener listener : listenerList){
            listener.onGetHazardEvent(event);
        }
    }

}
