package eventTest;

import events.addCustomerEvent.AddCustomerEvent;
import events.addCustomerEvent.AddCustomerEventHandler;
import events.addCustomerEvent.AddCustomerEventListenerImpl;
import events.listener.PrintEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import view.View;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class AddCustomerEventTest {



    //only one customer added because he already exists
    @Test
    public void onAddCustomerEventTest() throws StorageToSmallException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        StorageImpl storage = new StorageImpl(3);
        CustomerManagement management = new CustomerManagementImpl();
        AddCustomerEvent event1 = new AddCustomerEvent(this,"A");
        AddCustomerEventHandler handler = new AddCustomerEventHandler();
        AddCustomerEventListenerImpl listener = new AddCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        handler.add(listener);
        handler.handle(event1);
        handler.handle(event1);
        assertEquals(1,management.getNumberOfCustomer());
    }

    @Test
    public void validAddCustomerEvent(){
        String customer = "A";
        AddCustomerEvent event = new AddCustomerEvent(this,customer);
        AddCustomerEventHandler handler = mock(AddCustomerEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(customer,event.getCustomerName());
    }

    @Test
    public void removeListenerTest(){
        AddCustomerEvent event = mock(AddCustomerEvent.class);
        AddCustomerEventHandler handler = new AddCustomerEventHandler();
        AddCustomerEventListenerImpl listener = mock(AddCustomerEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onAddCustomerEventPrintEventHandleTest() throws StorageToSmallException {
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

        AddCustomerEvent event = new AddCustomerEvent(this,"Erik");
        AddCustomerEventListenerImpl listener = new AddCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCustomerEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onAddCustomerEventPrintTest() throws StorageToSmallException {
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

        AddCustomerEvent event = new AddCustomerEvent(this,"Erik");
        AddCustomerEventListenerImpl listener = new AddCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCustomerEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "[!] Customer already exists";
        assertEquals(expected, captor.getValue().getMessage());
    }

}
