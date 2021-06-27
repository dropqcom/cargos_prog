package events.showSpecificCargoTypEvent;

import events.listener.ShowSpecificCargoTypEventListener;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.LiquidBulkCargoImpl;
import storageContract.cargo.MixedCargoLiquidBulkAndUnitisedImpl;
import storageContract.cargo.UnitisedCargoImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ShowSpecificCargoTypEventListenerImpl implements ShowSpecificCargoTypEventListener {

    private Storage storage;
    private CustomerManagement management;

    private final String UNITISED = "Unitised";
    private final String LIQUID = "LiquidBulk";
    private final String MIXED = "MixedLiquidBulkAndUnitised";

    public ShowSpecificCargoTypEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    //PrintEvent-----------------------------------------------------------------------------
    private PrintEventHandler printEventHandler;

    public void setPrintEventHandler(PrintEventHandler printEventHandler) {
        this.printEventHandler = printEventHandler;
    }

    public void print(String message) {
        PrintEvent printEvent = new PrintEvent(this, message);
        if (this.printEventHandler != null) {
            this.printEventHandler.handle(printEvent);
        }
    }
    //---------------------------------------------------------------------------------------

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }

    @Override
    public void onShowSpecificCargoTypEvent(ShowSpecificCargoTypEvent event) {
        Collection<CargoImpl> cargoCollection = new ArrayList<>();
        if(null != event.getCargoTyp()){
            String cargoTyp = event.getCargoTyp();
            switch (cargoTyp){
                case LIQUID:
                    printLiquidCargo(storage);
                    String message = "User want to see LiquidBulkCargo in Storage";
                    LogEventUserInput logEventUserInput = new LogEventUserInput(this,message);
                    logEventHandler.handle(logEventUserInput);
                    break;

                case UNITISED:
                    printUnitisedCargo(storage);
                    String message2 = "User want to see UnitisedCargo in Storage";
                    LogEventUserInput logEventUserInput1 = new LogEventUserInput(this,message2);
                    logEventHandler.handle(logEventUserInput1);
                    break;

                case MIXED:
                    printMixedCargo(storage);
                    String message3 = "User want to see MixedLiquidBulkAndUnitisedCargo in storage";
                    LogEventUserInput logEventUserInput2 = new LogEventUserInput(this,message3);
                    logEventHandler.handle(logEventUserInput2);
                    break;

                default:
                    this.print("Check your input, unknown cargo typ\nAllowed cargo typ: Unitised, LiquidBulk, MixedLiquidBulkAndUnitised");
                    break;
            }
        }
    }


    private void printLiquidCargo(Storage storage){
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        ArrayList<CargoImpl> allCargoArrayList = new ArrayList<>(allCargo);
        for(int i = 0; i < allCargoArrayList.size(); i++){
            if(allCargoArrayList.get(i).getClass().equals(LiquidBulkCargoImpl.class)){
                LiquidBulkCargoImpl cargo = (LiquidBulkCargoImpl) allCargoArrayList.get(i);
                String customerName = cargo.getOwner().getName();
                int storagePos = cargo.getStoragePosition();
                LocalDateTime storageDate = cargo.getStorageDate();
                Date lastInspectionDate = cargo.getLastInspectionDate();
                boolean isPressurized = cargo.isPressurized();
                BigDecimal value = cargo.getValue();
                Duration duration = cargo.getStorageDuration();
                this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: LiquidBulkCargo\nDate of storage:"+storageDate+
                        "\nLast Inspection:"+lastInspectionDate+"\nPressurized: "+isPressurized+"\nValue: "+value+"\nDuration: "+duration + "\n");
            }
        }
    }
    private void printUnitisedCargo(Storage storage){
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        ArrayList<CargoImpl> allCargoArrayList = new ArrayList<>(allCargo);
        for(int i = 0; i < allCargoArrayList.size(); i++){
            if(allCargoArrayList.get(i).getClass().equals(UnitisedCargoImpl.class)){
                UnitisedCargoImpl cargo = (UnitisedCargoImpl) allCargoArrayList.get(i);
                String customerName = cargo.getOwner().getName();
                int storagePos = cargo.getStoragePosition();
                LocalDateTime storageDate = cargo.getStorageDate();
                Date lastInspectionDate = cargo.getLastInspectionDate();
                boolean isFragile = cargo.isFragile();
                BigDecimal value = cargo.getValue();
                Duration duration = cargo.getStorageDuration();
                this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: UnitisedCargo\nDate of storage:"+storageDate+
                        "\nLast Inspection:"+lastInspectionDate+"\nFragile: "+isFragile+"\nValue: "+value+"\nDuration: "+duration + "\n");

            }
        }
    }
    private void printMixedCargo(Storage storage){
        Collection<CargoImpl> allCargoCargo = storage.getAllCargo();
        ArrayList<CargoImpl> allCargoArrayList = new ArrayList<>(allCargoCargo);
        for(int i = 0; i < allCargoArrayList.size(); i++){
            if(allCargoArrayList.get(i).getClass().equals(MixedCargoLiquidBulkAndUnitisedImpl.class)){
                MixedCargoLiquidBulkAndUnitisedImpl cargo = (MixedCargoLiquidBulkAndUnitisedImpl) allCargoArrayList.get(i);
                String customerName = cargo.getOwner().getName();
                int storagePos = cargo.getStoragePosition();
                LocalDateTime storageDate = cargo.getStorageDate();
                Date lastInspectionDate = cargo.getLastInspectionDate();
                boolean isPressurized = cargo.isPressurized();
                boolean isFragile = cargo.isFragile();
                BigDecimal value = cargo.getValue();
                Duration duration = cargo.getStorageDuration();
                this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: MixedLiquidBulkAndUnitised\nDate of storage:"+storageDate+
                        "\nLast Inspection:"+lastInspectionDate+"\nFragile: "+isFragile+"\nPressurized: "
                        +isPressurized+"\nValue: "+value+"\nDuration: "+duration + "\n");
            }
        }
    }




}
