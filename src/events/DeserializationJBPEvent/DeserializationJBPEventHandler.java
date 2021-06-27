package events.DeserializationJBPEvent;

import events.listener.DeserializationJBPEventListener;

import java.util.LinkedList;
import java.util.List;

public class DeserializationJBPEventHandler {

    private List<DeserializationJBPEventListener> listenerList = new LinkedList<>();

    public void add(DeserializationJBPEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(DeserializationJBPEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(DeserializationJBPEvent event){
        for(DeserializationJBPEventListener listener : listenerList){
            listener.onDeserializationJBPEvent(event);
        }
    }
}
