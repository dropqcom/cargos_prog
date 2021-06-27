package events.addListenerEvent;

import events.addCargoEvent.AddCargoEventHandler;
import events.addCargoEvent.AddCargoEventListenerImpl;
import events.listener.AddCargoEventListener;
import events.listener.AddListenerEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.LogTyp;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

public class AddListenerEventListenerImpl implements AddListenerEventListener {

    private Storage storage;
    private CustomerManagement management;


    public AddListenerEventListenerImpl(Storage storage, CustomerManagement management){
        this.storage = storage;
        this.management = management;
    }


    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    private PrintEventHandler printEventHandler;
    public void setPrintEventHandler(PrintEventHandler printEventHandler){
        this.printEventHandler = printEventHandler;
    }

    @Override
    public void onAddListenerEvent(AddListenerEvent event) {
        AddCargoEventListenerImpl addCargoEventListener = new AddCargoEventListenerImpl(storage,management);
        AddCargoEventHandler addCargoEventHandler = event.getAddCargoEventHandler();
        addCargoEventHandler.add(addCargoEventListener);

        addCargoEventListener.setPrintEventHandler(printEventHandler);
        addCargoEventListener.setLogEventHandler(logEventHandler);

        LogEvent logEvent = new LogEvent(this, LogTyp.ADD_LISTENER);
        logEventHandler.handle2(logEvent);
    }
}
