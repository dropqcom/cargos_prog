package eventTest;

import events.listener.PrintEventListener;
import events.listener.ShowAllCargoEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import events.showAllCargoEvent.ShowAllCargoEvent;
import events.showAllCargoEvent.ShowAllCargoEventHandler;
import events.showAllCargoEvent.ShowAllCargoEventListenerImpl;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShowAllCargoEventTest {

    @Test
    public void listenerGetsAnNotification(){
        ShowAllCargoEvent event = mock(ShowAllCargoEvent.class);
        ShowAllCargoEventHandler handler = new ShowAllCargoEventHandler();
        ShowAllCargoEventListener listener = mock(ShowAllCargoEventListener.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onShowAllCargoEvent(event);
    }


    @Test
    public void properNotificationReaction(){
        Storage storage = mock(Storage.class);
        CustomerManagement management = mock(CustomerManagement.class);
        ShowAllCargoEventListener listener = new ShowAllCargoEventListenerImpl(storage,management);
        ShowAllCargoEvent event = mock(ShowAllCargoEvent.class);
        listener.onShowAllCargoEvent(event);
        verify(event, atLeastOnce()).getCargoStr();
        verify(event, never()).getSource();
    }

    @Test
    public void validShowCargoEventTest(){
        String cargoStr = "cargo";
        ShowAllCargoEvent event = new ShowAllCargoEvent(this,cargoStr);
        ShowAllCargoEventHandler handler = mock(ShowAllCargoEventHandler.class);
        handler.handle(event);

        verify(handler, times(1)).handle(event);
        assertEquals(cargoStr,event.getCargoStr());
    }

    @Test
    public void removeListenerTest(){
        ShowAllCargoEvent event = mock(ShowAllCargoEvent.class);
        ShowAllCargoEventHandler handler = new ShowAllCargoEventHandler();
        ShowAllCargoEventListener listener = mock(ShowAllCargoEventListener.class);
        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);
        verifyZeroInteractions(event);
    }

    @Test
    public void onShowAllCargoEventPrintEventHandler() throws StorageToSmallException {
        String command = "UnitCargo Erik 43 , n";

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        ShowAllCargoEvent event = new ShowAllCargoEvent(this,"cargo");
        ShowAllCargoEventListenerImpl listener = new ShowAllCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowAllCargoEvent(event);

        verify(printEventHandler, atLeastOnce()).handle(any());
    }


    @Test
    public void errorMessageOutputTest_2() throws StorageToSmallException {
        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        ShowAllCargoEvent event = new ShowAllCargoEvent(this,"cargo");
        ShowAllCargoEventListenerImpl listener = new ShowAllCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowAllCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "No cargo currently stored";
        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowAllCargoEventCorrectTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {

        Customer customer = new CustomerImpl("Erik");
        BigDecimal bigDecimal = BigDecimal.valueOf(12.2);
        UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer,bigDecimal,null,null,null,true);
        unitisedCargo.setStorageDate(null);
        unitisedCargo.setHasStorageDateBeenSet(true);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,unitisedCargo,storage);

        ShowAllCargoEvent event = new ShowAllCargoEvent(this,"cargo");
        ShowAllCargoEventListenerImpl listener = new ShowAllCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowAllCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik" + "\nPosition: 0"+"\nCargo Typ: UnitisedCargo\nDate of storage:null"+"\nLast Inspection:null"+"\nFragile: true"+"\nValue: "+bigDecimal+"\nDuration: null"+"\n";
        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowAllCargoEventCorrectTest2() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {

        Customer customer = new CustomerImpl("Erik");
        BigDecimal bigDecimal = BigDecimal.valueOf(12.2);
        LiquidBulkCargoImpl liquidBulkCargo = new LiquidBulkCargoImpl(customer,bigDecimal,null,null,null,true);
        liquidBulkCargo.setStorageDate(null);
        liquidBulkCargo.setHasStorageDateBeenSet(true);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,liquidBulkCargo,storage);

        ShowAllCargoEvent event = new ShowAllCargoEvent(this,"cargo");
        ShowAllCargoEventListenerImpl listener = new ShowAllCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowAllCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik" + "\nPosition: 0"+"\nCargo Typ: LiquidBulkCargo\nDate of storage:null"+"\nLast Inspection:null"+"\nPressurized: true"+"\nValue: "+bigDecimal+"\nDuration: null"+"\n";
        assertEquals(expected, captor.getValue().getMessage());
    }

    @Test
    public void onShowAllCargoEventCorrectTest3() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {

        Customer customer = new CustomerImpl("Erik");
        BigDecimal bigDecimal = BigDecimal.valueOf(12.2);
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargoLiquidBulkAndUnitised = new MixedCargoLiquidBulkAndUnitisedImpl(customer,bigDecimal,null,null,null,true,true);
        mixedCargoLiquidBulkAndUnitised.setStorageDate(null);
        mixedCargoLiquidBulkAndUnitised.setHasStorageDateBeenSet(true);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCustomer(customer);
        management.addCargo(customer,mixedCargoLiquidBulkAndUnitised,storage);

        ShowAllCargoEvent event = new ShowAllCargoEvent(this,"cargo");
        ShowAllCargoEventListenerImpl listener = new ShowAllCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onShowAllCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer: Erik" + "\nPosition: 0"+"\nCargo Typ: MixedLiquidBulkAndUnitised\nDate of storage:null"+"\nLast Inspection:null"+"\nFragile: true"+"\nPressurized: true"+"\nValue: "+bigDecimal+"\nDuration: null"+"\n";
        assertEquals(expected, captor.getValue().getMessage());
    }


}
