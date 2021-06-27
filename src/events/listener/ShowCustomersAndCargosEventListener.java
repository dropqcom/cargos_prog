package events.listener;


import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEvent;

public interface ShowCustomersAndCargosEventListener extends java.util.EventListener {

    void onShowCustomersAndCargosEvent(ShowCustomersAndCargosEvent event);

}
