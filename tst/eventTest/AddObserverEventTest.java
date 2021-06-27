package eventTest;

import events.addObserverEvent.AddObserverEvent;
import events.addObserverEvent.AddObserverEventHandler;
import events.addObserverEvent.AddObserverEventListenerImpl;
import events.listener.AddObserverEventListener;
import events.logEvent.LogEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.CustomerManagement;
import storageContract.administration.StorageImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddObserverEventTest {


    @Test
    public void listenerGetsAnNotification(){
        AddObserverEvent event = mock(AddObserverEvent.class);
        AddObserverEventHandler handler = new AddObserverEventHandler();
        AddObserverEventListener listener = mock(AddObserverEventListener.class);

        handler.add(listener);
        handler.handle(event);

        verify(listener, times(1)).onAddObserverEvent(event);
    }

    @Test
    public void properNotificationReaction(){
        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        AddObserverEventListener listener = new AddObserverEventListenerImpl(storage,management);
        AddObserverEvent event = mock(AddObserverEvent.class);
        listener.onAddObserverEvent(event);
        verify(event, atLeastOnce()).getClass_name();
        verify(event, never()).getSource();
    }

    @Test
    public void validAddObserverEvent(){
        String class_name = "LiquidBulkCargo";
        AddObserverEvent event = new AddObserverEvent(this,class_name);
        AddObserverEventHandler handler = mock(AddObserverEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(class_name,event.getClass_name());
    }

    @Test
    public void removeListenerTest(){
        AddObserverEvent event = mock(AddObserverEvent.class);
        AddObserverEventHandler handler = new AddObserverEventHandler();
        AddObserverEventListener listener = mock(AddObserverEventListener.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onAddObserverTest() throws StorageToSmallException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        StorageImpl storage = new StorageImpl(3);
        CustomerManagement management = mock(CustomerManagement.class);
        AddObserverEvent event = new AddObserverEvent(this,"test");
        AddObserverEventHandler handler = new AddObserverEventHandler();
        AddObserverEventListenerImpl listener = new AddObserverEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        handler.add(listener);
        handler.handle(event);
        assertEquals(1,storage.getObserverList().size());
        storage.clear();

    }

}
