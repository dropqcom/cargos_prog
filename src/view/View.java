package view;

import events.listener.PrintEventListener;
import events.printEvent.PrintEvent;

public class View implements PrintEventListener {


    @Override
    public void onPrintEvent(PrintEvent event) {
        String message = event.getMessage();
        System.out.println(message);
    }
}
