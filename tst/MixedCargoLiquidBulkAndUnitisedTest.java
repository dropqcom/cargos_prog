import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.cargo.Hazard;
import storageContract.cargo.MixedCargoLiquidBulkAndUnitised;
import storageContract.cargo.MixedCargoLiquidBulkAndUnitisedImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MixedCargoLiquidBulkAndUnitisedTest {

    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private boolean pressurized = true;
    private boolean fragile = false;
    private MixedCargoLiquidBulkAndUnitisedImpl cargo;

    @BeforeEach
    public void init(){
        cargo = new MixedCargoLiquidBulkAndUnitisedImpl(customer,bd,d,hazards,date,pressurized,false);
    }

    @Test
    public void isPressurizedTest(){
        assertTrue(cargo.isPressurized());
    }


    @Test
    public void isFragileTest(){
        assertFalse(cargo.isFragile());
    }


    @Test
    public void setter_isPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        cargo.setPressurized(false);
        final Field field = cargo.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        assertFalse(field.getBoolean(cargo));
    }

    @Test
    public void getter_isPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cargo.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        field.set(cargo,true);

        final boolean actual = cargo.isPressurized();
        assertTrue(actual);
    }

    @Test
    public void setter_isFragileTest() throws NoSuchFieldException, IllegalAccessException {
        cargo.setFragile(true);
        final Field field = cargo.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        assertTrue(field.getBoolean(cargo));
    }

    @Test
    public void getter_isFragileTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cargo.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        field.set(cargo,true);

        final boolean actual = cargo.isFragile();
        assertTrue(actual);
    }
}
