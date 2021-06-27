package storageContract.administration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import observer.Observer;

import storageContract.Exceptions.StorageToSmallException;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;


import java.util.*;

public class StorageImpl implements Storage {

    private Cargo[] storage;

    public StorageImpl(int size) throws StorageToSmallException {
        if (size <= 0) {
            throw new StorageToSmallException("Storage must be bigger than 0");
        }
        this.storage = new Cargo[size];
    }


    @Override
    public int getStorageSize() {
        return this.storage.length;
    }

    @Override
    public void add(CargoImpl cargo, int freeSpace) {
        storage[freeSpace] = cargo;
        this.inform();
    }

    @Override
    public void delete(Customer customer) {
        for (int i = 0; i < storage.length; i++) {
            if(null != storage[i]){
                if(storage[i].getOwner().getName().equals(customer.getName())){
                    storage[i] = null;
                }
            }
        }
        this.inform();
    }

    @Override
    public void delete(int position) {
        storage[position] = null;         //hab ich jetzt geändert um deletecargo zu richten...
        this.inform();
    }

    @Override
    public int freeSpaceInStorage() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public synchronized boolean checkIfStorageIsFull() {
        int counter = 0;
        for (Cargo cargo : this.storage) {
            if (cargo != null) {
                counter++;
            }
        }
        return counter == this.storage.length;
    }

    @Override
    public int getPositionOfCargo(CargoImpl cargo) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == cargo) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Customer getOwnerOfCargo(int position) {
        return storage[position].getOwner();
    }

    @Override
    public CargoImpl getCargo(int position) throws IndexOutOfBoundsException {
        if (position > storage.length || position < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (CargoImpl) storage[position];
    }

    @Override
    public ObservableList<Cargo> getStorageCargos() {
        ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
        for (int i = 0; i < this.storage.length; i++) {
            if (null != this.storage[i]) {
                cargoList.add(storage[i]);
            }
        }
        return cargoList;
    }

    @Override
    public Collection<CargoImpl> getAllCargo() {
        Collection<CargoImpl> allCargo = new ArrayList<>();
        for (int i = 0; i < getStorageSize(); i++) {
            if (null != this.storage[i]) {
                allCargo.add((CargoImpl) storage[i]);
            }
        }
        return allCargo;
    }

    @Override
    public int getHowManyCargo() {
        int counter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (null != storage[i]) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, null);
    }


    //------------------------------------------------------------------------------
    //--------------------------------------observation-----------------------------
    private List<Observer> observerList = new LinkedList<>();

    @Override
    public List<Observer> getObserverList() {
        return this.observerList;
    }

    @Override
    public void signIn(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void signOut(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void inform() {
        for (Observer observer : this.observerList) {
            observer.update();
        }
    }

    private int state_storage_size;
    private Collection<Hazard> state_hazard;

    public int getState_storage_size() {
        int counter = 0;
        for (Cargo cargo : storage) {
            if (null != cargo) {
                counter++;
            }
            state_storage_size = counter;
        }
        return state_storage_size;
    }



    // shows duplicated values
    @Override
    public Collection<Hazard> getHazardOfStorage()  {
        Collection<Hazard> hazardCollection = new ArrayList<>();
        for (Cargo cargo : storage) {
            if(cargo != null){
                if(null != cargo.getHazards()){
                    Collection<Hazard> h = cargo.getHazards();
                    if (h.contains(Hazard.explosive)) {
                        hazardCollection.add(Hazard.explosive);
                    }
                    if (h.contains(Hazard.flammable)) {
                        hazardCollection.add(Hazard.flammable);
                    }
                    if (h.contains(Hazard.toxic)) {
                        hazardCollection.add(Hazard.toxic);
                    }
                    if (h.contains(Hazard.radioactive)){
                        hazardCollection.add(Hazard.radioactive);
                    }
                }
            }
        }
        return new ArrayList<>(hazardCollection);
    }

    //doesn't show duplicated values
    @Override
    public Collection<Hazard> getNonHazardOfStorage(){
        Collection<Hazard> nonHazardCollection = new ArrayList<>();
        Collection<Hazard> hazardCollection = getHazardOfStorage();
        ArrayList<Hazard> arrayList = new ArrayList<>(hazardCollection);
        if(!arrayList.contains(Hazard.explosive)){
            nonHazardCollection.add(Hazard.explosive);
        }
        if(!arrayList.contains(Hazard.flammable)){
            nonHazardCollection.add(Hazard.flammable);
        }
        if(!arrayList.contains(Hazard.toxic)){
            nonHazardCollection.add(Hazard.toxic);
        }
        if(!arrayList.contains(Hazard.radioactive)){
            nonHazardCollection.add(Hazard.radioactive);
        }

        return new ArrayList<>(nonHazardCollection);
    }



}
