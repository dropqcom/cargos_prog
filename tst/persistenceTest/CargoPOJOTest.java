package persistenceTest;

import org.junit.jupiter.api.Test;
import persistence.CargoPOJO;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.cargo.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CargoPOJOTest {


    private Customer owner;
    private String valueStr = "232.2";
    private String storageDurationStr = "PT2M3S";
    private Collection<Hazard> hazardCollection;
    private Date lastInspectionDate = new Date();
    private String storageDateString = "2020-09-21T12:59";
    private boolean isPressurized = true;
    private boolean isFragile = false;
    private String cargoTyp = "UnitisedCargoImpl";

    private BigDecimal bigDecimal = new BigDecimal(233.2);
    private Duration duration = Duration.ofSeconds(121);
    private boolean hasStorageDateBeenSet = false;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Duration storageDuration = Duration.ofSeconds(23);

    private CargoPOJO cargoPOJO = new CargoPOJO();




    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();


    @Test
    public void constructorTest1(){
        owner = new CustomerImpl("TestDummy");
        CargoPOJO cargoPOJO = new CargoPOJO(owner,valueStr,storageDurationStr,hazardCollection,lastInspectionDate,storageDateString,isPressurized,isFragile,cargoTyp);
        assertNotNull(cargoPOJO);
    }

    @Test
    public void constructorTest2(){
        owner = new CustomerImpl("TestDummy");
        CargoPOJO cargoPOJO = new CargoPOJO(owner,bigDecimal,duration,hazardCollection,lastInspectionDate,hasStorageDateBeenSet,localDateTime);
        assertNotNull(cargoPOJO);
    }

    @Test
    public void getStorageDurationStringTest(){
        String expected = "1";
        cargoPOJO.setStorageDurationStr(expected);
        String actual = cargoPOJO.getStorageDurationStr();
        assertEquals(expected,actual);
    }


    @Test
    public void valueToStringTest(){
        BigDecimal bd = new BigDecimal(123);
        String actual = cargoPOJO.valueToString(bd);
        assertEquals("123",actual);
    }

    @Test
    public void storageDateToStringTest(){
        LocalDateTime date = LocalDateTime.of(2020,9,21,12,59);
        String actual = cargoPOJO.storageDateToString(date);
        assertEquals("2020-09-21T12:59",actual);
    }

    @Test
    public void storageDurationToStringTest(){
        Duration duration = Duration.ofSeconds(123);
        String actual = cargoPOJO.storageDurationToString(duration);
        assertEquals("PT2M3S",actual);
    }



    @Test
    public void setter_setValueStrTest() throws NoSuchFieldException, IllegalAccessException {
        String str = "123";
        cargoPOJO.setValueStr(str);
        final Field field = cargoPOJO.getClass().getDeclaredField("valueStr");
        field.setAccessible(true);
        assertEquals(field.get(cargoPOJO),str);
    }

    @Test
    public void getter_getValueStrTest() throws NoSuchFieldException, IllegalAccessException {
        String str = "123";
        final Field field = cargoPOJO.getClass().getDeclaredField("valueStr");
        field.setAccessible(true);
        field.set(cargoPOJO,str);
        final String actual = cargoPOJO.getValueStr();
        assertEquals(str,actual);
    }

    @Test
    public void setter_setStorageDateStrTest() throws NoSuchFieldException, IllegalAccessException {
        String str = "12-09-2020";
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDateStr");
        field.setAccessible(true);
        cargoPOJO.setStorageDateStr(str);
        assertEquals(field.get(cargoPOJO),str);
    }

    @Test
    public void getter_getStorageDateStrTest() throws NoSuchFieldException, IllegalAccessException {
        String str = "12-09-2020";
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDateStr");
        field.setAccessible(true);
        field.set(cargoPOJO,str);
        final String actual = cargoPOJO.getStorageDateStr();
        assertEquals(str,actual);
    }

    @Test
    public void setter_setPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cargoPOJO.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        cargoPOJO.setPressurized(isPressurized);
        assertEquals(field.get(cargoPOJO),isPressurized);
    }

    @Test
    public void setter_setCargoTypTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("cargoTyp");
        field.setAccessible(true);
        cargoPOJO.setCargoTyp(cargoTyp);
        assertEquals(field.get(cargoPOJO),cargoTyp);
    }

    @Test
    public void setter_setOwnerTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("owner");
        field.setAccessible(true);
        cargoPOJO.setOwner(owner);
        assertEquals(field.get(cargoPOJO),owner);
    }

    @Test
    public void setter_setFragileTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        cargoPOJO.setFragile(isFragile);
        assertEquals(field.get(cargoPOJO),isFragile);
    }

    @Test
    public void setter_setHazardTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("hazard");
        field.setAccessible(true);
        cargoPOJO.setHazard(hazardCollection);
        assertEquals(field.get(cargoPOJO),hazardCollection);
    }

    @Test
    public void setter_setLastInspectionDateTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("lastInspectionDate");
        field.setAccessible(true);
        cargoPOJO.setLastInspectionDate(lastInspectionDate);
        assertEquals(field.get(cargoPOJO),lastInspectionDate);
    }

    @Test
    public void setter_setStorageDurationTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDuration");
        field.setAccessible(true);
        cargoPOJO.setStorageDuration(storageDuration);
        assertEquals(field.get(cargoPOJO),storageDuration);
    }

    @Test
    public void setter_setValueTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("value");
        field.setAccessible(true);
        cargoPOJO.setValue(bigDecimal);
        assertEquals(field.get(cargoPOJO),bigDecimal);
    }

    @Test
    public void setter_setHasStorageDateBeenSetTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("hasStorageDateBeenSet");
        field.setAccessible(true);
        cargoPOJO.setHasStorageDateBeenSet(hasStorageDateBeenSet);
        assertEquals(field.get(cargoPOJO),hasStorageDateBeenSet);
    }

    @Test
    public void setter_setStorageDateTest() throws IllegalAccessException, NoSuchFieldException {
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDate");
        field.setAccessible(true);
        cargoPOJO.setStorageDate(localDateTime);
        assertEquals(field.get(cargoPOJO),localDateTime);
    }


    @Test
    public void getter_getOwnerTest() throws NoSuchFieldException, IllegalAccessException {
        Customer owner = new CustomerImpl("Test");
        final Field field = cargoPOJO.getClass().getDeclaredField("owner");
        field.setAccessible(true);
        field.set(cargoPOJO,owner);
        final Customer actual = cargoPOJO.getOwner();
        assertEquals(owner,actual);
    }

    @Test
    public void getter_getHazardTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cargoPOJO.getClass().getDeclaredField("hazard");
        field.setAccessible(true);
        field.set(cargoPOJO,hazardCollection);
        final Collection<Hazard> actual = cargoPOJO.getHazard();
        assertEquals(hazardCollection,actual);
    }

    @Test
    public void getter_getLastInspectionDateTest() throws NoSuchFieldException, IllegalAccessException {
        Date date = new Date();
        final Field field = cargoPOJO.getClass().getDeclaredField("lastInspectionDate");
        field.setAccessible(true);
        field.set(cargoPOJO,date);
        final Date actual = cargoPOJO.getLastInspectionDate();
        assertEquals(date,actual);
    }

    @Test
    public void getter_getStorageDurationTest() throws NoSuchFieldException, IllegalAccessException {
        Duration duration = Duration.ofSeconds(123);
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDuration");
        field.setAccessible(true);
        field.set(cargoPOJO,duration);
        final Duration actual = cargoPOJO.getStorageDuration();
        assertEquals(duration,actual);
    }

    @Test
    public void getter_getValueTest() throws NoSuchFieldException, IllegalAccessException {
        BigDecimal bd = new BigDecimal(123);
        final Field field = cargoPOJO.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(cargoPOJO,bd);
        final BigDecimal actual = cargoPOJO.getValue();
        assertEquals(actual,bd);
    }

    @Test
    public void getter_isHasStorageDateBeenSetest() throws NoSuchFieldException, IllegalAccessException {
        boolean setDate = true;
        final Field field = cargoPOJO.getClass().getDeclaredField("hasStorageDateBeenSet");
        field.setAccessible(true);
        field.set(cargoPOJO,setDate);
        final boolean actual = cargoPOJO.isHasStorageDateBeenSet();
        assertEquals(actual,setDate);
    }

    @Test
    public void getter_getStorageDateTest() throws NoSuchFieldException, IllegalAccessException {
        LocalDateTime storageDate = LocalDateTime.now();
        final Field field = cargoPOJO.getClass().getDeclaredField("storageDate");
        field.setAccessible(true);
        field.set(cargoPOJO,storageDate);
        final LocalDateTime actual = cargoPOJO.getStorageDate();
        assertEquals(actual,storageDate);
    }

    @Test
    public void getter_isPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        boolean isPressurized = false;
        final Field field = cargoPOJO.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        field.set(cargoPOJO,isPressurized);
        final boolean actual = cargoPOJO.isPressurized();
        assertEquals(actual,isPressurized);
    }

    @Test
    public void getter_isFragileTest() throws NoSuchFieldException, IllegalAccessException {
        boolean isFragile = false;
        final Field field = cargoPOJO.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        field.set(cargoPOJO,isFragile);
        final boolean actual = cargoPOJO.isFragile();
        assertEquals(actual,isFragile);
    }

    @Test
    public void getter_getCargoTypTest() throws NoSuchFieldException, IllegalAccessException {
        String cargoTyp = "UnitisedCargoImpl";
        final Field field = cargoPOJO.getClass().getDeclaredField("cargoTyp");
        field.setAccessible(true);
        field.set(cargoPOJO,cargoTyp);
        final String actual = cargoPOJO.getCargoTyp();
        assertEquals(actual,cargoTyp);
    }

    @Test
    public void valueToStringToBigDecimalTest2(){
        String bigDecimalString = "12";
        BigDecimal expected = BigDecimal.valueOf(12);
        assertEquals(expected,cargoPOJO.valueStringToBigDecimal(bigDecimalString));
    }



    @Test
    public void createPOJOTest(){
        UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer,bd,storageDuration,hazardCollection,lastInspectionDate,isFragile);
        LiquidBulkCargoImpl liquidBulkCargo = new LiquidBulkCargoImpl(customer,bigDecimal,storageDuration,hazards,lastInspectionDate,isPressurized);
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargoLiquidBulkAndUnitised = new MixedCargoLiquidBulkAndUnitisedImpl(customer,bigDecimal,storageDuration,hazards,lastInspectionDate,isPressurized,isFragile);
        unitisedCargo.setStorageDate(localDateTime);
        liquidBulkCargo.setStorageDate(localDateTime);
        mixedCargoLiquidBulkAndUnitised.setStorageDate(localDateTime);
        Collection<CargoImpl> cargoCollection = new ArrayList<>();
        cargoCollection.add(unitisedCargo);
        cargoCollection.add(liquidBulkCargo);
        cargoCollection.add(mixedCargoLiquidBulkAndUnitised);

        Collection<CargoPOJO> collection = cargoPOJO.createPOJO(cargoCollection);
        assertEquals(3,collection.size());
    }

    @Test
    public void createCargoImplTest(){
        CargoPOJO cargoPOJO1 = new CargoPOJO(owner,valueStr,storageDurationStr,hazardCollection,lastInspectionDate,storageDateString,isPressurized,isFragile,cargoTyp);
        CargoPOJO cargoPOJO2 = new CargoPOJO(owner,valueStr,storageDurationStr,hazardCollection,lastInspectionDate,storageDateString,isPressurized,isFragile,"LiquidBulkCargoImpl");
        CargoPOJO cargoPOJO3 = new CargoPOJO(owner,valueStr,storageDurationStr,hazardCollection,lastInspectionDate,storageDateString,isPressurized,isFragile,"MixedLiquidBulkAndUnitisedImpl");
        Collection<CargoPOJO> cargoPOJOCollection = new ArrayList<>();
        cargoPOJOCollection.add(cargoPOJO1);
        cargoPOJOCollection.add(cargoPOJO2);
        cargoPOJOCollection.add(cargoPOJO3);

        Collection<CargoImpl> cargoCollection = cargoPOJO.createCargoImpl(cargoPOJOCollection);
        assertEquals(3,cargoCollection.size());
    }

}
