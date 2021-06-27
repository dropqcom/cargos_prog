package eventTest;

import events.addCargoEvent.AddCargoEvent;
import events.addCargoEvent.AddCargoEventHandler;
import events.addCargoEvent.AddCargoEventListenerImpl;
import events.listener.PrintEventListener;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.UnitisedCargoImpl;
import view.View;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class AddCargoEventTest {

    private Storage storage;
    private CustomerManagement management;

    private PrintEventHandler printEventHandler;



    @Test
    public void validAddCargoEvent() {
        String command = "UnitisedCargo Erik 34.4 43 , y";
        String[] cargo = command.split(" ");
        AddCargoEvent event = new AddCargoEvent(this, cargo);
        AddCargoEventHandler addCargoEventHandler = mock(AddCargoEventHandler.class);
        addCargoEventHandler.handle(event);

        verify(addCargoEventHandler, times(1)).handle(event);
        assertEquals(cargo, event.getCargoStr());
    }


    @Test
    public void properNotificationReaction() {
        AddCargoEventListenerImpl addCargoEventListener = new AddCargoEventListenerImpl(storage, management);
        AddCargoEvent event = mock(AddCargoEvent.class);
        addCargoEventListener.onAddCargoEvent(event);
        verify(event, atLeastOnce()).getCargoStr();
        verify(event, never()).getSource();
    }

    @Test
    public void listenerGetsAnNotificationTest() {
        AddCargoEvent event = mock(AddCargoEvent.class);
        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = mock(AddCargoEventListenerImpl.class);

        handler.add(listener);
        handler.handle(event);
        verify(listener, times(1)).onAddCargoEvent(event);
    }

    @Test
    public void listenerRemovedTest() {
        AddCargoEvent event = mock(AddCargoEvent.class);
        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = mock(AddCargoEventListenerImpl.class);

        handler.add(listener);
        handler.remove(listener);
        handler.handle(event);

        verify(listener, never()).onAddCargoEvent(event);
    }

    @Test
    public void noInterActionWithEventTest() {
        AddCargoEvent event = mock(AddCargoEvent.class);
        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = mock(AddCargoEventListenerImpl.class);
        handler.add(listener);
        handler.handle(event);

        verifyZeroInteractions(event);
    }

    @Test
    public void getAddCargoListenerListTest() {
        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = mock(AddCargoEventListenerImpl.class);
        handler.add(listener);
        assertTrue(handler.getListenerList().contains(listener));
    }


    //misspelling cargoType
    @Test
    public void errorMessageOutputTest_1() throws StorageToSmallException {
        String command = "UnitCargo Erik 34.4 43 , n";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Unknown cargo typ\nAllowed: UnitisedCargo, LiquidBulkCargo, MixedLiquidBulkAndUnitised";
        assertEquals(expected,captor.getValue().getMessage());

    }

    // noch mehr misspelling tests....
    @Test
    public void errorMessageOutputTest_2() throws StorageToSmallException {
        String command = "UnitCargo Erik 43 , n";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Check your input, number of arguments seems wrong";
        assertEquals(expected,captor.getValue().getMessage());
    }


    @Test
    public void errorMessageOutputTest_3() throws StorageToSmallException {
        String command = "UnitCargo Erik error 43 , n";
        String[] cargo = command.split(" ");


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected =         "Unknown cargo typ\n" +
                "Allowed: UnitisedCargo, LiquidBulkCargo, MixedLiquidBulkAndUnitised";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void errorMessageOutputTest_4() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "UnitisedCargo Erik 45 43 waste n";
        String[] cargo = command.split(" ");


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);

        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "unknown hazard, allowed Hazard [flammable, explosive, toxic, radioactive]";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void errorMessageOutputTest_5() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "UnitisedCargo Erik 45 43 flammable 5";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "y or n allowed, string length > 1 detected";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void errorMessageOutputTest_6() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "LiquidBulkCargo Erik 45 43 flammable 55";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "y or n allowed, string length > 1 detected";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void errorMessageOutputTest_7() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "MixedLiquidBulkAndUnitised Erik 45 43 flammable 5 y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "y or n allowed, string length > 1 detected";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void errorMessageOutputTest_8() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "MixedLiquidBulkAndUnitised Erik 45 43 flammable y";
        String[] cargo = command.split(" ");


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "wrong argument number";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void checkIfCargoHasBeenAddedAfterEventTest1() throws StorageToSmallException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        StorageImpl storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        String command = "LiquidBulkCargo Erik 34.4 43 toxic,flammable,radioactive,explosive y";
        String[] cargo = command.split(" ");
        AddCargoEvent event = new AddCargoEvent(this, cargo);
        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);

        handler.add(listener);
        handler.handle(event);
        assertEquals(1, storage.getHowManyCargo());
    }

    @Test
    public void checkIfCargoHasBeenAddedAfterEventTest2() throws StorageToSmallException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);



        StorageImpl storage = new StorageImpl(6);
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer1 = new CustomerImpl("Sally");
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer1);

        String command = "UnitisedCargo Erik 34.34 43 toxic,flammable y";
        String[] cargo = command.split(" ");
        String command2 = "UnitisedCargo Sally 233 2 , n";
        String[] cargo2 = command2.split(" ");
        String command3 = "MixedLiquidBulkAndUnitised Sally 34.4 2 explosive y y";
        String[] cargo3 = command3.split(" ");

        AddCargoEvent event = new AddCargoEvent(this, cargo);
        AddCargoEvent event2 = new AddCargoEvent(this, cargo2);
        AddCargoEvent event3 = new AddCargoEvent(this, cargo3);

        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);
        handler.add(listener);
        handler.handle(event);
        handler.handle(event2);
        handler.handle(event3);
        assertEquals(3, storage.getHowManyCargo());
    }

    @Test
    public void storageIsFullExceptionDuringEventTest1() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {

        StorageImpl storage = new StorageImpl(1);
        CustomerManagement management = new CustomerManagementImpl();

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        Customer customer1 = new CustomerImpl("Sally");
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer1);
        UnitisedCargoImpl unitisedCargo = mock(UnitisedCargoImpl.class);
        management.addCargo(customer,unitisedCargo,storage);

        String command3 = "MixedLiquidBulkAndUnitised Sally 34.4 2 explosive y y";
        String[] cargo3 = command3.split(" ");

        AddCargoEvent event3 = new AddCargoEvent(this, cargo3);

        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        handler.add(listener);
        handler.handle(event3);



        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Storage is full";
        assertEquals(expected,captor.getValue().getMessage());


    }

    @Test
    public void storageIsFullExceptionDuringEventTest2() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        StorageImpl storage = new StorageImpl(1);
        CustomerManagement management = new CustomerManagementImpl();

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        Customer customer1 = new CustomerImpl("Sally");
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer1);

        String command = "UnitisedCargo Erik 34.4 43 radioactive,flammable y";
        String[] cargo = command.split(" ");

        UnitisedCargoImpl unitisedCargo = mock(UnitisedCargoImpl.class);
        management.addCargo(customer,unitisedCargo,storage);

        AddCargoEvent event = new AddCargoEvent(this, cargo);

        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        handler.add(listener);
        handler.handle(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Storage is full";
        assertEquals(expected,captor.getValue().getMessage());
    }


    @Test
    public void storageIsFullExceptionDuringEventTest3() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        StorageImpl storage = new StorageImpl(1);
        CustomerManagement management = new CustomerManagementImpl();

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        Customer customer1 = new CustomerImpl("Sally");
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer1);

        String command = "LiquidBulkCargo Erik 34.4 43 radioactive,flammable y";
        String[] cargo = command.split(" ");

        UnitisedCargoImpl unitisedCargo = mock(UnitisedCargoImpl.class);
        management.addCargo(customer,unitisedCargo,storage);

        AddCargoEvent event = new AddCargoEvent(this, cargo);

        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        handler.add(listener);
        handler.handle(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Storage is full";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void storageIsFullExceptionDuringEventTest4() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        StorageImpl storage = new StorageImpl(1);
        CustomerManagement management = new CustomerManagementImpl();

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        Customer customer1 = new CustomerImpl("Sally");
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer1);

        String command = "MixedLiquidBulkAndUnitised Erik 34.4 43 radioactive,flammable n y";
        String[] cargo = command.split(" ");

        UnitisedCargoImpl unitisedCargo = mock(UnitisedCargoImpl.class);
        management.addCargo(customer,unitisedCargo,storage);

        AddCargoEvent event = new AddCargoEvent(this, cargo);

        AddCargoEventHandler handler = new AddCargoEventHandler();
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage, management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        handler.add(listener);
        handler.handle(event);


        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Storage is full";
        assertEquals(expected,captor.getValue().getMessage());
    }


    @Test
    public void wrongDurationTest() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 45 WRONG flammable y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Duration must be numeric";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void wrongHazardTest() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 45 43 WRONG y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "unknown hazard, allowed Hazard [flammable, explosive, toxic, radioactive]";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void wrongBigDecimalTest1() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 4-5 43 radioactive y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Wrong argument! Allowed [0-9]+ or [0-9]+[.][0-9]+";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void wrongBigDecimalTest2() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik .45 43 radioactive y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Wrong argument! Allowed [0-9]+ or [0-9]+[.][0-9]+";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void wrongBigDecimalTest3() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 45. 43 radioactive y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Wrong argument! Allowed [0-9]+ or [0-9]+[.][0-9]+";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void wrongBigDecimalTest4() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 12..12 43 radioactive y";
        String[] cargo = command.split(" ");

        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Wrong argument! Allowed [0-9]+ or [0-9]+[.][0-9]+";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void mixedCargoWrongLastArgumentTest() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 12.12 43 radioactive y y1";
        String[] cargo = command.split(" ");


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "y or n allowed, string length > 1 detected";
        assertEquals(expected,captor.getValue().getMessage());
    }

    @Test
    public void createCustomerErrorMessageTest() throws StorageToSmallException {
        CustomerManagement management = new CustomerManagementImpl();
        Customer customer = new CustomerImpl("Sally");
        management.addCustomer(customer);

        String command = "MixedLiquidBulkAndUnitised Erik 12.12 43 radioactive y y";
        String[] cargo = command.split(" ");


        PrintEventHandler printEventHandler = mock(PrintEventHandler.class);
        PrintEventListener view = mock(View.class);
        printEventHandler.add(view);

        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        StorageImpl storage = new StorageImpl(2);
        AddCargoEvent event = new AddCargoEvent(this,cargo);
        AddCargoEventListenerImpl listener = new AddCargoEventListenerImpl(storage,management);
        listener.setLogEventHandler(logEventHandler);
        listener.setPrintEventHandler(printEventHandler);
        listener.onAddCargoEvent(event);

        ArgumentCaptor<PrintEvent> captor = ArgumentCaptor.forClass(PrintEvent.class);
        verify(printEventHandler).handle(captor.capture());
        String expected = "Customer does not exist, create one";
        assertEquals(expected,captor.getValue().getMessage());
    }
}
