package events.getNonHazardEvent;

import events.listener.GetNonHazardListener;

import java.util.LinkedList;
import java.util.List;

public class GetNonHazardEventHandler {

    private List<GetNonHazardListener> listenerList = new LinkedList<>();

    public void add(GetNonHazardListener listener){
        this.listenerList.add(listener);
    }
    public void remove(GetNonHazardListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(GetNonHazardEvent event){
        for(GetNonHazardListener listener : listenerList){
            listener.onGetNonHazardEvent(event);
        }
    }

}
