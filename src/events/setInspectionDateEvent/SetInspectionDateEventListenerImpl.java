package events.setInspectionDateEvent;

import events.listener.SetInspectionDateEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.LogTyp;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;

import java.util.Date;

public class SetInspectionDateEventListenerImpl implements SetInspectionDateEventListener {

    private Storage storage;
    private CustomerManagement management;

    public SetInspectionDateEventListenerImpl(Storage storage, CustomerManagement management){
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
    public void onSetInspectionDateEvent(SetInspectionDateEvent event) {
        if(null != event.getPos()){
            int position = Integer.parseInt(event.getPos());
            if(position > storage.getHowManyCargo() || position < 0){
                this.print("Index out of bounds, check your input");
            }else{
                CargoImpl cargo = storage.getCargo(position);
                Date now = new Date();
                cargo.setLastInspectionDate(now);
                cargo.setHasStorageDateBeenSet(true);
                management.deleteCargo(position,storage);
                try {
                    management.addCargo(cargo.getOwner(),cargo,storage);
                } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                    //wird nicht erreicht
                    e.printStackTrace();
                }
//                storage.delete(position);
//                storage.add(cargo,position);
            }
            LogEvent logEvent = new LogEvent(this, LogTyp.SET_INSPECTION);
            logEventHandler.handle2(logEvent);
        }
    }
}
