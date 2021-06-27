package events.deleteCustomerEvent;

import events.listener.DeleteCustomerEventListener;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.administration.Customer;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

public class DeleteCustomerEventListenerImpl implements DeleteCustomerEventListener {

    private Storage storage;
    private CustomerManagement management;

    public DeleteCustomerEventListenerImpl(Storage storage, CustomerManagement management) {
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
    public void onDeleteCustomerEvent(DeleteCustomerEvent event) {
        if(null != event.getCustomerName()){
            try {
                if(null != management.getCustomer(event.getCustomerName())){
                    Customer customer = management.getCustomer(event.getCustomerName());
                    management.delete(customer,storage);
                }
            } catch (CustomerDoesNotExistException e) {
                this.print("Customer does not exist");
                return;
            }
            this.print("[INFO] " + event.getCustomerName() + " has been deleted");
            String message = event.getCustomerName() + " has been deleted";
            LogEventUserInput logEventUserInput = new LogEventUserInput(this,message);
            logEventHandler.handle(logEventUserInput);
        }
    }
}
