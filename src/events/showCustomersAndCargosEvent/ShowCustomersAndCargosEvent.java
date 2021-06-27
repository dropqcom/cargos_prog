package events.showCustomersAndCargosEvent;


/**
 * kannst vielleicht AddCustomerEvent und diese vereinen
 */
public class ShowCustomersAndCargosEvent extends java.util.EventObject {

    private String customer;

    public ShowCustomersAndCargosEvent(Object source, String customer) {
        super(source);
        this.customer = customer;
    }

    public String getCustomer(){
        return this.customer;
    }
}
