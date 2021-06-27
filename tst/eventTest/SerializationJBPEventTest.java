package eventTest;

import events.listener.SerializationJBPEventListener;
import events.logEvent.LogEventHandler;
import events.serializationJBPEvent.SerializationJBPEvent;
import events.serializationJBPEvent.SerializationJBPEventHandler;
import events.serializationJBPEvent.SerializationJBPEventListenerImpl;

import log.Logger;
import org.junit.jupiter.api.Test;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

import static org.mockito.Mockito.*;

public class SerializationJBPEventTest {

    @Test
    public void listenerGetsAnNotification(){
        SerializationJBPEvent event = mock(SerializationJBPEvent.class);
        SerializationJBPEventHandler handler = new SerializationJBPEventHandler();
        SerializationJBPEventListener listener = mock(SerializationJBPEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onSerializationJBPEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        SerializationJBPEventListenerImpl listener = new SerializationJBPEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        SerializationJBPEvent event = mock(SerializationJBPEvent.class);
        listener.onSerializationJBPEvent(event);
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        SerializationJBPEvent event = new SerializationJBPEvent(this);
        SerializationJBPEventHandler handler = mock(SerializationJBPEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
    }

    @Test
    public void removeListenerTest(){
        SerializationJBPEvent event = mock(SerializationJBPEvent.class);
        SerializationJBPEventHandler handler = new SerializationJBPEventHandler();
        SerializationJBPEventListener listener = mock(SerializationJBPEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }
}
