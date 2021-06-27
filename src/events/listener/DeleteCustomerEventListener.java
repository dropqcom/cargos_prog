package events.listener;

import events.deleteCustomerEvent.DeleteCustomerEvent;

public interface DeleteCustomerEventListener extends java.util.EventListener {

    void onDeleteCustomerEvent(DeleteCustomerEvent event);

}
