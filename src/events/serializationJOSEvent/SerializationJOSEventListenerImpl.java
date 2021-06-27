package events.serializationJOSEvent;

import events.listener.SerializationJOSEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.LogTyp;
import persistence.Persistence;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;

import java.util.Collection;

public class SerializationJOSEventListenerImpl implements SerializationJOSEventListener {

    private Storage storage;
    private CustomerManagement management;

    public SerializationJOSEventListenerImpl(Storage storage, CustomerManagement management){
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onSerializationJOSEvent(SerializationJOSEvent event) {
        if(null != event){
            Collection<CargoImpl> allCargo = storage.getAllCargo();
            Persistence.serialize("allCargo.ser",allCargo);
            LogEvent logEvent = new LogEvent(this, LogTyp.SAVE_JOS);
            logEventHandler.handle2(logEvent);
        }
    }
}
