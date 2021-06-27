package events.listener;

import events.DeserializationJBPEvent.DeserializationJBPEvent;

public interface DeserializationJBPEventListener extends java.util.EventListener  {

    void onDeserializationJBPEvent(DeserializationJBPEvent event);

}
