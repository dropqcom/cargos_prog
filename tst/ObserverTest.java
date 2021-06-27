import observer.ObserverHazardImpl;
import observer.ObserverSizeImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.*;
import storageContract.cargo.Hazard;
import storageContract.cargo.LiquidBulkCargoImpl;
import storageContract.cargo.UnitisedCargoImpl;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class ObserverTest {

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


    private CustomerManagementImpl management;
    private StorageImpl storage;


    @BeforeEach
    public void init() throws StorageToSmallException {
        management = new CustomerManagementImpl();
        storage = new StorageImpl(3);
    }

    @AfterEach
    public void clear(){
        management.clear();
        storage.clear();
    }

    @Test
    public void observerSizeConstructorTest(){
        ObserverSizeImpl observerSize = new ObserverSizeImpl(storage);
        assertNotNull(observerSize);
    }

    @Test
    public void ObserverHazardConstructorTest(){
        ObserverHazardImpl observerHazard = new ObserverHazardImpl(storage);
        assertNotNull(observerHazard);
    }




    @Test
    public void observerSizeTest1(){
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        LiquidBulkCargoImpl cargo2 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        ObserverSizeImpl observerSize = new ObserverSizeImpl(storage);
        storage.signIn(observerSize);

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        storage.add(cargo,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());

        assertEquals("[Observer] Storage almost full, only 1 free space left\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void observerHazardTest1(){

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        hazards.add(Hazard.flammable);
        hazards.add(Hazard.radioactive);
        hazards2.add(Hazard.explosive);
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer1,bd,d,hazards,date,fragile);
        LiquidBulkCargoImpl cargo2 = new LiquidBulkCargoImpl(customer2,bd2,d2,hazards2,date2,pressurized2);
        ObserverHazardImpl observerHazard = new ObserverHazardImpl(storage);
        storage.signIn(observerHazard);
        storage.add(cargo,storage.freeSpaceInStorage());
        storage.add(cargo2,storage.freeSpaceInStorage());
        storage.delete(1);
        storage.add(cargo2,storage.freeSpaceInStorage());

        // no message should be displayed
        Collection<Hazard> hazards3 = new ArrayList<>();
        UnitisedCargoImpl cargo3 = new UnitisedCargoImpl(customer2,bd,d,hazards3,date2,false);
        storage.add(cargo3,storage.freeSpaceInStorage());

        assertEquals("[Observer] Hazards in storage changed\n" +
                "[Observer] Hazards in storage changed\n" +
                "[Observer] Hazards in storage changed\n" +
                 "[Observer] Hazards in storage changed\n", outContent.toString());

        System.setOut(originalOut);
    }



}
