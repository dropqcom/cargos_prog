import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.cargo.Hazard;
import storageContract.cargo.UnitisedCargo;
import storageContract.cargo.UnitisedCargoImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UnitisedCargoTest {
    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private boolean fragile = false;



    UnitisedCargoImpl cargo;

    @BeforeEach
    public void init(){
        cargo = new UnitisedCargoImpl(customer,bd,d,hazards,date,fragile);
    }

    @Test
    public void isFragileTest(){
        assertFalse(cargo.isFragile());
    }

    @Test
    public void setFragileTest(){
        cargo.setFragile(true);
        assertTrue(cargo.isFragile());
    }


    //Quelle: https://stackoverflow.com/questions/21354311/junit-test-of-setters-and-getters-of-instance-variables
    @Test
    public void real_setFragileTest() throws NoSuchFieldException, IllegalAccessException {
        //when
        cargo.setFragile(false);

        //then
        final Field field = cargo.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        assertFalse(field.getBoolean(cargo));
    }

    @Test
    public void real_isFragileTest() throws NoSuchFieldException, IllegalAccessException {
        //given
        final Field field = cargo.getClass().getDeclaredField("isFragile");
        field.setAccessible(true);
        field.set(cargo,false);

        //when
        final boolean actual = cargo.isFragile();
        assertFalse(actual);
    }

}
