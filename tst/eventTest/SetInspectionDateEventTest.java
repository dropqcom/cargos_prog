package eventTest;

import events.listener.PrintEventListener;
import events.listener.SetInspectionDateEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import events.setInspectionDateEvent.SetInspectionDateEvent;
import events.setInspectionDateEvent.SetInspectionDateEventHandler;
import events.setInspectionDateEvent.SetInspectionDateEventListenerImpl;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.UnitisedCargoImpl;
import view.View;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SetInspectionDateEventTest {

    @Test
    public void listenerGetsAnNotification(){
        SetInspectionDateEvent event = mock(SetInspectionDateEvent.class);
        SetInspectionDateEventHandler handler = new SetInspectionDateEventHandler();
        SetInspectionDateEventListener listener = mock(SetInspectionDateEventListener.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onSetInspectionDateEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        SetInspectionDateEventListener listener = new SetInspectionDateEventListenerImpl(storage,management);
        SetInspectionDateEvent event = mock(SetInspectionDateEvent.class);
        listener.onSetInspectionDateEvent(event);
        verify(event, atLeastOnce()).getPos();
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        String pos = "0";
        SetInspectionDateEvent event = new SetInspectionDateEvent(this,pos);
        SetInspectionDateEventHandler handler = mock(SetInspectionDateEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(pos,event.getPos());
    }

    @Test
    public void removeListenerTest(){
        SetInspectionDateEvent event = mock(SetInspectionDateEvent.class);
        SetInspectionDateEventHandler handler = new SetInspectionDateEventHandler();
        SetInspectionDateEventListener listener = mock(SetInspectionDateEventListener.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onSetInspectionDateEventPrintEventHandleTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        SetInspectionDateEvent event = new SetInspectionDateEvent(this,"1");
        SetInspectionDateEventListenerImpl listener = new SetInspectionDateEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onSetInspectionDateEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onSetInspectionDateEventPrintTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();


        SetInspectionDateEvent event = new SetInspectionDateEvent(this,"-1");
        SetInspectionDateEventListenerImpl listener = new SetInspectionDateEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onSetInspectionDateEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Index out of bounds, check your input";
        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onSetInspectionDateTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        Date lastInspectionDate = new Date();
        UnitisedCargoImpl cargo = mock(UnitisedCargoImpl.class);
        when(cargo.getStoragePosition()).thenReturn(0);
        when(cargo.getOwner()).thenReturn(customer);
        management.addCustomer(customer);
        management.addCargo(customer,cargo,storage);


        SetInspectionDateEvent event = new SetInspectionDateEvent(this,"0");
        SetInspectionDateEventListenerImpl listener = new SetInspectionDateEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onSetInspectionDateEvent(event);

        verify(cargo, atLeastOnce()).setLastInspectionDate(any());
    }

}
