package events.listener;

import events.setInspectionDateEvent.SetInspectionDateEvent;

public interface SetInspectionDateEventListener {
    void onSetInspectionDateEvent(SetInspectionDateEvent event);
}
