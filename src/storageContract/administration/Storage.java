package storageContract.administration;

import javafx.collections.ObservableList;
import observer.Observer;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Storage {

    /**
     * gets the max storage size (how many cargo can be stored)
     *
     * @return capacity of the storage
     */
    int getStorageSize();


    /**
     * gets all the existing hazard in storage
     *
     * @return returns an array of max size = 4 because only 4 hazard exists
     * if a specific hazard exists it will be stored only once in the array
     * even if the hazard exists more than one time in the storage
     */
    Collection<Hazard> getHazardOfStorage();

    /**
     * gets all the non existing hazard in storage
     * @return returns an array of max size = 4 because only 4 hazard exists
     */
    Collection<Hazard> getNonHazardOfStorage();


    /**
     * add cargo in storage
     *
     * @param cargo     cargo we want to add
     * @param freeSpace at position were we have space
     */
    void add(CargoImpl cargo, int freeSpace);

    /**
     * clears the storage
     */
    void clear();

    /**
     * deletes the cargo from a specific customer
     *
     * @param customer customer which cargo we want to delete
     */
    void delete(Customer customer);

    /**
     * deletes a cargo at a certain position
     *
     * @param position index at which the cargo we want to delete is
     */
    void delete(int position);

    /**
     * check at which index we can store cargo
     *
     * @return returns the free index
     * returns -1 if storage is full
     */
    int freeSpaceInStorage();

    /**
     * checks if storage is full
     *
     * @return true if full, false otherwise
     */
    boolean checkIfStorageIsFull();

    /**
     * gets the index (position) on which the specific cargo is stored
     * -1 when not found
     *
     * @param cargo cargo we want to find
     * @return index (position)
     */
    int getPositionOfCargo(CargoImpl cargo);


    /**
     * get owner of cargo at certain position
     * @param position of cargo
     * @return owner
     */
    Customer getOwnerOfCargo(int position);

    /**
     * get the cargo from storage at a certain position
     * @param position of cargo
     * @return cargo
     * @throws IndexOutOfBoundsException position could be negative or bigger than storage size
     */
    CargoImpl getCargo(int position) throws IndexOutOfBoundsException;


    ObservableList<Cargo> getStorageCargos();

    Collection<CargoImpl> getAllCargo();

    int getHowManyCargo();













    /**
     * -----------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------
     * ---------------------------------- Wird beobachtet --------------------------------------------
     * -----------------------------------------------------------------------------------------------
     * -----------------------------------------------------------------------------------------------
     */

    List<Observer> getObserverList();
    void signIn(Observer observer);
    void signOut(Observer observer);
    void inform();





}
