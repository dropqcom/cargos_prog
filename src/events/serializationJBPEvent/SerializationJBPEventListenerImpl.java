package events.serializationJBPEvent;

import events.listener.SerializationJBPEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.LogTyp;
import persistence.Persistence;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;

import java.util.Collection;

public class SerializationJBPEventListenerImpl implements SerializationJBPEventListener {

    private Storage storage;
    private CustomerManagement management;

    public SerializationJBPEventListenerImpl(Storage storage, CustomerManagement management){
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onSerializationJBPEvent(SerializationJBPEvent event) {
        if(null != event){
            Collection<CargoImpl> allCargo = storage.getAllCargo();
            Persistence.encoderJBP("beanEncoded.xml",allCargo);
        }
        LogEvent logEvent = new LogEvent(this,LogTyp.SAVE_JBP);
        logEventHandler.handle2(logEvent);
    }
}
