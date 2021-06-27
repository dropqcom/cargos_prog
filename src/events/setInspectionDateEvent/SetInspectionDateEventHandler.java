package events.setInspectionDateEvent;

import events.listener.SetInspectionDateEventListener;

import java.util.LinkedList;
import java.util.List;

public class SetInspectionDateEventHandler {

    private List<SetInspectionDateEventListener> listenerList = new LinkedList<>();

    public void add(SetInspectionDateEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(SetInspectionDateEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(SetInspectionDateEvent event){
        for(SetInspectionDateEventListener listener : listenerList){
            listener.onSetInspectionDateEvent(event);
        }
    }
}
