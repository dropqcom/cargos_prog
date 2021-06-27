package events.listener;

import events.deleteCargoAtPosition.DeleteCargoAtPositionEvent;

public interface DeleteCargoAtPositionEventListener extends java.util.EventListener {

    void onDeleteCargoAtPositionEvent(DeleteCargoAtPositionEvent event);
}
