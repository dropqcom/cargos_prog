package eventTest;

import events.deleteCargoAtPosition.DeleteCargoAtPositionEvent;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventHandler;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventListenerImpl;
import events.listener.PrintEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;

import storageContract.cargo.Hazard;
import storageContract.cargo.UnitisedCargoImpl;
import view.View;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class DeleteCargoAtPositionEventTest {

    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private boolean fragile = false;




    @Test
    public void onDeleteCargoAtPositionEventTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        StorageImpl storage = new StorageImpl(3);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer,bd,d,hazards,date,false);
        management.addCustomer(customer);
        management.addCargo(customer,cargo,storage);
        DeleteCargoAtPositionEvent event = new DeleteCargoAtPositionEvent(this,"0");
        DeleteCargoAtPositionEventHandler handler = new DeleteCargoAtPositionEventHandler();
        DeleteCargoAtPositionEventListenerImpl listener = new DeleteCargoAtPositionEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        handler.add(listener);
        handler.handle(event);
        assertEquals(0,storage.getHowManyCargo());
    }

    @Test
    public void validDeleteCargoEventTest(){
        String pos = "0";
        DeleteCargoAtPositionEvent event = new DeleteCargoAtPositionEvent(this,pos);
        DeleteCargoAtPositionEventHandler handler = mock(DeleteCargoAtPositionEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(pos,event.getPosition());
    }

    @Test
    public void removeListenerTest(){
        DeleteCargoAtPositionEvent event = mock(DeleteCargoAtPositionEvent.class);
        DeleteCargoAtPositionEventHandler handler = new DeleteCargoAtPositionEventHandler();
        DeleteCargoAtPositionEventListenerImpl listener = mock(DeleteCargoAtPositionEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }


    @Test
    public void onDeleteCargoAtPositionErrorMessageTest() throws StorageToSmallException { ;
        String pos = "2";
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(3);
        CustomerManagement management = new CustomerManagementImpl();

        DeleteCargoAtPositionEvent event = mock(DeleteCargoAtPositionEvent.class);
        when(event.getPosition()).thenReturn(pos);
        DeleteCargoAtPositionEventListenerImpl listener = new DeleteCargoAtPositionEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onDeleteCargoAtPositionEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "No cargo at " + pos;
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void onDeleteCargoAtPositionErrorMessageTest2() throws StorageToSmallException {
        String pos = "-4";

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        DeleteCargoAtPositionEvent event = new DeleteCargoAtPositionEvent(this,pos);
        DeleteCargoAtPositionEventListenerImpl listener = new DeleteCargoAtPositionEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onDeleteCargoAtPositionEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Index out of bounds, check your input";
        assertEquals(expected,captor.getValue().getMessage());
    }


}
