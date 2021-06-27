package eventTest;

import events.listener.PrintEventListener;
import events.listener.ShowSpecificCargoTypEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEvent;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventHandler;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventListenerImpl;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.LiquidBulkCargoImpl;
import storageContract.cargo.MixedCargoLiquidBulkAndUnitisedImpl;
import storageContract.cargo.UnitisedCargoImpl;
import view.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShowSpecificCargoTypEventTest {


    @Test
    public void listenerGetsAnNotification(){
        ShowSpecificCargoTypEvent event = mock(ShowSpecificCargoTypEvent.class);
        ShowSpecificCargoTypEventHandler handler = new ShowSpecificCargoTypEventHandler();
        ShowSpecificCargoTypEventListener listener = mock(ShowSpecificCargoTypEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onShowSpecificCargoTypEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEventListener listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        ShowSpecificCargoTypEvent event = mock(ShowSpecificCargoTypEvent.class);
        listener.onShowSpecificCargoTypEvent(event);
        verify(event, atLeastOnce()).getCargoTyp();
        verify(event, never()).getSource();
    }

    @Test
    public void validShowCargoEventTest(){
        String cargoTyp = "Unitised";
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,cargoTyp);
        ShowSpecificCargoTypEventHandler handler = mock(ShowSpecificCargoTypEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(cargoTyp,event.getCargoTyp());
    }

    @Test
    public void removeListenerTest(){
        ShowSpecificCargoTypEvent event = mock(ShowSpecificCargoTypEvent.class);
        ShowSpecificCargoTypEventHandler handler = new ShowSpecificCargoTypEventHandler();
        ShowSpecificCargoTypEventListener listener = mock(ShowSpecificCargoTypEventListenerImpl.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onShowSpecificCargoTypTest1(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"Unitised");
        ShowSpecificCargoTypEventHandler handler = new ShowSpecificCargoTypEventHandler();
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.onShowSpecificCargoTypEvent(event);
        verify(storage, atLeastOnce()).getAllCargo();
    }

    @Test
    public void onShowSpecificCargoTypTest2(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"LiquidBulk");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.onShowSpecificCargoTypEvent(event);
        verify(storage, atLeastOnce()).getAllCargo();
    }

    @Test
    public void onShowSpecificCargoTypTest3(){
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"MixedLiquidBulkAndUnitised");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.onShowSpecificCargoTypEvent(event);
        verify(storage, atLeastOnce()).getAllCargo();
    }

    @Test
    public void onShowSpecificCargoTypEventPrintEventHandle(){
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"WrongCargo");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);

        listener.onShowSpecificCargoTypEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }

    @Test
    public void onShowSpecificCargoTypEventPrintEvent(){
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = mock(StorageImpl.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"WrongCargo");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowSpecificCargoTypEvent(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Check your input, unknown cargo typ\nAllowed cargo typ: Unitised, LiquidBulk, MixedLiquidBulkAndUnitised";
        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowSpecificCargoTypEventPrintEvent2() throws CustomerDoesNotExistException, StorageIsFullException, StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        Customer customer = new CustomerImpl("Erik");
        UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer,null,null,null,null,true);
        unitisedCargo.setStorageDate(null);
        unitisedCargo.setHasStorageDateBeenSet(true);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,unitisedCargo,storage);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"Unitised");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowSpecificCargoTypEvent(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik\n" +
                "Position: 0\n" +
                "Cargo Typ: UnitisedCargo\n" +
                "Date of storage:null\n" +
                "Last Inspection:null\n" +
                "Fragile: true\n" +
                "Value: null\n" +
                "Duration: null\n";

        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowSpecificCargoTypEventPrintEvent3() throws CustomerDoesNotExistException, StorageIsFullException, StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        Customer customer = new CustomerImpl("Erik");
        LiquidBulkCargoImpl liquidBulkCargo = new LiquidBulkCargoImpl(customer,null,null,null,null,true);
        liquidBulkCargo.setStorageDate(null);
        liquidBulkCargo.setHasStorageDateBeenSet(true);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,liquidBulkCargo,storage);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"LiquidBulk");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowSpecificCargoTypEvent(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik\n" +
                "Position: 0\n" +
                "Cargo Typ: LiquidBulkCargo\n" +
                "Date of storage:null\n" +
                "Last Inspection:null\n" +
                "Pressurized: true\n" +
                "Value: null\n" +
                "Duration: null\n";

        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowSpecificCargoTypEventPrintEvent4() throws CustomerDoesNotExistException, StorageIsFullException, StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        Customer customer = new CustomerImpl("Erik");
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargoLiquidBulkAndUnitised = new MixedCargoLiquidBulkAndUnitisedImpl(customer,null,null,null,null,true,true);
        mixedCargoLiquidBulkAndUnitised.setStorageDate(null);
        mixedCargoLiquidBulkAndUnitised.setHasStorageDateBeenSet(true);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,mixedCargoLiquidBulkAndUnitised,storage);
        ShowSpecificCargoTypEvent event = new ShowSpecificCargoTypEvent(this,"MixedLiquidBulkAndUnitised");
        ShowSpecificCargoTypEventListenerImpl listener = new ShowSpecificCargoTypEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowSpecificCargoTypEvent(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik\n" +
                "Position: 0\n" +
                "Cargo Typ: MixedLiquidBulkAndUnitised\n" +
                "Date of storage:null\n" +
                "Last Inspection:null\n" +
                "Fragile: true\n" +
                "Pressurized: true\n" +
                "Value: null\n" +
                "Duration: null\n";
        assertEquals(expected, captor.getValue().getMessage());
    }

}
