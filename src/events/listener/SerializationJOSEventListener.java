package events.listener;

import events.serializationJOSEvent.SerializationJOSEvent;

public interface SerializationJOSEventListener extends java.util.EventListener {

    void onSerializationJOSEvent(SerializationJOSEvent event);
}
