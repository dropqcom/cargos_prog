package ListenerTest;

import events.deleteCargoAtPosition.DeleteCargoAtPositionEvent;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventListenerImpl;
import events.deleteCustomerEvent.DeleteCustomerEvent;
import events.deleteCustomerEvent.DeleteCustomerEventListenerImpl;
import events.logEvent.LogEventHandler;
import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.*;
import storageContract.administration.*;
import storageContract.cargo.Hazard;
import storageContract.cargo.UnitisedCargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListenerTest {


    //---------------- for unitised cargo -------------------------
    private final String NAME_1 = "Freddy";
    private Customer customer1 = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private boolean fragile = false;
    //-------------------------------------------------------------
    //----------------- for liquid bulk cargo ---------------------
    private final String NAME_2 = "Sally";
    private Customer customer2 = new CustomerImpl(NAME_1);
    private BigDecimal bd2 = new BigDecimal(23);
    private Duration d2 = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards2 = new ArrayList<>();
    private Date date2 = new Date();
    private boolean pressurized2 = true;
    //--------------------------------------------------------------


    private CustomerManagement management;
    private Storage storage;

    @BeforeEach
    public void init() throws StorageToSmallException {
        management = new CustomerManagementImpl();
        storage = new StorageImpl(5);
    }

    @AfterEach
    public void clear(){
        management.clear();
        storage.clear();
    }


//    @Test
//    public void addCustomerEventTest() throws CustomerDoesNotExistException {
//        LogEventHandler logEventHandler = new LogEventHandler();
//        Logger logger = mock(Logger.class);
//        logEventHandler.addListener(logger);
//
//
//        AddCustomerEventListenerImpl addCustomerEventListener = new AddCustomerEventListenerImpl(storage,management);
//        AddCustomerEvent mockEvent = new AddCustomerEvent(this,"Teddy");
//        addCustomerEventListener.onAddCustomerEvent(mockEvent);
//        addCustomerEventListener.setLogEventHandler(logEventHandler);
//
//        //Customer should have been added
//        Customer customer = management.getCustomer("Teddy");
//        assertEquals(mockEvent.getCustomerName(),customer.getName());
//    }

    @Test
    public void deleteCustomerEventTest()  {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);

        Customer customer = new CustomerImpl("Erik");
        management.addCustomer(customer);
        assertTrue(management.checkIfCustomerExist(customer.getName()));
        DeleteCustomerEventListenerImpl deleteCustomerEventListener = new DeleteCustomerEventListenerImpl(storage,management);
        deleteCustomerEventListener.setLogEventHandler(logEventHandler);
        DeleteCustomerEvent mockEvent = mock(DeleteCustomerEvent.class);
        when(mockEvent.getCustomerName()).thenReturn("Erik");
        deleteCustomerEventListener.onDeleteCustomerEvent(mockEvent);

        //Customer should have been deleted
        assertFalse(management.checkIfCustomerExist(customer.getName()));
    }

    @Test
    public void deleteCargoAtPositionEventTest() throws CustomerDoesNotExistException, StorageIsFullException {
        LogEventHandler logEventHandler = new LogEventHandler();
        Logger logger = mock(Logger.class);
        logEventHandler.addListener(logger);
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        management.addCustomer(customer1);
        management.addCargo(customer1,cargo,storage);
        DeleteCargoAtPositionEventListenerImpl deleteCargoAtPositionEventListener = new DeleteCargoAtPositionEventListenerImpl(storage,management);
        deleteCargoAtPositionEventListener.setLogEventHandler(logEventHandler);
        DeleteCargoAtPositionEvent mockEvent = mock(DeleteCargoAtPositionEvent.class);
        when(mockEvent.getPosition()).thenReturn("0");
        deleteCargoAtPositionEventListener.onDeleteCargoAtPositionEvent(mockEvent);

        //cargo should have been deleted
        assertEquals(0,storage.freeSpaceInStorage());

    }




}
