package serialization;

import com.sun.corba.se.impl.naming.pcosnaming.PersistentBindingIterator;
import org.junit.jupiter.api.Test;
import persistence.Persistence;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.StorageImpl;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;
import storageContract.cargo.UnitisedCargoImpl;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SerializationTest {



    @Test
    public void serializeJOSTest() throws IOException {
        ObjectOutput oos = mock(ObjectOutput.class);
        Collection<CargoImpl> cargoCollection = new ArrayList<>();
        cargoCollection.add(mock(CargoImpl.class));
        cargoCollection.add(mock(CargoImpl.class));
        Persistence.serialize(oos,cargoCollection);
        try {
            verify(oos).writeObject(any());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void deserializeJOSTest() throws IOException {
        ObjectInput oi = mock(ObjectInput.class);
        try{
            Collection<CargoImpl> items = Persistence.deserialize(oi);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        try {
            verify(oi).readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void serialize_deserializeTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = new CustomerImpl("Erik");
        CustomerManagementImpl management = new CustomerManagementImpl();
        StorageImpl storage = new StorageImpl(2);
        CargoImpl cargo = new CargoImpl(customer, null,null,null,null);
        management.addCargo(customer,cargo,storage);
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        File file = mock(File.class);
        when(file.getName()).thenReturn("test.ser");
        Persistence.serialize(file.getName(),allCargo);
        management.deleteCargo(0,storage);
        assertNotNull(Persistence.deserialize(file.getName()));
    }

    @Test
    public void returnNullDeserializeTest(){
        assertNull(Persistence.deserialize("nonExisting"));
    }

    @Test
    public void serialize_deserializeJBPTest() throws StorageToSmallException, CustomerDoesNotExistException, StorageIsFullException {
        CustomerImpl customer = new CustomerImpl("Erik");
        CustomerManagementImpl management = new CustomerManagementImpl();
        StorageImpl storage = new StorageImpl(2);
        BigDecimal bigDecimal = BigDecimal.valueOf(12.1);
        Duration duration = Duration.ofSeconds(12);
        Collection<Hazard> hazardCollection = new ArrayList<>();
        Date lastInspectionDate = new Date();
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(customer, bigDecimal,duration,hazardCollection,lastInspectionDate,true);
        cargo.setStorageDate(null);
        management.addCargo(customer,cargo,storage);
        File file = mock(File.class);
        when(file.getName()).thenReturn("test.xml");
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        Persistence.encoderJBP(file.getName(),allCargo);
        storage.clear();
        Collection<CargoImpl> retrievedCargo = Persistence.decoderJBP(file.getName());
        assertEquals(allCargo.size(),retrievedCargo.size());
    }






}
