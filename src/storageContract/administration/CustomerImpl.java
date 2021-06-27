package storageContract.administration;

import com.sun.xml.internal.ws.developer.Serialization;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;

public class CustomerImpl implements Customer, Serializable {
    private String name;

    public CustomerImpl(String name) {
        this.name = name;
    }

    public CustomerImpl(){}

    @Override
    public String getName() {
        return this.name;
    }


    //---------------------------------- NOT IMPLEMENTED YET -------------------------------------------

//    @Override
//    public BigDecimal getMaxValue() {
//        return null;
//    }
//    @Override
//    public Duration getMaxDurationOfStorage() {
//        return null;
//    }

    //-----------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
