package log;

import events.addCargoEvent.AddCargoEvent;
import events.addCargoEvent.AddCargoEventListenerImpl;
import events.listener.PrintEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEventHandler;
import log.log_ressources.LogStringsEnglish;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import view.View;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class LogTest {

    @Test
    public void constructorTest(){
        Logger logger = new Logger("test",false,null);
        assertNotNull(logger);
    }

    @Test
    public void isLogEnableTest(){
        Logger logger = new Logger("test",false,null);
        assertFalse(logger.isLog_enable());
    }


    @Test
    public void validLogEventUserInput(){
        LogEventUserInput logEventUserInput = new LogEventUserInput(this,"A");
        LogEventHandler handler = mock(LogEventHandler.class);
        handler.handle(logEventUserInput);

        verify(handler, times(1)).handle(logEventUserInput);
        assertEquals("A",logEventUserInput.getString());
    }

    @Test
    public void removeListenerTest(){
        LogEventUserInput logEventUserInput = mock(LogEventUserInput.class);
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        logEventHandler.removeListener(logger);
        logEventHandler.handle(logEventUserInput);
        verifyZeroInteractions(logEventUserInput);
    }

    @Test
    public void validLogEvent(){
        LogEvent logEvent = new LogEvent(this,LogTyp.GET_NON_HAZARD);
        LogEventHandler handler = mock(LogEventHandler.class);
        handler.handle2(logEvent);

        verify(handler, times(1)).handle2(logEvent);
        assertEquals(LogTyp.GET_NON_HAZARD,logEvent.getLogTyp());
    }


    @Test
    public void onLogLogTest(){
        LogEventHandler handler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        handler.addListener(logger);
        LogEvent event = new LogEvent(this,LogTyp.ADD_OBSERVER);
        handler.handle2(event);
        verify(logger,atLeastOnce()).onLogEvent(event);
    }


    @Test
    public void addCargoLogTest() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "UnitisedCargo Erik 45 43 flammable y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogStringsEnglish logStringsEnglish = new LogStringsEnglish();
        LogEventHandler logEventHandler = mock(LogEventHandler.class);

        String filename = "test.log";
        File file = mock(File.class);
        when(file.getName()).thenReturn(filename);

        Logger logger = new Logger(filename,true,logStringsEnglish);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);


        verify(logEventHandler,atLeastOnce()).handle(any());
    }



}
