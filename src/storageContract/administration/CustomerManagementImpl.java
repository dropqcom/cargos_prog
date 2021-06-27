package storageContract.administration;

import storageContract.Exceptions.*;
import storageContract.cargo.Cargo;
import storageContract.cargo.CargoImpl;

import java.time.LocalDateTime;
import java.util.*;

public class CustomerManagementImpl implements CustomerManagement {
    private HashMap<Customer, ArrayList<Cargo>> management;

    public CustomerManagementImpl() {
        this.management = new HashMap<>();
    }



    @Override
    public void addCustomer(Customer customer) {
        if (!checkIfCustomerExist(customer.getName())) {
            management.put(customer, new ArrayList<Cargo>());
        }
    }

    @Override
    public synchronized void addCargo(Customer customer, CargoImpl cargo, Storage storage) throws StorageIsFullException, CustomerDoesNotExistException {
        if (storage.checkIfStorageIsFull()) {
            throw new StorageIsFullException("Storage is full");
        }
        //add to management
        if (checkIfCustomerExist(customer.getName())) {
            ArrayList<Cargo> cargoFromCustomer = management.get(getCustomer(customer.getName()));
//            if (null == cargoFromCustomer) {
//                cargoFromCustomer = new ArrayList<>();
//            } else {
//                cargoFromCustomer = management.get(getCustomer(customer.getName()));
//            }

            cargoFromCustomer.add(cargo);
            //management.replace(getCustomer(customer.getName()),cargoFromCustomer);
            management.put(getCustomer(customer.getName()),cargoFromCustomer);

            //add to storage
            int freeSpace = storage.freeSpaceInStorage();
            if (freeSpace != -1) {
                storage.add(cargo, freeSpace);
                cargo.setStoragePosition(freeSpace);
                //set Storage Date if hasStorageDateBeenSet = false
                if(!cargo.isHasStorageDateBeenSet()){
                    LocalDateTime dateOfStorage = LocalDateTime.now();
                    cargo.setStorageDate(dateOfStorage);
                    cargo.setHasStorageDateBeenSet(true);
                }
            }
        }
    }

    @Override
    public void delete(Customer customer, Storage storage) throws CustomerDoesNotExistException {
        if (!checkIfCustomerExist(customer.getName())) {
            throw new CustomerDoesNotExistException("Customer does not exist");
        }
        storage.delete(customer);
        this.management.remove(customer);
    }


    @Override
    public synchronized void deleteCargo(int position, Storage storage) throws IndexOutOfBoundsException {
        if ((position > storage.getStorageSize()) || (position < 0)) {
            throw new IndexOutOfBoundsException("Position > storage size or position < 0");
        }else{
            //delete in management
            try {

//                ArrayList<Cargo> cargoList = (ArrayList<Cargo>) getCargoFromCustomer(storage.getOwnerOfCargo(position).getName());
//                try {
//                    cargoList.remove(storage.getCargo(position));
//                } catch (NullPointerException e) {
//                    System.err.println("No cargo at that position");
//                }
//                delete(storage.getOwnerOfCargo(position),storage);
//                management.put(storage.getOwnerOfCargo(position), cargoList);

                storage.getCargo(position).setHasStorageDateBeenSet(false);
                List<Cargo> list = getCargoFromCustomer(storage.getOwnerOfCargo(position).getName());
                //du musst die alte arraylist vom customer löschen :r customer problem....
                //oder update mit compute
                //this.management.remove(storage.getOwnerOfCargo(position));

                //ArrayList<Cargo> oldList = new ArrayList<>(list);
                ArrayList<Cargo> cargoList = new ArrayList<>(list);
                cargoList.remove(storage.getCargo(position));
                //this.management.remove(storage.getOwnerOfCargo(position),oldList);
                //this.management.put(storage.getCargo(position).getOwner(),cargoList);

                //delete customer
                //delete(customer,storage);

                //new
                //management.replace(storage.getOwnerOfCargo(position),oldList,cargoList);

                management.put(storage.getOwnerOfCargo(position), cargoList);
                //delete in storage
                storage.delete(position);

            } catch (NullPointerException | CustomerDoesNotExistException e) {
                System.err.println("Customer or cargo does not exist at that position");
            }
        }
    }

    @Override
    public Map<Customer, Integer> getCustomerAndNumberOfCargo() throws NullPointerException {


//
//        HashMap<Customer, Integer> customerAndNumber = new HashMap<>();
//
//        for (Customer customer : management.keySet()) {
//            int numberOfCargo = management.get(customer).size();
//            customerAndNumber.put(customer, numberOfCargo);
//        }
//        return customerAndNumber;
//
//
//
//
        Map<Customer, Integer> customerAndNumber = new HashMap<>();
        for (Customer customer : management.keySet()) {
            ArrayList<Cargo> cargoArrayList = management.get(customer);
            int counter = 0;
            if(null != cargoArrayList){
                for(int i = 0; i < cargoArrayList.size(); i++){
                    if(null != cargoArrayList.get(i)){
                        counter++;
                    }
                }
                customerAndNumber.put(customer,counter);
            }

            //falsch size zählt auch null werte und bei management.remove
            //int numberOfCargo = management.get(customer).size();
            //customerAndNumber.put(customer, numberOfCargo);
        }
        return new HashMap<>(customerAndNumber);
    }


    @Override
    public void setInspectionDate(int position, Storage storage) throws NullPointerException {
        if (position > storage.getStorageSize() || position < 0) {
            throw new IndexOutOfBoundsException("Position > storage size or position < 0");
        }
        if (storage.getCargo(position) == null) {
            throw new NullPointerException("Cargo does not exist");
        }
        //LocalDateTime now = LocalDateTime.now();
        Date now = new Date();
        CargoImpl cargo = storage.getCargo(position);
        cargo.setLastInspectionDate(now);
    }

    @Override
    public void clear() {
        management.clear();
    }

    @Override
    public int getNumberOfCustomer() {
        return this.management.size();
    }


    @Override
    public List<Cargo> getCargoFromCustomer(String customerName) throws CustomerDoesNotExistException {
        List<Cargo> cargoList = new ArrayList<>();
        if (!checkIfCustomerExist(customerName)) {
            throw new CustomerDoesNotExistException("Customer does not exist");
        }else{
            if(null != management.get(getCustomer(customerName))){
                cargoList = management.get(getCustomer(customerName));
            }
            return cargoList;
        }
    }

    @Override
    public boolean checkIfCustomerExist(String customerName) {
        for (Customer key : management.keySet()) {
            if (key.getName().equals(customerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Customer getCustomer(String name) throws CustomerDoesNotExistException {
        for (Customer key : management.keySet()) {
            if (key.getName().equals(name)) {
                return key;
            }
        }
        throw new CustomerDoesNotExistException("Customer does not exist");
    }


}
