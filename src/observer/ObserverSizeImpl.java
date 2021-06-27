package observer;

import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.administration.StorageImpl;

public class ObserverSizeImpl implements Observer{

    private StorageImpl storage;
    private int oldState_storage_size;

    public ObserverSizeImpl(StorageImpl storage){
        this.storage = storage;
        this.oldState_storage_size = this.storage.getState_storage_size();
    }



    @Override
    public void update() {
        int newState_storage_size = storage.getState_storage_size();
        if(newState_storage_size + 1 == storage.getStorageSize()){
            System.out.println("[Observer] Storage almost full, only 1 free space left");
            this.oldState_storage_size = newState_storage_size;
        }
    }
}
