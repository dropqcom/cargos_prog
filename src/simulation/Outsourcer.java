package simulation;

import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.Storage;
import storageContract.administration.StorageImpl;
import storageContract.cargo.CargoImpl;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


public class Outsourcer extends Thread {

    private String thread_name;

    private StorageImpl myStorage;
    private StorageImpl storage1;
    private StorageImpl storage2;

    private CustomerManagementImpl management;

    private Object monitor = new Object();

    public Outsourcer(StorageImpl myStorage, StorageImpl storage1, StorageImpl storage2, CustomerManagementImpl management, String thread_name, Object monitor) {
        this.myStorage = myStorage;
        this.storage1 = storage1;
        this.storage2 = storage2;
        this.management = management;
        this.thread_name = thread_name;
        this.monitor = monitor;
    }

    public void run() {
        System.out.println(thread_name + " is starting...");
            while (true) {
                synchronized (monitor){
                    if (myStorage.checkIfStorageIsFull()) {
                        //choose random Storage
                        int randStorage = ThreadLocalRandom.current().nextInt(0, 2);
                        StorageImpl randomStorage = null;
                        switch (randStorage) {
                            case 0:
                                randomStorage = storage1;
                                break;
                            case 1:
                                randomStorage = storage2;
                                break;
                            default:
                                System.err.println("Problem happened while choosing a random storage");
                                break;
                        }
                        try {
                            assert randomStorage != null;
                            if (!randomStorage.checkIfStorageIsFull()) {
                                CargoImpl oldestCargo = getOldestCargo(myStorage);
                                int position = getPositionOfOldestCargo(myStorage);
                                System.out.println(thread_name + " got the oldest cargo: " + oldestCargo);
                                management.addCustomer(oldestCargo.getOwner()); //geändert
                                management.addCargo(oldestCargo.getOwner(), oldestCargo, randomStorage);
                                System.out.println("Oldest cargo added to new Storage");
                                management.deleteCargo(position, myStorage);
                            }
                        } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                            e.printStackTrace();
                        }
                        this.monitor.notifyAll();
                    }else{
                        try {
                            this.monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }


    public static synchronized CargoImpl getOldestCargo(Storage storage) {
        CargoImpl oldestCargo = storage.getCargo(0);
        for (int i = 0; i < storage.getStorageSize(); i++) {
            if(null != storage.getCargo(i)){
                LocalDateTime oldestDate = oldestCargo.getStorageDate();
                LocalDateTime checkDate = storage.getCargo(i).getStorageDate();
                if (checkDate.isBefore(oldestDate)) {
                    oldestCargo = storage.getCargo(i);
                }
            }
        }
        return oldestCargo;
    }

    public static synchronized int getPositionOfOldestCargo(Storage storage) {
        CargoImpl oldestCargo = storage.getCargo(0);
        int position = 0;
        for (int i = 0; i < storage.getHowManyCargo(); i++) {
            LocalDateTime oldestDate = oldestCargo.getStorageDate();
            LocalDateTime checkDate = storage.getCargo(i).getStorageDate();
            if (checkDate.isBefore(oldestDate)) {
                oldestCargo = storage.getCargo(i);
                position = i;
            }
        }
        return position;
    }

}
