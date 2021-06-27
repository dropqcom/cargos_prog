package eventTest;

import events.listener.PrintEventListener;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import org.junit.jupiter.api.Test;
import view.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrintEventTest {

    @Test
    public void removeListenerTest(){
        PrintEvent event = mock(PrintEvent.class);
        PrintEventHandler handler = new PrintEventHandler();
        PrintEventListener listener = mock(View.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void validPrintEventTest(){
        String string = "Test";
        PrintEvent event = new PrintEvent(this,string);
        PrintEventHandler handler = mock(PrintEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(string,event.getMessage());
    }

    @Test
    public void properNotificationReaction(){
        PrintEventListener listener = new View();
        PrintEvent event = mock(PrintEvent.class);
        listener.onPrintEvent(event);
        verify(event, atLeastOnce()).getMessage();
        verify(event, never()).getSource();
    }

    @Test
    public void listenerGetsAnNotification(){
        PrintEvent event = mock(PrintEvent.class);
        PrintEventHandler handler = new PrintEventHandler();
        PrintEventListener listener = mock(View.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onPrintEvent(event);
    }

}
