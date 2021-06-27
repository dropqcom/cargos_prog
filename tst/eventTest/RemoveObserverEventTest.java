package eventTest;

import events.listener.RemoveObserverEventListener;

import events.logEvent.LogEventHandler;
import events.removeObserverEvent.RemoveObserverEvent;
import events.removeObserverEvent.RemoveObserverEventHandler;
import events.removeObserverEvent.RemoveObserverEventListenerImpl;

import log.Logger;
import observer.ObserverSizeImpl;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.CustomerManagement;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.Storage;
import storageContract.administration.StorageImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class RemoveObserverEventTest {

    @Test
    public void listenerGetsAnNotification(){
        RemoveObserverEvent event = mock(RemoveObserverEvent.class);
        RemoveObserverEventHandler handler = new RemoveObserverEventHandler();
        RemoveObserverEventListener listener = mock(RemoveObserverEventListener.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onRemoveObserverEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        RemoveObserverEventListener listener = new RemoveObserverEventListenerImpl(storage,management);
        RemoveObserverEvent event = mock(RemoveObserverEvent.class);
        listener.onRemoveObserverEvent(event);
        verify(event, atLeastOnce()).getClass_name();
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        String class_name = "UnitisedCargo";
        RemoveObserverEvent event = new RemoveObserverEvent(this,class_name);
        RemoveObserverEventHandler handler = mock(RemoveObserverEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(class_name,event.getClass_name());
    }

    @Test
    public void removeListenerTest(){
        RemoveObserverEvent event = mock(RemoveObserverEvent.class);
        RemoveObserverEventHandler handler = new RemoveObserverEventHandler();
        RemoveObserverEventListener listener = mock(RemoveObserverEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void removeObserverTest() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        ObserverSizeImpl observerSize = new ObserverSizeImpl(storage);
        storage.signIn(observerSize);
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        RemoveObserverEvent event = new RemoveObserverEvent(this,"ObserverSize");
        RemoveObserverEventListenerImpl listener = new RemoveObserverEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.onRemoveObserverEvent(event);


        int observerCount = storage.getObserverList().size();
        assertEquals(0,observerCount);
    }
}
