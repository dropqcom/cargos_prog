package persistence;

import storageContract.administration.Customer;
import storageContract.cargo.*;

import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Persistence {

    public static void serialize(String filename, Collection<CargoImpl> cargoToSerialize){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            serialize(oos,cargoToSerialize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialize(ObjectOutput objectOutput, Collection<CargoImpl> cargoCollection) throws IOException {
        objectOutput.writeObject(cargoCollection);
    }

    public static Collection<CargoImpl> deserialize(String filename){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return deserialize(ois);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<CargoImpl> deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (Collection<CargoImpl>) objectInput.readObject();
    }




    public static void encoderJBP(String filename, Collection<CargoImpl> cargoToEncode){
        CargoPOJO cargoPOJO = new CargoPOJO();
        Collection<CargoPOJO> POJOCollection = cargoPOJO.createPOJO(cargoToEncode);

        try(XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))){
            encoder.writeObject(POJOCollection);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Collection<CargoImpl> decoderJBP(String filename){
        CargoPOJO cargoPOJO = new CargoPOJO();
        try(XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))){
            Collection<CargoPOJO> cargoPOJOCollection = (Collection<CargoPOJO>) decoder.readObject();
            Collection<CargoImpl> cargoCollection = cargoPOJO.createCargoImpl(cargoPOJOCollection);
            return cargoCollection;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
