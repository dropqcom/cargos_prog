package events.deleteCargoAtPosition;

import events.listener.DeleteCargoAtPositionEventListener;

import java.util.LinkedList;
import java.util.List;

public class DeleteCargoAtPositionEventHandler {

    private List<DeleteCargoAtPositionEventListener> listenerList = new LinkedList<>();

    public void add(DeleteCargoAtPositionEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(DeleteCargoAtPositionEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(DeleteCargoAtPositionEvent event){
        for(DeleteCargoAtPositionEventListener listener : listenerList){
            listener.onDeleteCargoAtPositionEvent(event);
        }
    }

}
