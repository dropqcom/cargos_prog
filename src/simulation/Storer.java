package simulation;

import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.StorageImpl;
import storageContract.cargo.CargoImpl;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Storer extends Thread {

    private String thread_name;

    private StorageImpl storage1;
    private StorageImpl storage2;
    private StorageImpl storage3;

    private CustomerManagementImpl management;

    private Object monitor = new Object();

    ArrayList<CargoImpl> cargoList = new ArrayList<>();

    public Storer(StorageImpl storage1, StorageImpl storage2, StorageImpl storage3, CustomerManagementImpl management, ArrayList<CargoImpl> cargoList, String thread_name, Object monitor) {
        this.storage1 = storage1;
        this.storage2 = storage2;
        this.storage3 = storage3;
        this.management = management;
        this.cargoList = cargoList;
        this.thread_name = thread_name;
        this.monitor = monitor;
    }


    public void run() {
        System.out.println(thread_name + " starting...");
            while (true) {
                synchronized (monitor){
                    //choose random Storage
                    int randStorage = ThreadLocalRandom.current().nextInt(0, 3) + 1;
                    StorageImpl randomStorage = null;
                    switch (randStorage) {
                        case 1:
                            randomStorage = storage1;
                            break;
                        case 2:
                            randomStorage = storage2;
                            break;
                        case 3:
                            randomStorage = storage3;
                            break;
                        default:
                            System.err.println("Problem happened while choosing a random storage");
                            break;
                    }
                    assert randomStorage != null;
                    if (!randomStorage.checkIfStorageIsFull()) {
                        //choose random Cargo
                        int randCargo = ThreadLocalRandom.current().nextInt(0, 12);
                        CargoImpl randomCargo = cargoList.get(randCargo);
                        try {
                            management.addCustomer(randomCargo.getOwner()); ///geändert
                            management.addCargo(randomCargo.getOwner(), randomCargo, randomStorage);
                            System.out.println("Cargo added by " + thread_name + " Cargo = " + randomCargo +"\n" +randomStorage.getHowManyCargo() + " cargo(s) in S[" + randStorage+"]" );
                            this.monitor.notifyAll();
                        } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                            e.printStackTrace();
                        }
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


}
