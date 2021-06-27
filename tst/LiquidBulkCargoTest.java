import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;

import storageContract.cargo.Hazard;
import storageContract.cargo.LiquidBulkCargo;
import storageContract.cargo.LiquidBulkCargoImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class LiquidBulkCargoTest {

    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private boolean pressurized = true;

    private LiquidBulkCargoImpl cargo;

    @BeforeEach
    public void init(){
        cargo = new LiquidBulkCargoImpl(customer, bd, d, hazards, date, pressurized);
    }

    @Test
    public void isPressurizedTest() {
        assertTrue(cargo.isPressurized());
    }



    @Test
    public void setIsPressurizedTest(){
        cargo.setPressurized(false);
        assertFalse(cargo.isPressurized());
    }

    @Test
    public void setter_isPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        cargo.setPressurized(true);
        final Field field = cargo.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        assertTrue(field.getBoolean(cargo));
    }

    @Test
    public void getter_isPressurizedTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cargo.getClass().getDeclaredField("isPressurized");
        field.setAccessible(true);
        field.set(cargo,false);
        final boolean actual = cargo.isPressurized();
        assertFalse(actual);
    }

}