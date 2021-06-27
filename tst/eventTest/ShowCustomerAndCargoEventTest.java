package eventTest;

import events.listener.PrintEventListener;
import events.listener.ShowCustomersAndCargosEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEvent;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventHandler;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventListenerImpl;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import view.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShowCustomerAndCargoEventTest {

    @Test
    public void listenerGetsAnNotification(){
        ShowCustomersAndCargosEvent event = mock(ShowCustomersAndCargosEvent.class);
        ShowCustomersAndCargosEventHandler handler = new ShowCustomersAndCargosEventHandler();
        ShowCustomersAndCargosEventListener listener = mock(ShowCustomersAndCargosEventListener.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onShowCustomersAndCargosEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowCustomersAndCargosEventListener listener = new ShowCustomersAndCargosEventListenerImpl(storage,management);
        ShowCustomersAndCargosEvent event = mock(ShowCustomersAndCargosEvent.class);
        listener.onShowCustomersAndCargosEvent(event);
        verify(event, atLeastOnce()).getCustomer();
        verify(event, never()).getSource();
    }

    @Test
    public void validShowCustomerAndCargoEvent(){
        String input = "customer";
        ShowCustomersAndCargosEvent event = new ShowCustomersAndCargosEvent(this,input);
        ShowCustomersAndCargosEventHandler handler = mock(ShowCustomersAndCargosEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(input,event.getCustomer());
    }

    @Test
    public void removeListenerTest(){
        ShowCustomersAndCargosEvent event = mock(ShowCustomersAndCargosEvent.class);
        ShowCustomersAndCargosEventHandler handler = new ShowCustomersAndCargosEventHandler();
        ShowCustomersAndCargosEventListener listener = mock(ShowCustomersAndCargosEventListener.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onShowCustomerAndCargoEventPrintEventHandleTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        ShowCustomersAndCargosEvent event = new ShowCustomersAndCargosEvent(this,"customer");
        ShowCustomersAndCargosEventListenerImpl listener = new ShowCustomersAndCargosEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowCustomersAndCargosEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onShowCustomerAndCargoEventPrintEventTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        ShowCustomersAndCargosEvent event = new ShowCustomersAndCargosEvent(this,"customer");
        ShowCustomersAndCargosEventListenerImpl listener = new ShowCustomersAndCargosEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowCustomersAndCargosEvent(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "{Erik=0}\n";
        assertEquals(expected, captor.getValue().getMessage());
    }



}
