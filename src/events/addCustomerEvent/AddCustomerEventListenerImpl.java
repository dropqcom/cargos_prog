package events.addCustomerEvent;


import events.listener.AddCustomerEventListener;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.administration.*;

public class AddCustomerEventListenerImpl implements AddCustomerEventListener {

    private Storage storage;
    private CustomerManagement management;

    public AddCustomerEventListenerImpl(Storage storage, CustomerManagement management){
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
    public void onAddCustomerEvent(AddCustomerEvent event){
        if(null != event.getCustomerName()){
            String customerName = event.getCustomerName();
            Customer customer = new CustomerImpl(customerName);

            int before = management.getNumberOfCustomer();
            this.management.addCustomer(customer);
            int after = management.getNumberOfCustomer();
            if(after > before){
                this.print("[INFO] Customer " + customerName + " has been added");
                String message = "Customer " + customerName + " has been added";
                LogEventUserInput logEventUserInput = new LogEventUserInput(this,message);
                logEventHandler.handle(logEventUserInput);
            }else{
                this.print("[!] Customer already exists");
            }

        }
    }



}
