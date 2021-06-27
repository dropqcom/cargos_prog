package events.listener;

import events.deserializationJOSEvent.DeserializationJOSEvent;

public interface DeserializationJOSEventListener extends java.util.EventListener {

    void onDeserializationJOSEvent(DeserializationJOSEvent event);
}
