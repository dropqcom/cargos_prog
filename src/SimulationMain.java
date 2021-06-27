
import simulation.Outsourcer;
import simulation.Storer;

import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.StorageImpl;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SimulationMain {


    public static void main(String[] args) throws StorageToSmallException {

        //random Customers
        Customer customer1 = new CustomerImpl("Erik");
        Customer customer2 = new CustomerImpl("Sally");
        Customer customer3 = new CustomerImpl("Michelle");
        Customer customer4 = new CustomerImpl("Vincent");

        //for random cargos
        BigDecimal bd1 = new BigDecimal(23);
        BigDecimal bd2 = new BigDecimal(45);
        BigDecimal bd3 = new BigDecimal(66);
        BigDecimal bd4 = new BigDecimal(34);

        Duration duration1 = Duration.ofSeconds(2222);
        Duration duration2 = Duration.ofSeconds(2435);
        Duration duration3 = Duration.ofSeconds(5634);
        Duration duration4 = Duration.ofSeconds(43634);

        Collection<Hazard> hazard1 = new ArrayList<>();
        Collection<Hazard> hazard2 = new ArrayList<>();
        Collection<Hazard> hazard3 = new ArrayList<>();

        hazard1.add(Hazard.explosive);
        hazard2.add(Hazard.flammable);
        hazard2.add(Hazard.radioactive);

        Date date = new Date();
        boolean fragileFalse = false;
        boolean fragileTrue = true;
        boolean pressurizedTrue = true;
        boolean pressurizedFalse = false;

        //random cargos
        LiquidBulkCargoImpl lbc1 = new LiquidBulkCargoImpl(customer1, bd2, duration1, hazard1, date, pressurizedTrue);
        LiquidBulkCargoImpl lbc2 = new LiquidBulkCargoImpl(customer2, bd3, duration2, hazard1, date, pressurizedFalse);
        LiquidBulkCargoImpl lbc3 = new LiquidBulkCargoImpl(customer2, bd2, duration1, hazard1, date, pressurizedTrue);
        LiquidBulkCargoImpl lbc4 = new LiquidBulkCargoImpl(customer4, bd1, duration4, hazard3, date, pressurizedTrue);

        UnitisedCargoImpl uc1 = new UnitisedCargoImpl(customer2, bd3, duration3, hazard2, date, fragileFalse);
        UnitisedCargoImpl uc2 = new UnitisedCargoImpl(customer3, bd2, duration2, hazard3, date, fragileTrue);
        UnitisedCargoImpl uc3 = new UnitisedCargoImpl(customer4, bd3, duration1, hazard3, date, fragileFalse);
        UnitisedCargoImpl uc4 = new UnitisedCargoImpl(customer4, bd2, duration3, hazard3, date, fragileTrue);

        MixedCargoLiquidBulkAndUnitisedImpl mixedCargo1 = new MixedCargoLiquidBulkAndUnitisedImpl(customer4, bd3, duration4, hazard3, date, pressurizedTrue, fragileTrue);
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargo2 = new MixedCargoLiquidBulkAndUnitisedImpl(customer2, bd2, duration4, hazard2, date, pressurizedTrue, fragileFalse);
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargo3 = new MixedCargoLiquidBulkAndUnitisedImpl(customer1, bd1, duration4, hazard1, date, pressurizedFalse, fragileTrue);
        MixedCargoLiquidBulkAndUnitisedImpl mixedCargo4 = new MixedCargoLiquidBulkAndUnitisedImpl(customer4, bd2, duration3, hazard1, date, pressurizedTrue, fragileTrue);

        ArrayList<CargoImpl> cargoList = new ArrayList<>();
        cargoList.add(lbc1);
        cargoList.add(lbc2);
        cargoList.add(lbc3);
        cargoList.add(lbc4);
        cargoList.add(uc1);
        cargoList.add(uc2);
        cargoList.add(uc3);
        cargoList.add(uc4);
        cargoList.add(mixedCargo1);
        cargoList.add(mixedCargo2);
        cargoList.add(mixedCargo3);
        cargoList.add(mixedCargo4);

        StorageImpl storage1 = new StorageImpl(100);
        StorageImpl storage2 = new StorageImpl(80);
        StorageImpl storage3 = new StorageImpl(45);

        CustomerManagementImpl management = new CustomerManagementImpl();

        Object monitor = new Object();


        Storer storer1 = new Storer(storage1, storage2, storage3, management, cargoList, "EinlagerungsThread1",monitor);
        Storer storer2 = new Storer(storage1, storage2, storage3, management, cargoList, "EinlagerungsThread2",monitor);

        Outsourcer outsourcer1 = new Outsourcer(storage1, storage2, storage3, management, "AuslagerungsThread1 (Storage1)",monitor);
        Outsourcer outsourcer2 = new Outsourcer(storage2, storage1, storage3, management, "AuslagerungsThread2 (Storage2)",monitor);
        Outsourcer outsourcer3 = new Outsourcer(storage3, storage1, storage2, management, "Auslagerungsthread3 (Storage3)",monitor);

        System.out.println("Storage Size: s[1] = " + storage1.getStorageSize());
        System.out.println("Storage Size: s[2] = " + storage2.getStorageSize());
        System.out.println("Storage Size: s[3] = " + storage3.getStorageSize());
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Threads starting in 2 sec.");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        storer1.start();
        storer2.start();
        outsourcer1.start();
        outsourcer2.start();
        outsourcer3.start();

        try {
            storer1.join();
            storer2.join();
            outsourcer1.join();
            outsourcer2.join();
            outsourcer3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
