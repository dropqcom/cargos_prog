package events.getHazardEvent;

import events.listener.GetHazardEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.LogTyp;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.Hazard;

import java.util.ArrayList;
import java.util.Collection;

public class GetHazardEventListenerImpl implements GetHazardEventListener {

    private Storage storage;
    private CustomerManagement management;

    public GetHazardEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onGetHazardEvent(GetHazardEvent event) {
        if(null != event.getHazardStr()){
            Collection<Hazard> hazardCollection = storage.getHazardOfStorage();
            ArrayList<Hazard> hazards = new ArrayList<>(hazardCollection);
            this.print(hazards.toString());
            LogEvent logEvent = new LogEvent(this, LogTyp.GET_HAZARD);
            logEventHandler.handle2(logEvent);
        }
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
}
