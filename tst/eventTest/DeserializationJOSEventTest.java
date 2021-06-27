package eventTest;

import events.addCargoEvent.AddCargoEvent;
import events.addCargoEvent.AddCargoEventListenerImpl;
import events.deserializationJOSEvent.DeserializationJOSEvent;
import events.deserializationJOSEvent.DeserializationJOSEventHandler;
import events.deserializationJOSEvent.DeserializationJOSEventListenerImpl;
import events.listener.DeserializationJOSEventListener;
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
import storageContract.cargo.UnitisedCargo;
import storageContract.cargo.UnitisedCargoImpl;
import view.View;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeserializationJOSEventTest {

    @Test
    public void listenerGetsAnNotification(){
        DeserializationJOSEvent event = mock(DeserializationJOSEvent.class);
        DeserializationJOSEventHandler handler = new DeserializationJOSEventHandler();
        DeserializationJOSEventListener listener = mock(DeserializationJOSEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onDeserializationJOSEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        DeserializationJOSEventListenerImpl listener = new DeserializationJOSEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        DeserializationJOSEvent event = mock(DeserializationJOSEvent.class);
        listener.onDeserializationJOSEvent(event);
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        DeserializationJOSEvent event = new DeserializationJOSEvent(this);
        DeserializationJOSEventHandler handler = mock(DeserializationJOSEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
    }

    @Test
    public void removeListenerTest(){
        DeserializationJOSEvent event = mock(DeserializationJOSEvent.class);
        DeserializationJOSEventHandler handler = new DeserializationJOSEventHandler();
        DeserializationJOSEventListener listener = mock(DeserializationJOSEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void addCargoCallTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = mock(CustomerManagement.class);
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        Customer customer = new CustomerImpl("Erik");
        UnitisedCargoImpl unitisedCargo = mock(UnitisedCargoImpl.class);
        management.addCargo(customer,unitisedCargo,storage);

        DeserializationJOSEvent event = new DeserializationJOSEvent(this);
        DeserializationJOSEventHandler handler = new DeserializationJOSEventHandler();
        DeserializationJOSEventListenerImpl listener = new DeserializationJOSEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        handler.add(listener);
        handler.handle(event);

        verify(management,atLeastOnce()).addCargo(customer,unitisedCargo,storage);
    }






}
