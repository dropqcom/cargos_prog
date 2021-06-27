package storageContract.administration;

import storageContract.Exceptions.*;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerManagement {

    /**
     * adds a customer in the management (hashMap)
     * @param customer customer we want to add
     */
    void addCustomer(Customer customer);

    /**
     * adds a cargo to the correct customer in the management
     * and adds the cargo to the storage
     * @param customer customer who wants to add a cargo
     * @param cargo cargo to be addded
     * @param storage were the cargo is stored
     */
    void addCargo(Customer customer, CargoImpl cargo, Storage storage) throws StorageIsFullException, CustomerDoesNotExistException;

    /**
     * get all customer and their number of cargo
     * @return hashMap with Customer as key and number of cargo as value
     * @throws NullPointerException their could be no customer and cargo currently stored in management
     */
    Map<Customer,Integer> getCustomerAndNumberOfCargo() throws NullPointerException;

    /**
     * deletes a Customer and his cargo
     * cargo also has to be deleted in storage
     * @param customer customer we want to delete from management
     * @param storage the cargo linked to customer has to be deleted too
     */
    void delete(Customer customer, Storage storage) throws CustomerDoesNotExistException;

    /**
     * deletes a cargo at a certain position
     * cargo must be deleted in management and storage
     * @param position position of cargo we want to delete
     * @param storage delete the cargo in storage
     * @throws IndexOutOfBoundsException position could be bigger than storage size
     */
    void deleteCargo(int position, Storage storage) throws IndexOutOfBoundsException;

    /**
     * set the inspection date
     * @param position on this cargo at this position
     * @param storage in storage
     * @throws NullPointerException cargo at position could be null
     * @throws IndexOutOfBoundsException position could be index out of bounds
     */
    void setInspectionDate(int position, Storage storage) throws NullPointerException, IndexOutOfBoundsException;

    /**
     * clear management
     */
    void clear();

    /**
     * get amount of customer in management
     * @return number of customer
     */
    int getNumberOfCustomer();

    /**
     * get the cargo of a specific customer
     * @param customerName which cargo we want
     * @return cargo of customer
     * @throws CustomerDoesNotExistException customer could not exist
     */
    List<Cargo> getCargoFromCustomer(String customerName) throws CustomerDoesNotExistException;

    /**
     * method to check if a customer is in management
     * @param customerName which is checked
     * @return true if customer is stored in management, false otherwise
     */
    boolean checkIfCustomerExist(String customerName);

    /**
     * gets the customer
     * @param name name of customer we want to find
     * @return customer with name
     * @throws CustomerDoesNotExistException customer could not exist
     */
    Customer getCustomer(String name) throws CustomerDoesNotExistException;

}
