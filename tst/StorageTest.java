import javafx.collections.ObservableList;
import observer.Observer;
import observer.ObserverSizeImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.*;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class StorageTest {

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
    private Customer customer2 = new CustomerImpl(NAME_2);
    private BigDecimal bd2 = new BigDecimal(23);
    private Duration d2 = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards2 = new ArrayList<>();
    private Date date2 = new Date();
    private boolean pressurized2 = true;
    //--------------------------------------------------------------

    private CustomerManagementImpl management;
    private StorageImpl storage;


    @Test
    public void initTest(){
        assertThrows(StorageToSmallException.class,()->{
            Storage storage = new StorageImpl(-10);
        });
    }

    @Test
    public void initTest2(){
        assertThrows(StorageToSmallException.class,()->{
            StorageImpl storage = new StorageImpl(-1);
        });
    }

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
    public void storageGetLengthTest() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(9);
        int expectedLength = 9;
        int actualLength = storage.getStorageSize();
        assertEquals(expectedLength,actualLength);
    }

    @Test
    public void freeSpaceTest1(){
        int freeSpace = storage.freeSpaceInStorage();
        int expected = 0;
        assertEquals(expected,freeSpace);
    }

    @Test
    public void freeSpaceTest2() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(2);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        int expectedFreeSpace = -1;   //no free space
        assertEquals(expectedFreeSpace,storage.freeSpaceInStorage());
    }

    @Test
    public void freeSpaceTest3() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(3);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        int expectedFreeSpace = 2;
        assertEquals(expectedFreeSpace,storage.freeSpaceInStorage());
    }

    @Test
    public void freeSpaceTest4() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(3);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        storage.delete(0);
        int expectedFreeSpace = 0;
        assertEquals(expectedFreeSpace,storage.freeSpaceInStorage());
    }

    @Test
    public void isStorageFullTest() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(2);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        assertTrue(storage.checkIfStorageIsFull());
    }

    @Test
    public void isStorageFullTest2() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(3);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        assertFalse(storage.checkIfStorageIsFull());
    }

    @Test
    public void isStorageFullTest3() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(12);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        assertFalse(storage.checkIfStorageIsFull());
    }

    @Test
    public void deleteCargoTest() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(2);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        assertTrue(storage.checkIfStorageIsFull());
        storage.delete(1);
        assertFalse(storage.checkIfStorageIsFull());
    }

    @Test
    public void cargoNotFoundExceptionTest(){
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        int expectedPos = -1;
        assertEquals(expectedPos,storage.getPositionOfCargo(cargo2));
    }

    @Test
    public void getPositionOfCargoTest() throws StorageToSmallException {
        StorageImpl storage = new StorageImpl(2);
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        int expectedPosition = 0;
        assertEquals(expectedPosition,storage.getPositionOfCargo(cargo1));
    }

    @Test
    public void getOwnerOfCargoTest(){
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        assertEquals(customer2,storage.getOwnerOfCargo(0));
    }


    @Test
    public void observerTest(){
        Observer observer = new ObserverSizeImpl(storage);
        storage.signOut(observer);
    }


    @Test
    public void deleteCargoFromCustomerTest(){
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        storage.delete(customer1);
        int expectedFreeSpace = 1;
        assertEquals(expectedFreeSpace,storage.freeSpaceInStorage());
    }

    @Test
    public void getCargoTest(){
        assertThrows(IndexOutOfBoundsException.class,()->{
           storage.getCargo(-1);
        });
    }

    @Test
    public void getCargoTest2(){
        assertThrows(IndexOutOfBoundsException.class,()->{
            StorageImpl storage = new StorageImpl(2);
            storage.getCargo(2);
        });
    }

    @Test
    public void getCargoTest3() {
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2, bd2, d2, hazards2, date2, pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1, bd, d, hazards, date, fragile);
        storage.add(cargo1, storage.freeSpaceInStorage());
        storage.add(cargo2, storage.freeSpaceInStorage());
        assertEquals(cargo1, storage.getCargo(0));
    }






    @Test
    public void getStorageCargoTest1(){
        LiquidBulkCargoImpl cargo1 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        UnitisedCargoImpl cargo2 = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        storage.add(cargo1,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        ObservableList<Cargo> cargoList = storage.getStorageCargos();
        assertEquals(storage.getHowManyCargo(),cargoList.size());
    }


    @Test
    public void getHazardOfStorageTest(){
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.radioactive);
        hazards2.add(Hazard.explosive);
        hazards.add(Hazard.toxic);
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        LiquidBulkCargoImpl cargo2 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        storage.add(cargo,0);
        storage.add(cargo2,2);
        Collection<Hazard> hazardCollection = storage.getHazardOfStorage();
        assertTrue(hazardCollection.contains(Hazard.explosive));
        assertTrue(hazardCollection.contains(Hazard.radioactive));
        assertTrue(hazardCollection.contains(Hazard.flammable));
        assertTrue(hazardCollection.contains(Hazard.toxic));
    }

    @Test
    public void getNonHazardOfStorage() {
        Collection<Hazard> myHazard = new ArrayList<>();
        myHazard.add(Hazard.flammable);
        myHazard.add(Hazard.explosive);
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer1,bd,d,myHazard,date,fragile);
        LiquidBulkCargoImpl cargo2 = new LiquidBulkCargoImpl(customer2,bd2,d2,myHazard,date2,pressurized2);
        storage.add(cargo,0);
        storage.add(cargo2,2);
        Collection<Hazard> nonHazard2 = storage.getNonHazardOfStorage();
        System.out.println(nonHazard2.toString());
        assertTrue(nonHazard2.contains(Hazard.radioactive));
        assertTrue(nonHazard2.contains(Hazard.toxic));
    }

    @Test
    public void getNonHazardTest(){
        Collection<Hazard> hazardCollection = storage.getNonHazardOfStorage();
        assertTrue(hazardCollection.contains(Hazard.explosive));
        assertTrue(hazardCollection.contains(Hazard.radioactive));
        assertTrue(hazardCollection.contains(Hazard.flammable));
        assertTrue(hazardCollection.contains(Hazard.toxic));
    }




}
