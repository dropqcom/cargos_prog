package events.listener;

import events.getNonHazardEvent.GetNonHazardEvent;

public interface GetNonHazardListener extends java.util.EventListener {

    void onGetNonHazardEvent(GetNonHazardEvent event);

}
