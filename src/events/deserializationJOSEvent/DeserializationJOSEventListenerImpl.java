package events.deserializationJOSEvent;

import events.listener.DeserializationJOSEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
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

public class DeserializationJOSEventListenerImpl implements DeserializationJOSEventListener {

    private Storage storage;
    private CustomerManagement management;

    public DeserializationJOSEventListenerImpl(Storage storage, CustomerManagement management){
        this.storage = storage;
        this.management = management;
    }

    //PrintEvent-----------------------------------------------------------------------------
    private PrintEventHandler printEventHandler;

    public void setPrintEventHandler(PrintEventHandler printEventHandler) {
        this.printEventHandler = printEventHandler;
    }

    public void print(String message) {
        PrintEvent printEvent = new PrintEvent(this, message);
        if (this.printEventHandler != null) {
            this.printEventHandler.handle(printEvent);
        }
    }
    //---------------------------------------------------------------------------------------

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onDeserializationJOSEvent(DeserializationJOSEvent event) {
        if(null != event){
            File file = new File("allCargo.ser");
            if(file.exists()){
                Collection<CargoImpl> loadedCargo = Persistence.deserialize("allCargo.ser");
                assert loadedCargo != null;
                ArrayList<CargoImpl> cargoList = new ArrayList<>(loadedCargo);
                for(int i = 0; i < cargoList.size(); i++){
                    try {
                        management.addCustomer(cargoList.get(i).getOwner());
                        management.addCargo(cargoList.get(i).getOwner(),cargoList.get(i),storage);
                    } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                        this.print("Storage is full");
                        return;
                    }
                }
                LogEvent logEvent = new LogEvent(this, LogTyp.LOAD_JOS);
                logEventHandler.handle2(logEvent);
            }
        }
    }
}
