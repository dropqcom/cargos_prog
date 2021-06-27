package eventTest;

import events.DeserializationJBPEvent.DeserializationJBPEvent;
import events.DeserializationJBPEvent.DeserializationJBPEventHandler;
import events.DeserializationJBPEvent.DeserializationJBPEventListenerImpl;
import events.listener.DeserializationJBPEventListener;

import events.logEvent.LogEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import storageContract.administration.*;

import static org.mockito.Mockito.*;

public class DeserializationJBPEventTest {


    @Test
    public void listenerGetsAnNotification(){
        DeserializationJBPEvent event = mock(DeserializationJBPEvent.class);
        DeserializationJBPEventHandler handler = new DeserializationJBPEventHandler();
        DeserializationJBPEventListener listener = mock(DeserializationJBPEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onDeserializationJBPEvent(event);
    }

    @Test
    public void properNotificationReaction(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        DeserializationJBPEventListenerImpl listener = new DeserializationJBPEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        DeserializationJBPEvent event = mock(DeserializationJBPEvent.class);
        listener.onDeserializationJBPEvent(event);
        verify(event, never()).getSource();
    }

    @Test
    public void validSetDateTest(){
        DeserializationJBPEvent event = new DeserializationJBPEvent(this);
        DeserializationJBPEventHandler handler = mock(DeserializationJBPEventHandler.class);
        handler.handle(event);
        verify(handler, times(1)).handle(event);
    }

    @Test
    public void removeListenerTest(){
        DeserializationJBPEvent event = mock(DeserializationJBPEvent.class);
        DeserializationJBPEventHandler handler = new DeserializationJBPEventHandler();
        DeserializationJBPEventListener listener = mock(DeserializationJBPEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }




}
