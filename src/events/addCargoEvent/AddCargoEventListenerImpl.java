package events.addCargoEvent;


import events.listener.AddCargoEventListener;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.*;
import storageContract.cargo.*;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class AddCargoEventListenerImpl implements AddCargoEventListener {

    private Storage storage;
    private CustomerManagement management;


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




    public AddCargoEventListenerImpl(Storage storage, CustomerManagement management) {
        this.storage = storage;
        this.management = management;
    }

    private static boolean isNumeric(String s) {
        return s != null && s.matches("[+]?\\d*\\d+");

    }


    //Cargo Typ
    private boolean zero_argument_check(String zero_argument) {
        if (zero_argument.equals("UnitisedCargo")) {
            return true;
        } else if (zero_argument.equals("LiquidBulkCargo")) {
            return true;
        } else if (zero_argument.equals("MixedLiquidBulkAndUnitised")) {
            return true;
        } else {
            return false;
        }
    }


    private boolean first_argument_check(String first_argument){
        return management.checkIfCustomerExist(first_argument);
    }


    //Value
    private boolean second_argument_check(String second_argument) {
        if (second_argument.matches("[0-9]+[.][0-9]+")) {
            return true;
        }
        return false;
    }

    //Duration
    private boolean third_argument_check(String third_argument) {
        if (isNumeric(third_argument)) {
            return true;
        }
        return false;
    }

    //Hazard
    //die ist falsch -- jetzt richtig? nein toxic,,flammable wwird akzeptiert
    private boolean fourth_argument_check(String fourth_argument) {
        if(fourth_argument.equals(",")){
            return true;
        }else{
            String[] hazardStr = fourth_argument.split(",");
            for (String s : hazardStr){
                if(s.equals("flammable")){
                    return true;
                }else if(s.equals("toxic")){
                    return true;
                }else if(s.equals("radioactive")){
                    return true;
                }else if(s.equals("explosive")){
                    return true;
                }else{
                    return false;
                }
            }
        }
        //will not be reached
        return false;
    }

    private Collection<Hazard> getHazard(String fourth_argument) {
        String[] hazardStr = fourth_argument.split(",");
        Collection<Hazard> hazards = new ArrayList<>();
        for (String s : hazardStr) {
            switch (s) {
                case "flammable":
                    hazards.add(Hazard.flammable);
                    break;
                case "toxic":
                    hazards.add(Hazard.toxic);
                    break;
                case "radioactive":
                    hazards.add(Hazard.radioactive);
                    break;
                case "explosive":
                    hazards.add(Hazard.explosive);
                    break;
                default:
                    break;
            }
        }
        return hazards;
    }

    private UnitisedCargoImpl createUnitisedCargo(Customer customer, BigDecimal value, Duration duration, Collection<Hazard> hazards, String cargoTyp, String fifth_argument) {
        UnitisedCargoImpl cargo = null;
        String fragileStr = fifth_argument;
        boolean fragile;
        Date lastInspectionDate = new Date();
        if ((fragileStr.length() == 1) && (fragileStr.equals("y") || fragileStr.equals("n"))) {
            fragile = fragileStr.equals("y");
            cargo = new UnitisedCargoImpl(customer, value, duration, hazards, lastInspectionDate, fragile);
        } else {
            this.print("y or n allowed, string length > 1 detected");
            return null;
        }
        return cargo;
    }

    private LiquidBulkCargoImpl createLiquidBulkCargo(Customer customer, BigDecimal value, Duration duration, Collection<Hazard> hazards, String cargoTyp, String fifth_argument) {
        LiquidBulkCargoImpl cargo = null;
        String pressureStr = fifth_argument;
        boolean pressurized;
        Date lastInspectionDate = new Date();
        if ((pressureStr.length() == 1) && pressureStr.equals("y") || pressureStr.equals("n")) {
            pressurized = pressureStr.equals("y");
            cargo = new LiquidBulkCargoImpl(customer, value, duration, hazards, lastInspectionDate, pressurized);
        } else {
            this.print("y or n allowed, string length > 1 detected");
            return null;
        }
        return cargo;
    }

    private MixedCargoLiquidBulkAndUnitisedImpl createMixedCargo(Customer customer, BigDecimal value, Duration duration, Collection<Hazard> hazards, String cargoTyp, String fifth_argument, String sixth_argument) {
        String fragileStr = fifth_argument;
        String pressureStr = sixth_argument;
        boolean fragile = false;
        boolean pressurized = false;
        MixedCargoLiquidBulkAndUnitisedImpl cargo = null;
        Date lastInspectionDate = new Date();
        if ((pressureStr.length() == 1) && (pressureStr.equals("y") || pressureStr.equals("n"))) {
            pressurized = pressureStr.equals("y");
        } else {
            this.print("y or n allowed, string length > 1 detected");
            return null;
        }
        if ((fragileStr.length() == 1) && ((fragileStr.equals("y") || fragileStr.equals("n")))) {
            fragile = fragileStr.equals("y");
        } else {
            this.print("y or n allowed, string length > 1 detected");
            return null;
        }
        cargo = new MixedCargoLiquidBulkAndUnitisedImpl(customer, value, duration, hazards, lastInspectionDate, pressurized, fragile);
        return cargo;
    }


    @Override
    public void onAddCargoEvent(AddCargoEvent event) {
        BigDecimal value = null;
        Duration storageDuration = null;
        Customer customer = null;
        if (null != event.getCargoStr()) {
            String[] cargoStr = event.getCargoStr();
            if (cargoStr.length < 6) {
                this.print("Check your input, number of arguments seems wrong");
                return;
            } else {
                String cargoTyp = cargoStr[0];

                if (zero_argument_check(cargoTyp)) {
                    String customer_name = cargoStr[1];

                    if(first_argument_check(customer_name)){
                        customer = new CustomerImpl(customer_name);
                    }else{
                        this.print("Customer does not exist, create one");
                        return;
                    }

                    if (second_argument_check(cargoStr[2])) {
                        double val = Double.parseDouble(cargoStr[2]);
                        value = BigDecimal.valueOf(val);
                    } else if (isNumeric(cargoStr[2])) {
                        value = BigDecimal.valueOf(Long.parseLong(cargoStr[2]));
                    } else {
                        this.print("Wrong argument! Allowed [0-9]+ or [0-9]+[.][0-9]+");
                        return;
                    }

                    if (third_argument_check(cargoStr[3])) {
                        storageDuration = Duration.ofSeconds(Integer.parseInt(cargoStr[3]));
                    } else {
                        this.print("Duration must be numeric");
                        return;
                    }

                    Collection<Hazard> hazards = null;
                    if (fourth_argument_check(cargoStr[4])) {
                        hazards = getHazard(cargoStr[4]);
                    } else {
                        this.print("unknown hazard, allowed Hazard [flammable, explosive, toxic, radioactive]");
                        return;
                    }




                    switch (cargoTyp){
                        case "UnitisedCargo":
                            UnitisedCargoImpl cargo1 = createUnitisedCargo(customer,value,storageDuration,hazards,cargoTyp,cargoStr[5]);
                            try {
                                if(null != cargo1){
                                    this.management.addCargo(customer,cargo1,storage);
                                }else{
                                    break;
                                }
                            } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                                this.print("Storage is full");
                                break;
                            }
                            this.print("[INFO] the cargo from " + customer_name + " has been added at position " + storage.getPositionOfCargo(cargo1));

                            String message = "Cargo added : " + cargoTyp + " " + customer_name + " " + value + " " + storageDuration + " " + hazards.toString() + " " + cargoStr[5];
                            LogEventUserInput logEvent = new LogEventUserInput(this,message);
                            logEventHandler.handle(logEvent);

                            break;

                        case "LiquidBulkCargo":
                            LiquidBulkCargoImpl cargo2 = createLiquidBulkCargo(customer,value,storageDuration,hazards,cargoTyp,cargoStr[5]);
                            try {
                                if(null != cargo2){
                                    this.management.addCargo(customer,cargo2,storage);
                                }else{
                                    break;
                                }
                            } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                                this.print("Storage is full");
                                break;
                            }
                            this.print("[INFO] the cargo from " + customer_name + " has been added at position " + storage.getPositionOfCargo(cargo2));

                            String message2 = "Cargo added : " + cargoTyp + " " + customer_name + " " + value + " " + storageDuration + " " + hazards.toString() + " " + cargoStr[5];
                            LogEventUserInput logEvent2 = new LogEventUserInput(this,message2);
                            logEventHandler.handle(logEvent2);

                            break;

                        case "MixedLiquidBulkAndUnitised":
                            if(cargoStr.length < 7){
                                this.print("wrong argument number");
                                break;
                            }
                            MixedCargoLiquidBulkAndUnitisedImpl cargo3 = createMixedCargo(customer,value,storageDuration,hazards,cargoTyp,cargoStr[5],cargoStr[6]);
                            try {
                                if(null != cargo3){
                                    this.management.addCargo(customer,cargo3,storage);
                                }else{
                                    break;
                                }
                            } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                                this.print("Storage is full");
                                break;
                            }
                            this.print("[INFO] the cargo from " + customer_name + " has been added at position " + storage.getPositionOfCargo(cargo3));


                            String message3 = "Cargo added : " + cargoTyp + " " + customer_name + " " + value + " " + storageDuration + " " + hazards.toString() + " " + cargoStr[5] + " " + cargoStr[6];
                            LogEventUserInput logEvent3 = new LogEventUserInput(this,message3);
                            logEventHandler.handle(logEvent3);

                            break;

                        default:
                            break;
                    }


                } else {
                    this.print("Unknown cargo typ\nAllowed: UnitisedCargo, LiquidBulkCargo, MixedLiquidBulkAndUnitised");
                    return;
                }
            }
        }
    }
}
