import org.junit.jupiter.api.Test;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    public void initTest(){
        String NAME = "Eric";
        Customer customer = new CustomerImpl(NAME);
        assertEquals(NAME,customer.getName());
    }

    @Test
    public void toStringTest(){
        String NAME = "Sally";
        Customer customer = new CustomerImpl(NAME);
        assertEquals("Sally",customer.toString());
    }

    @Test
    public void setNameTest(){
        Customer customer = new CustomerImpl("ABC");
        customer.setName("Mann");
        assertEquals("Mann",customer.getName());
    }

    @Test
    public void defaultConstructorTest(){
        Customer customer = new CustomerImpl();
        assertNotNull(customer);
    }




}