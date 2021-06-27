package observer;

import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.administration.StorageImpl;
import storageContract.cargo.Hazard;

import java.util.ArrayList;
import java.util.Collection;

public class ObserverHazardImpl implements Observer {

    private StorageImpl storage;
    private Collection<Hazard> oldState_Hazard;

    public ObserverHazardImpl(StorageImpl storage){
        this.storage = storage;
        this.oldState_Hazard = new ArrayList<>();
    }



    @Override
    public void update() {
        Collection<Hazard> newState = storage.getHazardOfStorage();
        ArrayList<Hazard> hazards = new ArrayList<>(newState);
        ArrayList<Hazard> oldState = new ArrayList<>(oldState_Hazard);

        //hazard has been remove
        if(oldState.size() > newState.size() || oldState.size() < newState.size()){
            System.out.println("[Observer] Hazards in storage changed");
        }
        this.oldState_Hazard = newState;
    }


}
