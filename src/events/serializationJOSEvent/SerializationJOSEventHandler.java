package events.serializationJOSEvent;

import events.listener.SerializationJOSEventListener;

import java.util.LinkedList;
import java.util.List;

public class SerializationJOSEventHandler {

    private List<SerializationJOSEventListener> listenerList = new LinkedList<>();

    public void add(SerializationJOSEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(SerializationJOSEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(SerializationJOSEvent event){
        for(SerializationJOSEventListener listener : listenerList){
            listener.onSerializationJOSEvent(event);
        }
    }
}
