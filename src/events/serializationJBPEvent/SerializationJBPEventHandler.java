package events.serializationJBPEvent;

import events.listener.SerializationJBPEventListener;

import java.util.LinkedList;
import java.util.List;

public class SerializationJBPEventHandler {

    private List<SerializationJBPEventListener> listenerList = new LinkedList<>();

    public void add(SerializationJBPEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(SerializationJBPEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(SerializationJBPEvent event){
        for(SerializationJBPEventListener listener : listenerList){
            listener.onSerializationJBPEvent(event);
        }
    }

}

