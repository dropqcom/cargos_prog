package events.printEvent;

import events.listener.PrintEventListener;

import java.util.LinkedList;
import java.util.List;

public class PrintEventHandler {

    private List<PrintEventListener> listenerList = new LinkedList<>();

    public void add(PrintEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(PrintEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(PrintEvent event){
        for(PrintEventListener listener : listenerList){
            listener.onPrintEvent(event);
        }
    }

}
