package events.DeserializationJBPEvent;

import events.listener.DeserializationJBPEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.LogTyp;
import persistence.Persistence;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class DeserializationJBPEventListenerImpl implements DeserializationJBPEventListener {


    private Storage storage;
    private CustomerManagement management;

    public DeserializationJBPEventListenerImpl(Storage storage, CustomerManagement management){
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onDeserializationJBPEvent(DeserializationJBPEvent event) {
        if(null != event){
            File file = new File("beanEncoded.xml");
            if(file.exists()){
                Collection<CargoImpl> loadedCargo = Persistence.decoderJBP("beanEncoded.xml");
                assert loadedCargo != null;
                ArrayList<CargoImpl> cargoList = new ArrayList<>(loadedCargo);
                for (CargoImpl cargo : cargoList) {
                    try {
                        management.addCustomer(cargo.getOwner());
                        management.addCargo(cargo.getOwner(), cargo, storage);
                    } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                LogEvent logEvent = new LogEvent(this, LogTyp.LOAD_JBP);
                logEventHandler.handle2(logEvent);
            }
        }
    }
}
