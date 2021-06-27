package events.listener;


import events.addCustomerEvent.AddCustomerEvent;

public interface AddCustomerEventListener extends java.util.EventListener {

    void onAddCustomerEvent(AddCustomerEvent event);

}
