package events.removeObserverEvent;

import events.listener.RemoveObserverEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.LogTyp;
import observer.Observer;
import observer.ObserverSizeImpl;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

import java.util.List;

public class RemoveObserverEventListenerImpl implements RemoveObserverEventListener {


    private Storage storage;
    private CustomerManagement management;


    public RemoveObserverEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onRemoveObserverEvent(RemoveObserverEvent event) {
        if(null != event.getClass_name()){
            List<Observer> list = storage.getObserverList();
            for(int i = 0; i < list.size(); i++){
                if(null != list.get(i)){
                    Observer observer = list.get(i);
                    if(observer.getClass().equals(ObserverSizeImpl.class)){
                        storage.signOut(observer);
                        LogEvent logEvent = new LogEvent(this, LogTyp.REMOVE_OBSERVER);
                        logEventHandler.handle2(logEvent);
                        break;
                    }
                }
            }
        }
    }
}
