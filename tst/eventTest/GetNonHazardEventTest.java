package eventTest;


import events.getNonHazardEvent.GetNonHazardEvent;
import events.getNonHazardEvent.GetNonHazardEventHandler;
import events.getNonHazardEvent.GetNonHazardEventListenerImpl;

import events.listener.GetNonHazardListener;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetNonHazardEventTest {


    @Test
    public void listenerGetsAnNotification(){
        GetNonHazardEvent event = mock(GetNonHazardEvent.class);
        GetNonHazardEventHandler handler = new GetNonHazardEventHandler();
        GetNonHazardEventListenerImpl listener = mock(GetNonHazardEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onGetNonHazardEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        GetNonHazardListener listener = new GetNonHazardEventListenerImpl(storage,management);
        GetNonHazardEvent event = mock(GetNonHazardEvent.class);
        listener.onGetNonHazardEvent(event);
        verify(event, atLeastOnce()).getNonHazardStr();
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        String hazard = "hazard";
        GetNonHazardEvent event = new GetNonHazardEvent(this,hazard);
        GetNonHazardEventHandler handler = mock(GetNonHazardEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(hazard,event.getNonHazardStr());
    }

    @Test
    public void removeListenerTest(){
        GetNonHazardEvent event = mock(GetNonHazardEvent.class);
        GetNonHazardEventHandler handler = new GetNonHazardEventHandler();
        GetNonHazardListener listener = mock(GetNonHazardListener.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }


    @Test
    public void onGetNonHazardEventPrintEventHandleTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        GetNonHazardEvent event = new GetNonHazardEvent(this,"hazard");
        GetNonHazardEventListenerImpl listener = new GetNonHazardEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onGetNonHazardEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onGetNonHazardEventPrintEventHandleTest2() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        ArrayList<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.explosive);
        Customer customer = new CustomerImpl("Erik");
        UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer,null,null,hazards,null,false);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,unitisedCargo,storage);

        GetNonHazardEvent event = new GetNonHazardEvent(this,"hazard");
        GetNonHazardEventListenerImpl listener = new GetNonHazardEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onGetNonHazardEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "[flammable, toxic, radioactive]";
        assertEquals(expected, captor.getValue().getMessage());
    }



}
