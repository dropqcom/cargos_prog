package events.showAllCargoEvent;

import events.listener.ShowAllCargoEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import log.LogTyp;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class ShowAllCargoEventListenerImpl implements ShowAllCargoEventListener {

    private Storage storage;
    private CustomerManagement management;

    public ShowAllCargoEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }



    private PrintEventHandler printEventHandler;

    public void setPrintEventHandler(PrintEventHandler printEventHandler){
        this.printEventHandler = printEventHandler;
    }

    public void print(String message) {
        PrintEvent printEvent = new PrintEvent(this, message);
        if (this.printEventHandler != null) {
            this.printEventHandler.handle(printEvent);
        }
    }

    private LogEventHandler logEventHandler;
    public void setLogEventHandler(LogEventHandler logEventHandler){
        this.logEventHandler = logEventHandler;
    }


    @Override
    public void onShowAllCargoEvent(ShowAllCargoEvent event) {
        if(null != event.getCargoStr()){
            if(0 == storage.getHowManyCargo()){
                this.print("No cargo currently stored");
            }else{
                boolean isFragile = false;
                boolean isPressurized = false;
                for(int i = 0; i < storage.getStorageSize(); i++){
                    if(null != storage.getCargo(i)){
                        if(storage.getCargo(i).getClass().equals(UnitisedCargoImpl.class)){
                            UnitisedCargoImpl cargo = (UnitisedCargoImpl) storage.getCargo(i);
                            isFragile = cargo.isFragile();
                        } else if(storage.getCargo(i).getClass().equals(LiquidBulkCargoImpl.class)){
                            LiquidBulkCargoImpl cargo = (LiquidBulkCargoImpl) storage.getCargo(i);
                            isPressurized = cargo.isPressurized();
                        }else if(storage.getCargo(i).getClass().equals(MixedCargoLiquidBulkAndUnitisedImpl.class)){
                            MixedCargoLiquidBulkAndUnitisedImpl cargo = (MixedCargoLiquidBulkAndUnitisedImpl) storage.getCargo(i);
                            isFragile = cargo.isFragile();
                            isPressurized = cargo.isPressurized();
                        }

                        String customerName = storage.getCargo(i).getOwner().getName();
                        CargoImpl cargo = storage.getCargo(i);
                        String type = String.valueOf(cargo.getClass());
                        int storagePos = cargo.getStoragePosition();
                        LocalDateTime storageDate = cargo.getStorageDate();
                        Date lastInspectionDate = cargo.getLastInspectionDate();
                        BigDecimal value = cargo.getValue();
                        Duration duration = cargo.getStorageDuration();

                        if(storage.getCargo(i).getClass().equals(UnitisedCargoImpl.class)){
                            this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: UnitisedCargo\nDate of storage:"+storageDate+"\nLast Inspection:"+lastInspectionDate+"\nFragile: "+isFragile+"\nValue: "+value+"\nDuration: "+duration+"\n");
                        } else if(storage.getCargo(i).getClass().equals(LiquidBulkCargoImpl.class)){
                            this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: LiquidBulkCargo\nDate of storage:"+storageDate+"\nLast Inspection:"+lastInspectionDate+"\nPressurized: "+isPressurized+"\nValue: "+value+"\nDuration: "+duration+"\n");
                        }else if (storage.getCargo(i).getClass().equals(MixedCargoLiquidBulkAndUnitisedImpl.class)){
                            this.print("Customer: "+customerName+"\nPosition: "+storagePos+"\nCargo Typ: MixedLiquidBulkAndUnitised\nDate of storage:"+storageDate+"\nLast Inspection:"+lastInspectionDate+"\nFragile: "+isFragile+"\nPressurized: "+isPressurized+"\nValue: "+value+"\nDuration: "+duration+"\n");
                        }
                    }
                }
            }
            LogEvent logEvent = new LogEvent(this, LogTyp.SHOW_CARGO);
            logEventHandler.handle2(logEvent);
        }
    }
}
