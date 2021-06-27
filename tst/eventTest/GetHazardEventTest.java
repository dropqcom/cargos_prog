package eventTest;

import events.getHazardEvent.GetHazardEvent;
import events.getHazardEvent.GetHazardEventHandler;
import events.getHazardEvent.GetHazardEventListenerImpl;
import events.listener.GetHazardEventListener;
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

public class GetHazardEventTest {

    @Test
    public void listenerGetsAnNotification(){
        GetHazardEvent event = mock(GetHazardEvent.class);
        GetHazardEventHandler handler = new GetHazardEventHandler();
        GetHazardEventListener listener = mock(GetHazardEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onGetHazardEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        GetHazardEventListener listener = new GetHazardEventListenerImpl(storage,management);
        GetHazardEvent event = mock(GetHazardEvent.class);
        listener.onGetHazardEvent(event);
        verify(event, atLeastOnce()).getHazardStr();
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        String hazard = "hazard";
        GetHazardEvent event = new GetHazardEvent(this,hazard);
        GetHazardEventHandler handler = mock(GetHazardEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(hazard,event.getHazardStr());
    }

    @Test
    public void removeListenerTest(){
        GetHazardEvent event = mock(GetHazardEvent.class);
        GetHazardEventHandler handler = new GetHazardEventHandler();
        GetHazardEventListener listener = mock(GetHazardEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onGetHazardEventPrintEventHandleTest() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        GetHazardEvent event = new GetHazardEvent(this,"hazard");
        GetHazardEventListenerImpl listener = new GetHazardEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onGetHazardEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onGetHazardEventPrintEventHandleTest2() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
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

        GetHazardEvent event = new GetHazardEvent(this,"hazard");
        GetHazardEventListenerImpl listener = new GetHazardEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onGetHazardEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "[explosive]";
        assertEquals(expected, captor.getValue().getMessage());
    }


}
