package eventTest;

import events.deleteCustomerEvent.DeleteCustomerEvent;
import events.deleteCustomerEvent.DeleteCustomerEventHandler;
import events.deleteCustomerEvent.DeleteCustomerEventListenerImpl;
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


public class DeleteCustomerEventTest {

    private Storage storage;
    private CustomerManagement management;

    @Test
    public void onDeleteCustomerEventTest() throws StorageToSmallException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        StorageImpl storage = new StorageImpl(3);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("A");
        management.addCustomer(customer);
        DeleteCustomerEvent event = new DeleteCustomerEvent(this,"A");
        DeleteCustomerEventHandler handler = new DeleteCustomerEventHandler();
        DeleteCustomerEventListenerImpl listener = new DeleteCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        handler.add(listener);
        handler.handle(event);
        assertEquals(0,management.getNumberOfCustomer());
    }

    @Test
    public void listenerRemoveTest(){
        DeleteCustomerEvent event = mock(DeleteCustomerEvent.class);
        DeleteCustomerEventHandler handler = new DeleteCustomerEventHandler();
        DeleteCustomerEventListenerImpl listener = mock(DeleteCustomerEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verify(listener, never()).onDeleteCustomerEvent(event);
    }


    @Test
    public void validDeleteCustomerEvent(){
        String customer = "A";
        DeleteCustomerEvent event = new DeleteCustomerEvent(this,customer);
        DeleteCustomerEventHandler handler = mock(DeleteCustomerEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(customer,event.getCustomerName());
    }

    @Test
    public void removeListenerTest(){
        DeleteCustomerEvent event = mock(DeleteCustomerEvent.class);
        DeleteCustomerEventHandler handler = mock(DeleteCustomerEventHandler.class);
        DeleteCustomerEventListenerImpl listener = mock(DeleteCustomerEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onDeleteCustomerEventPrintEventTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        DeleteCustomerEvent event = new DeleteCustomerEvent(this,"Erik");
        DeleteCustomerEventListenerImpl listener = new DeleteCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onDeleteCustomerEvent(event);


        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onDeleteCustomerEventPrintEventTest2() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        DeleteCustomerEvent event = new DeleteCustomerEvent(this,"Erik");
        DeleteCustomerEventListenerImpl listener = new DeleteCustomerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onDeleteCustomerEvent(event);



        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer does not exist";
        assertEquals(expected, captor.getValue().getMessage());
    }





}
