package simulationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.Outsourcer;
import simulation.Storer;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;
import storageContract.cargo.LiquidBulkCargoImpl;
import storageContract.cargo.UnitisedCargoImpl;

import java.io.ObjectInput;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimulationTests {


    /*
     * Testing all the method used for Simulation
     * addCargo(Customer customer, CargoImpl cargo, Storage storage) already tested in CustomerManagementTest
     * checkIfStorageIsFull() already tested in StorageTest
     * deleteCargo(int position, Storage storage) already tested in CustomerManagementTest
     */



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

    @Test
    public void storerConstructorTest(){
        StorageImpl storage = mock(StorageImpl.class);
        StorageImpl storage2 = mock(StorageImpl.class);
        StorageImpl storage3 = mock(StorageImpl.class);
        CustomerManagementImpl management = mock(CustomerManagementImpl.class);
        ArrayList<CargoImpl> arrayList = mock(ArrayList.class);
        Object monitor = mock(Object.class);
        Storer storer = new Storer(storage,storage2,storage3,management,arrayList,"test",monitor);
        assertNotNull(storer);
    }

    @Test
    public void outsourceConstructorTest(){
        StorageImpl storage = mock(StorageImpl.class);
        StorageImpl storage2 = mock(StorageImpl.class);
        StorageImpl storage3 = mock(StorageImpl.class);
        CustomerManagementImpl management = mock(CustomerManagementImpl.class);
        Object monitor = mock(Object.class);
        Outsourcer outsourcer = new Outsourcer(storage,storage2,storage3,management,"test",monitor);
        assertNotNull(outsourcer);
    }

    @Test
    public void getHowManyCargoTest1() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        int howManyCargo = storage.getHowManyCargo();
        assertEquals(2,howManyCargo);
    }

    @Test
    public void getHowManyCargoTest2(){
        int howManyCargo = storage.getHowManyCargo();
        assertEquals(0,howManyCargo);
    }

    @Test
    public void getOldestCargoTest1() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCargo(customer,cargo2,storage);
        CargoImpl oldestCargo = Outsourcer.getOldestCargo(storage);
        assertEquals(cargo1,oldestCargo);
    }


    @Test
    public void getOldestCargoTest2() throws CustomerDoesNotExistException, StorageIsFullException, InterruptedException {
        CustomerImpl customer = new CustomerImpl("Erik");
        CustomerImpl customer2 = new CustomerImpl("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCustomer(customer2);
        management.addCargo(customer,cargo1,storage);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer2,bd,d,hazards,date,fragile);
        management.addCargo(customer2,cargo2,storage);
        management.deleteCargo(1,storage); //cargo2
        LiquidBulkCargoImpl cargo3 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCargo(customer,cargo3,storage);
        CargoImpl oldestCargo = Outsourcer.getOldestCargo(storage);

        assertEquals(cargo1,oldestCargo);
    }

    @Test
    public void getPositionOfOldestCargoTest2() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        management.addCustomer(customer);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCargo(customer,cargo1,storage);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCargo(customer,cargo2,storage);
        management.deleteCargo(0,storage); //cargo1
        UnitisedCargoImpl cargo3 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCargo(customer,cargo3,storage);
        management.deleteCargo(0,storage);
        UnitisedCargoImpl cargo4 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCargo(customer,cargo4,storage);
        int pos = Outsourcer.getPositionOfOldestCargo(storage);
        assertEquals(1,pos);
    }

    @Test
    public void getPositionOfOldestCargoTest1() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCargo(customer,cargo1,storage);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCargo(customer,cargo2,storage);
        int pos = Outsourcer.getPositionOfOldestCargo(storage);
        assertEquals(0,pos);
    }

    @Test
    public void getOldestCargoTest() throws CustomerDoesNotExistException, StorageIsFullException {
        LocalDateTime newer = LocalDateTime.of(2012,12,12,12,12);
        LocalDateTime older = LocalDateTime.of(2012,1,1,1,1);
        CustomerImpl customer = new CustomerImpl("Erik");
        CustomerImpl customer2 = new CustomerImpl("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date,pressurized2);
        management.addCustomer(customer);
        management.addCustomer(customer2);
        management.addCargo(customer,cargo1,storage);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer2,bd,d,hazards,date,fragile);
        management.addCargo(customer2,cargo2,storage);
        cargo1.setStorageDate(newer);
        cargo2.setStorageDate(older);
        assertEquals(cargo2,Outsourcer.getOldestCargo(storage));
    }


}
