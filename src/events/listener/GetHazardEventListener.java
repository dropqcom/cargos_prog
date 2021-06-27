package events.listener;

import events.getHazardEvent.GetHazardEvent;

public interface GetHazardEventListener extends java.util.EventListener {

    void onGetHazardEvent(GetHazardEvent event);
}
