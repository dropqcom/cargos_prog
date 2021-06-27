package eventTest;

import events.addCargoEvent.AddCargoEventHandler;
import events.addListenerEvent.AddListenerEvent;
import events.addListenerEvent.AddListenerEventHandler;
import events.addListenerEvent.AddListenerEventListenerImpl;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.CustomerManagement;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.Storage;
import storageContract.administration.StorageImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class AddListenerEventTest {
    @Test
    public void onAddListenerEventTest() {
        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();
        AddListenerEvent event  = new AddListenerEvent(this,addCargoEventHandler);
        AddListenerEventHandler handler = new AddListenerEventHandler();
        AddListenerEventListenerImpl listener = mock(AddListenerEventListenerImpl.class);
        handler.add(listener);
        handler.handle(event);
        verify(listener, atLeastOnce()).onAddListenerEvent(event);
    }


    @Test
    public void validAddListenerEvent(){
        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();
        AddListenerEvent event = new AddListenerEvent(this,addCargoEventHandler);
        AddListenerEventHandler handler = mock(AddListenerEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(addCargoEventHandler,event.getAddCargoEventHandler());
    }

    @Test
    public void noInteractionWithEventTest(){
        AddListenerEvent event = mock(AddListenerEvent.class);
        AddListenerEventHandler handler = new AddListenerEventHandler();
        AddListenerEventListenerImpl listener = mock(AddListenerEventListenerImpl.class);
        handler.add(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void listenerRemoveTest(){
        AddListenerEvent event = mock(AddListenerEvent.class);
        AddListenerEventHandler handler = new AddListenerEventHandler();
        AddListenerEventListenerImpl listener = mock(AddListenerEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verify(listener, never()).onAddListenerEvent(event);
    }

    @Test
    public void constructorTest(){
        Storage storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        AddListenerEventListenerImpl addListenerEventListener = new AddListenerEventListenerImpl(storage,management);
        assertNotNull(addListenerEventListener);
    }

    @Test
    public void onAddListenerEventTest2(){
        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();
        AddListenerEvent event = new AddListenerEvent(this,addCargoEventHandler);
        AddListenerEventHandler handler = mock(AddListenerEventHandler.class);
        AddListenerEventListenerImpl listener = mock(AddListenerEventListenerImpl.class);
        handler.add(listener);
        listener.onAddListenerEvent(event);
        assertNotNull(event.getAddCargoEventHandler());
    }

    @Test
    public void onAddListenerEventLogEventHandlerTest() throws StorageToSmallException {
        LogEventHandler logEventHandler = mock(LogEventHandler.class);
        Logger logger = mock(Logger.class);
        when(logger.isLog_enable()).thenReturn(true);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();

        AddCargoEventHandler addCargoEventHandler = mock(AddCargoEventHandler.class);

        AddListenerEvent event = new AddListenerEvent(this,addCargoEventHandler);
        AddListenerEventListenerImpl listener = new AddListenerEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.onAddListenerEvent(event);

        verify(logEventHandler,atLeastOnce()).handle2(any());
    }





}
