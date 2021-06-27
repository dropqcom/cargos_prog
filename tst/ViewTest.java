import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class ViewTest {


    @Test
    public void viewTest(){
        View view = new View();
        PrintEvent event = mock(PrintEvent.class);
        view.onPrintEvent(event);
        verify(event, atLeastOnce()).getMessage();
        verify(event, never()).getSource();
    }

    @Test
    public void correctMessageDisplayedTest(){
        String message = "Hello Test";
        PrintStream originalOut = System.out;
        try{
            PrintStream out = mock(PrintStream.class);
            System.setOut(out);
            PrintEvent event = mock(PrintEvent.class);
            when(event.getMessage()).thenReturn(message);
            View view = new View();
            view.onPrintEvent(event);

            verify(out).println("Hello Test");
        }finally{
            System.setOut(originalOut);
        }
    }
}
