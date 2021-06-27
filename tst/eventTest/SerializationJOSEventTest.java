package eventTest;

import events.listener.SerializationJOSEventListener;
import events.logEvent.LogEventHandler;
import events.serializationJOSEvent.SerializationJOSEvent;
import events.serializationJOSEvent.SerializationJOSEventHandler;
import events.serializationJOSEvent.SerializationJOSEventListenerImpl;
import log.Logger;
import org.junit.jupiter.api.Test;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SerializationJOSEventTest {

    @Test
    public void listenerGetsAnNotification(){
        SerializationJOSEvent event = mock(SerializationJOSEvent.class);
        SerializationJOSEventHandler handler = new SerializationJOSEventHandler();
        SerializationJOSEventListener listener = mock(SerializationJOSEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onSerializationJOSEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        SerializationJOSEventListenerImpl listener = new SerializationJOSEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        SerializationJOSEvent event = mock(SerializationJOSEvent.class);
        listener.onSerializationJOSEvent(event);
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        SerializationJOSEvent event = new SerializationJOSEvent(this);
        SerializationJOSEventHandler handler = mock(SerializationJOSEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
    }

    @Test
    public void removeListenerTest(){
        SerializationJOSEvent event = mock(SerializationJOSEvent.class);
        SerializationJOSEventHandler handler = new SerializationJOSEventHandler();
        SerializationJOSEventListener listener = mock(SerializationJOSEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

}
