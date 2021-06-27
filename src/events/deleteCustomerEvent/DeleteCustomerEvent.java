package events.deleteCustomerEvent;

public class DeleteCustomerEvent extends java.util.EventObject {

    private String customerName;

    public DeleteCustomerEvent(Object source, String customerName) {
        super(source);
        this.customerName = customerName;
    }

    public String getCustomerName(){
        return this.customerName;
    }
}
