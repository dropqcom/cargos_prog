package events.deleteCargoAtPosition;

import events.listener.DeleteCargoAtPositionEventListener;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

public class DeleteCargoAtPositionEventListenerImpl implements DeleteCargoAtPositionEventListener {

    private Storage storage;
    private CustomerManagement management;

    public DeleteCargoAtPositionEventListenerImpl(Storage storage, CustomerManagement management) {
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
    public void onDeleteCargoAtPositionEvent(DeleteCargoAtPositionEvent event) {
        if(null != event.getPosition()){
            int pos = Integer.parseInt(event.getPosition());
            if(pos >= 0 && pos <= storage.getStorageSize()){
                if(null != storage.getCargo(pos)){
                    this.management.deleteCargo(pos,storage);
                }else{
                    this.print("No cargo at " + pos);
                    return;
                }
            }else{
                this.print("Index out of bounds, check your input");
                return;
            }
            this.print("[INFO] cargo at " + pos + " has been deleted");
            String message = "Cargo at " + pos + " has been deleted";
            LogEventUserInput logEventUserInput = new LogEventUserInput(this,message);
            logEventHandler.handle(logEventUserInput);
        }

    }
}
