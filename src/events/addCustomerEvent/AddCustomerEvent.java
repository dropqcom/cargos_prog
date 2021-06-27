package events.addCustomerEvent;

public class AddCustomerEvent extends java.util.EventObject {

    private String customerName;

    public AddCustomerEvent(Object source, String customerName) {
        super(source);
        this.customerName = customerName;
    }

    public String getCustomerName(){
        return this.customerName;
    }

}
