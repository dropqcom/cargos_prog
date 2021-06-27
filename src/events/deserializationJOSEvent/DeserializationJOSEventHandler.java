package events.deserializationJOSEvent;

import events.listener.DeserializationJOSEventListener;

import java.util.LinkedList;
import java.util.List;

public class DeserializationJOSEventHandler {

    private List<DeserializationJOSEventListener> listenerList = new LinkedList<>();

    public void add(DeserializationJOSEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(DeserializationJOSEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(DeserializationJOSEvent event){
        for(DeserializationJOSEventListener listener : listenerList){
            listener.onDeserializationJOSEvent(event);
        }
    }
}
