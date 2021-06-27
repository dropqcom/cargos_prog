package events.showCustomersAndCargosEvent;

import events.listener.ShowCustomersAndCargosEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.LogTyp;
import storageContract.administration.Customer;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

import java.util.Map;

public class ShowCustomersAndCargosEventListenerImpl implements ShowCustomersAndCargosEventListener {

    private Storage storage;
    private CustomerManagement management;


    public ShowCustomersAndCargosEventListenerImpl(Storage storage, CustomerManagement management){
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
    public void onShowCustomersAndCargosEvent(ShowCustomersAndCargosEvent event) {
        if(null != event.getCustomer()){
            Map<Customer, Integer> customerAndCargoMap = null;
            customerAndCargoMap = this.management.getCustomerAndNumberOfCargo();
            this.print(customerAndCargoMap.toString() + "\n");



            LogEvent logEvent = new LogEvent(this, LogTyp.SHOW_CUSTOMER_CARGO);
            logEventHandler.handle2(logEvent);
        }
    }


}
