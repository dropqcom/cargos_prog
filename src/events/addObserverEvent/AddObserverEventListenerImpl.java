package events.addObserverEvent;

import events.listener.AddObserverEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.LogTyp;
import observer.ObserverSizeImpl;
import storageContract.administration.CustomerManagement;
import storageContract.administration.StorageImpl;



public class AddObserverEventListenerImpl implements AddObserverEventListener {

    private StorageImpl storage;
    private CustomerManagement management;

    public AddObserverEventListenerImpl(StorageImpl storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onAddObserverEvent(AddObserverEvent event) {
        if(null != event.getClass_name()){
            ObserverSizeImpl observerSize = new ObserverSizeImpl(storage);
            storage.signIn(observerSize);
            LogEvent logEvent = new LogEvent(this, LogTyp.ADD_OBSERVER);
            logEventHandler.handle2(logEvent);
        }
    }
}
