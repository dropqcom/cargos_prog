import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;
import storageContract.cargo.UnitisedCargoImpl;

import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CargoTest {

    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private Date nullDate = null;
    private CargoImpl cargo;

    @BeforeEach
    public void init(){
        hazards.add(Hazard.explosive);
        cargo = new CargoImpl(customer,bd,d,hazards,date);
    }

    @Test
    public void getOwnerTest(){
        assertEquals(customer,cargo.getOwner());
    }

    @Test
    public void getValueTest(){
        assertEquals(bd,cargo.getValue());
    }

    @Test
    public void getDurationOfStorageTest(){
        assertEquals(d,cargo.getDurationOfStorage());
    }

    @Test
    public void getHazardsTest(){
        assertEquals(hazards,cargo.getHazards());
    }

    @Test
    public void getLastInspectionDate(){
        hazards.add(Hazard.flammable);
        Cargo cargo = new CargoImpl(customer,bd,d,hazards,date);
        assertEquals(date,cargo.getLastInspectionDate());
    }

    @Test
    public void hasStorageDateBeenSetTest1(){
        assertFalse(cargo.isHasStorageDateBeenSet());
    }

    @Test
    public void hasStorageDateBeenSetTest2(){
        cargo.setHasStorageDateBeenSet(true);
        assertTrue(cargo.isHasStorageDateBeenSet());
    }

    @Test
    public void setOwnerTest(){
        Customer customer2 = new CustomerImpl("Sally");
        cargo.setOwner(customer2);
        assertEquals("Sally",cargo.getOwner().getName());
    }

    @Test
    public void setValueTest(){
        BigDecimal new_bd = new BigDecimal(9999);
        cargo.setValue(new_bd);
        assertEquals(new_bd,cargo.getValue());
    }

    @Test
    public void setStorageDurationTest(){
        Duration new_d = Duration.ofSeconds(999);
        cargo.setStorageDuration(new_d);
        assertEquals(new_d,cargo.getStorageDuration());
    }

    @Test
    public void setHazardTest(){
        Collection<Hazard> new_h = new ArrayList<>();
        new_h.add(Hazard.toxic);
        cargo.setHazard(new_h);
        assertNotNull(cargo.getHazard());
    }

    @Test
    public void getStoragePositionTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        Storage storage = new StorageImpl(2);
        CustomerManagement management = new CustomerManagementImpl();
        management.addCargo(customer,cargo,storage);
        assertEquals(0,cargo.getStoragePosition());
    }

    @Test
    public void hasStorageDateBeenSetTest(){
        CargoImpl cargo = new CargoImpl(customer,bd,d,hazards,date);
        assertFalse(cargo.isHasStorageDateBeenSet());
    }

    @Test
    public void getSerialVersionUIDTest(){
        assertEquals(1L,CargoImpl.getSerialVersionUID());
    }

    @Test
    public void setter_setOwnerTest() throws NoSuchFieldException, IllegalAccessException {
        Customer customer = new CustomerImpl("Dummy");
        cargo.setOwner(customer);

        final Field field = cargo.getClass().getDeclaredField("owner");
        field.setAccessible(true);
        assertEquals(field.get(cargo),customer);
    }

    @Test
    public void getter_getOwnerTest() throws NoSuchFieldException, IllegalAccessException {
        Customer customer = new CustomerImpl("dummy");
        final Field field = cargo.getClass().getDeclaredField("owner");
        field.setAccessible(true);
        field.set(cargo,customer);
        final Customer actual = cargo.getOwner();
        assertEquals(actual,customer);
    }

    @Test
    public void setter_setValueTest() throws NoSuchFieldException, IllegalAccessException {
        BigDecimal bd = new BigDecimal(123);
        cargo.setValue(bd);
        final Field field = cargo.getClass().getDeclaredField("value");
        field.setAccessible(true);
        assertEquals(field.get(cargo),bd);
    }

    @Test
    public void getter_getValueTest() throws NoSuchFieldException, IllegalAccessException {
        BigDecimal bd = new BigDecimal(123);
        final Field field = cargo.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(cargo,bd);
        final BigDecimal actual = cargo.getValue();
        assertEquals(actual,bd);
    }


    @Test
    public void setter_setLastInspectionDateTest() throws NoSuchFieldException, IllegalAccessException {
        Date date = new Date();
        cargo.setLastInspectionDate(date);
        final Field field = cargo.getClass().getDeclaredField("lastInspectionDate");
        field.setAccessible(true);
        assertEquals(field.get(cargo),date);
    }

    @Test
    public void getter_getLastInspectionDateTest() throws NoSuchFieldException, IllegalAccessException {
        Date date = new Date();
        final Field field = cargo.getClass().getDeclaredField("lastInspectionDate");
        field.setAccessible(true);
        field.set(cargo,date);
        final Date actual = cargo.getLastInspectionDate();
        assertEquals(date,actual);
    }

    @Test
    public void setter_storageDurationTest() throws NoSuchFieldException, IllegalAccessException {
        Duration duration = Duration.ofSeconds(123);
        cargo.setStorageDuration(duration);
        final Field field = cargo.getClass().getDeclaredField("storageDuration");
        field.setAccessible(true);
        assertEquals(field.get(cargo),duration);
    }

    @Test
    public void getter_getStorageDurationTest() throws NoSuchFieldException, IllegalAccessException {
        Duration duration = Duration.ofSeconds(123);
        final Field field = cargo.getClass().getDeclaredField("storageDuration");
        field.setAccessible(true);
        field.set(cargo,duration);
        final Duration actual = cargo.getStorageDuration();
        assertEquals(duration,actual);
    }

    @Test
    public void toStringTest(){
        CargoImpl cargo = new CargoImpl(customer,bd,d,hazards,nullDate);
        String expected = "CargoImpl{owner=Freddy, value=23, lastInspectionDate=null, storageDuration=PT37M2S, hazard=[explosive], storagePosition=0, storageDate=null}";
        assertEquals(expected,cargo.toString());
    }

}
