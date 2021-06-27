import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.mockito.internal.matchers.Null;
import storageContract.Exceptions.*;
import storageContract.administration.*;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerManagementTest {

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
    public void addCustomerTest() {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        management.addCustomer(customer);
        int expectedSize = 1;
        int actualSize = management.getNumberOfCustomer();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    public void addMoreCustomerTest() {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        CustomerImpl customer2 = mock(CustomerImpl.class);
        when(customer2.getName()).thenReturn("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer2);
        int expectedSize = 2;
        int actualSize = management.getNumberOfCustomer();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    public void deleteCustomerTest() throws CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        CustomerImpl customer2 = mock(CustomerImpl.class);
        when(customer2.getName()).thenReturn("Erik");
        management.addCustomer(customer);
        management.addCustomer(customer2);
        management.delete(customer, storage);
        int expectedSize = 1;
        int actualSize = management.getNumberOfCustomer();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    public void deleteCustomerExceptionTest(){
        assertThrows(CustomerDoesNotExistException.class,()->{
            CustomerImpl customer = mock(CustomerImpl.class);
            when(customer.getName()).thenReturn("Sam");
            CustomerImpl customer2 = mock(CustomerImpl.class);
            when(customer2.getName()).thenReturn("Erik");
            management.addCustomer(customer);
            management.delete(customer2,storage);
        });
    }


    @Test
    public void addCargoButStorageFullTest(){
        assertThrows(StorageIsFullException.class,()->{
           Storage storage = new StorageImpl(1);
            CustomerImpl customer = mock(CustomerImpl.class);
            when(customer.getName()).thenReturn("Sam");
            LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
            UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
            management.addCustomer(customer);
            management.addCargo(customer,cargo1,storage);
            management.addCargo(customer,cargo2,storage);
        });
    }

    @Test
    public void addCargoTest1() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        assertEquals(cargo1,storage.getCargo(0));
    }

    @Test
    public void addCargoTest2() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        List<Cargo> cargoListFromCustomer = management.getCargoFromCustomer(customer.getName());
        assertEquals(cargo1,cargoListFromCustomer.get(0));
    }

    @Test
    public void addCargoTest3() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        List<Cargo> cargoListFromCustomer = management.getCargoFromCustomer(customer.getName());
        assertEquals(cargo1,cargoListFromCustomer.get(0));
        assertEquals(cargo2,cargoListFromCustomer.get(1));
    }

    @Test
    public void getCargoFromCustomerButDoesNotExistTest(){
        assertThrows(CustomerDoesNotExistException.class,()->{
            CustomerImpl customer = mock(CustomerImpl.class);
            when(customer.getName()).thenReturn("Sam");
            management.getCargoFromCustomer(customer.getName());
        });
    }

//    @Test
//    public void getCargoFromCustomerTest1() throws CustomerDoesNotExistException, StorageIsFullException {
//        Customer customer = new CustomerImpl("Erik");
//        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
//        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
//
//        Customer customer2 = new CustomerImpl("Sally");
//        MixedCargoLiquidBulkAndUnitisedImpl cargo3 = new MixedCargoLiquidBulkAndUnitisedImpl(customer2,bd,d,hazards,date,true,false);
//
//        management.addCargo(customer,cargo1,storage);
//        management.addCargo(customer2,cargo3,storage);
//        management.addCargo(customer,cargo2,storage);
//
//        management.deleteCargo(0,storage);
//
//        management.addCargo(customer,cargo1,storage);
//
//        List<Cargo> cargoList = management.getCargoFromCustomer(customer);
//        System.out.println("TESTEST" + cargoList.toString());
//
//        management.deleteCargo(0,storage);
//        cargoList = management.getCargoFromCustomer(customer);
//        System.out.println("TESTTEST " + cargoList.toString());
//    }

    @Test
    public void deleteCargoExceptionTest1(){
        assertThrows(IndexOutOfBoundsException.class,()->{
           management.deleteCargo(-2,storage);
        });
    }

    @Test
    public void deleteCargoExceptionTest2(){
        assertThrows(IndexOutOfBoundsException.class,()->{
            management.deleteCargo(10,storage);
        });
    }

    @Test
    public void deleteCargoTest1() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.deleteCargo(0,storage);
        assertNull(storage.getCargo(0));
    }

    @Test
    public void deleteCargoTest2() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.deleteCargo(1,storage);
        List<Cargo> actualList = management.getCargoFromCustomer(customer.getName());
        int expectedSize = 1;
        assertEquals(expectedSize,actualList.size());
    }

    @Test
    public void deleteCargoTest3() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        hazards.add(Hazard.toxic);
        Customer customer3 = new CustomerImpl("Erik");
        MixedCargoLiquidBulkAndUnitisedImpl cargo3 = new MixedCargoLiquidBulkAndUnitisedImpl(customer3,bd,d2,hazards,date,true,false);
        management.addCustomer(customer);
        management.addCustomer(customer3);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.addCargo(customer3,cargo3,storage);
        management.deleteCargo(0,storage);
        ArrayList<Cargo> expectedList = new ArrayList<>();
        expectedList.add(cargo2);
        expectedList.add(cargo3);
        List<Cargo> aCargo = management.getCargoFromCustomer(customer.getName());
        List<Cargo> bCargo = management.getCargoFromCustomer(customer3.getName());
        ArrayList<Cargo> actualList = new ArrayList<>();
        actualList.addAll(aCargo);
        actualList.addAll(bCargo);
        assertEquals(actualList,expectedList);
    }

    @Test
    public void getPositionOfCargoTest() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = new CustomerImpl("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        hazards.add(Hazard.toxic);
        Customer customer3 = new CustomerImpl("Erik");
        MixedCargoLiquidBulkAndUnitisedImpl cargo3 = new MixedCargoLiquidBulkAndUnitisedImpl(customer3,bd,d2,hazards,date,true,false);
        management.addCustomer(customer);
        management.addCustomer(customer3);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.addCargo(customer3,cargo3,storage);
        management.deleteCargo(0,storage);
        ArrayList<Cargo> actualList = new ArrayList<>();
        int position_cargo1 = storage.getPositionOfCargo(cargo1);
        int position_cargo2 = storage.getPositionOfCargo(cargo2);
        int position_cargo3 = storage.getPositionOfCargo(cargo3);
        assertEquals(1,position_cargo2);
        assertEquals(2,position_cargo3);
        assertEquals(-1,position_cargo1); //-1 because deleted
    }

    @Test
    public void setInspectionDateExceptionTest1(){
        assertThrows(IndexOutOfBoundsException.class,()->{
           management.setInspectionDate(-2,storage);
        });
    }

    @Test
    public void setInspectionDateExceptionTest2(){
        assertThrows(IndexOutOfBoundsException.class,()->{
            management.setInspectionDate(10,storage);
        });
    }

    @Test
    public void setInspectionDateExceptionTest3(){
        assertThrows(NullPointerException.class,()->{
            management.setInspectionDate(1,storage);
        });
    }


    @Test
    public void setInspectionDateTest() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.setInspectionDate(0,storage);
        CargoImpl cargo = storage.getCargo(0);
        assertNotNull(cargo.getLastInspectionDate());
    }

    @Test
    public void getCustomerAndNumberOfCargoTest() throws StorageIsFullException, CustomerDoesNotExistException {
        CustomerImpl customer = new CustomerImpl("sam");
        CustomerImpl customer2 = new CustomerImpl("Erik");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
        UnitisedCargoImpl cargo3 = new UnitisedCargoImpl(customer2,bd,d,hazards,date,false);
        management.addCustomer(customer);
        management.addCustomer(customer2);
        management.addCargo(customer,cargo1,storage);
        management.addCargo(customer,cargo2,storage);
        management.addCargo(customer2,cargo3,storage);
        Map<Customer, Integer> map = management.getCustomerAndNumberOfCargo();
        int mapSize = map.size();
        int expectedSize = 2;
        assertEquals(expectedSize,mapSize);
        int expectedCargo = 2;
        int actualCargo = map.get(customer);
        assertEquals(expectedCargo,actualCargo);
    }





    @Test
    public void setStorageDateTest() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        assertNotNull(cargo1.getStorageDate());
    }


    @Test
    public void addCargoTest() throws CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = mock(CustomerImpl.class);
        when(customer.getName()).thenReturn("Sam");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCustomer(customer);
        management.addCargo(customer,cargo1,storage);
        assertNotNull(management.getCustomerAndNumberOfCargo());
    }

    @Test
    public void deleteCargoExceptionTest() {
        assertThrows(NullPointerException.class,()->{
            CustomerImpl customer = mock(CustomerImpl.class);
            when(customer.getName()).thenReturn("Sam");
            CustomerImpl customer2 = mock(CustomerImpl.class);
            when(customer.getName()).thenReturn("Erik");
            LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
            management.addCustomer(customer);
            management.addCustomer(customer2);
            management.addCargo(customer2,cargo1,storage);
            management.delete(customer,storage);
        });
    }

    @Test
    public void deleteCustomerThatDoesNotExistTest() {
        Customer customer = new CustomerImpl("Erik");
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        try{
            management.addCargo(customer,cargo1,storage);
            management.deleteCargo(2,storage);
        }  catch (CustomerDoesNotExistException | StorageIsFullException e) {
           assertEquals("Customer or cargo does not exist at that position", e.getMessage());
        }
    }

    @Test
    public void customerDoesNotExistExceptionTest(){
        assertThrows(CustomerDoesNotExistException.class,()->{
           management.getCustomer("Dumbo");
        });
    }

    @Test
    public void checkIfCustomerExistTest() throws CustomerDoesNotExistException {
        Customer customer = new CustomerImpl("Erik");
        Customer customer2 = new CustomerImpl("Sam");
        management.addCustomer(customer);
        management.addCustomer(customer2);
        management.delete(customer2,storage);
        assertTrue(management.checkIfCustomerExist(customer.getName()));
        assertFalse(management.checkIfCustomerExist(customer2.getName()));
    }

    @Test
    public void addCargoTest4() throws CustomerDoesNotExistException, StorageIsFullException {
        Customer customer = new CustomerImpl("Test");
        management.addCustomer(customer);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer,bd2,d2,hazards2,date2,pressurized2);
        management.addCargo(customer,cargo1,storage);
        assertEquals(1,storage.getHowManyCargo());
    }



}
