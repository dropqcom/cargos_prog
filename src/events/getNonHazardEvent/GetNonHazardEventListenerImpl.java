package events.getNonHazardEvent;

import events.listener.GetNonHazardListener;
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

public class GetNonHazardEventListenerImpl implements GetNonHazardListener {

    private Storage storage;
    private CustomerManagement management;

    public GetNonHazardEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    @Override
    public void onGetNonHazardEvent(GetNonHazardEvent event) {
        if(null != event.getNonHazardStr()){
            Collection<Hazard> nonHazardCollection = storage.getNonHazardOfStorage();
            ArrayList<Hazard> arrayList = new ArrayList<>(nonHazardCollection);
            this.print(arrayList.toString());
            LogEvent logEvent = new LogEvent(this, LogTyp.GET_NON_HAZARD);
            logEventHandler.handle2(logEvent);
        }

    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
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
