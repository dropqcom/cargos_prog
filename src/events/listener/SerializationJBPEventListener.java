package events.listener;

import events.serializationJBPEvent.SerializationJBPEvent;


public interface SerializationJBPEventListener extends java.util.EventListener {


    void onSerializationJBPEvent(SerializationJBPEvent event);

}
