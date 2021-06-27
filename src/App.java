import cli.CommandLine;
import events.DeserializationJBPEvent.DeserializationJBPEventHandler;
import events.DeserializationJBPEvent.DeserializationJBPEventListenerImpl;
import events.addCargoEvent.AddCargoEventHandler;
import events.addListenerEvent.AddListenerEventHandler;
import events.addListenerEvent.AddListenerEventListenerImpl;
import events.addObserverEvent.AddObserverEventHandler;
import events.addObserverEvent.AddObserverEventListenerImpl;
import events.deserializationJOSEvent.DeserializationJOSEventHandler;
import events.deserializationJOSEvent.DeserializationJOSEventListenerImpl;
import events.getNonHazardEvent.GetNonHazardEventHandler;
import events.getNonHazardEvent.GetNonHazardEventListenerImpl;
import events.listener.*;
import events.addCargoEvent.AddCargoEventListenerImpl;
import events.addCustomerEvent.AddCustomerEventHandler;
import events.addCustomerEvent.AddCustomerEventListenerImpl;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventHandler;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventListenerImpl;
import events.deleteCustomerEvent.DeleteCustomerEventHandler;
import events.deleteCustomerEvent.DeleteCustomerEventListenerImpl;
import events.getHazardEvent.GetHazardEventHandler;
import events.getHazardEvent.GetHazardEventListenerImpl;
import events.logEvent.LogEventHandler;
import events.printEvent.PrintEventHandler;
import events.removeObserverEvent.RemoveObserverEventHandler;
import events.removeObserverEvent.RemoveObserverEventListenerImpl;
import events.serializationJBPEvent.SerializationJBPEventHandler;
import events.serializationJBPEvent.SerializationJBPEventListenerImpl;
import events.serializationJOSEvent.SerializationJOSEventHandler;
import events.serializationJOSEvent.SerializationJOSEventListenerImpl;
import events.setInspectionDateEvent.SetInspectionDateEventHandler;
import events.setInspectionDateEvent.SetInspectionDateEventListenerImpl;
import events.showAllCargoEvent.ShowAllCargoEventHandler;
import events.showAllCargoEvent.ShowAllCargoEventListenerImpl;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventHandler;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventListenerImpl;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventHandler;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventListenerImpl;
import log.Logger;
import log.log_ressources.LogStringsEnglish;
import observer.ObserverHazardImpl;
import observer.ObserverSizeImpl;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.StorageImpl;
import view.View;

import java.io.IOException;
import java.util.Scanner;

public class App {


    public static void main(String[] args) throws IOException, StorageToSmallException {
        int storageSize = 4;


        if(args.length == 1){
            if(isNumeric(args[0])){
                storageSize = Integer.parseInt(args[0]);
            }else{
                String input = "a";
                Scanner scanner = new Scanner(System.in);
                while(!isNumeric(input)){
                    System.out.print("Only numeric values allowed!\nType in the storage size: ");
                    input = scanner.next();
                }
            }
        } else if(args.length == 2){
            if(isNumeric(args[0])){
                storageSize = Integer.parseInt(args[0]);
            }else{
                String input = "a";
                Scanner scanner = new Scanner(System.in);
                while(!isNumeric(input)){
                    System.out.print("Only numeric values allowed!\nType in the storage size: ");
                    input = scanner.next();
                }
            }
        } else if (args.length == 3){
            //not implemented yet
        }



        CustomerManagementImpl management = new CustomerManagementImpl();
        CommandLine cli = new CommandLine();
        StorageImpl storage = new StorageImpl(storageSize);





        AddCustomerEventHandler addCustomerHandler = new AddCustomerEventHandler();
        AddCustomerEventListenerImpl addCustomerListener = new AddCustomerEventListenerImpl(storage,management);

        ShowCustomersAndCargosEventHandler showCustomersAndCargosEventHandler = new ShowCustomersAndCargosEventHandler();
        ShowCustomersAndCargosEventListenerImpl showCustomersAndCargosEventListener = new ShowCustomersAndCargosEventListenerImpl(storage,management);

        DeleteCustomerEventHandler deleteCustomerEventHandler = new DeleteCustomerEventHandler();
        DeleteCustomerEventListenerImpl deleteCustomerEventListener = new DeleteCustomerEventListenerImpl(storage,management);

        DeleteCargoAtPositionEventHandler deleteCargoAtPositionEventHandler = new DeleteCargoAtPositionEventHandler();
        DeleteCargoAtPositionEventListenerImpl deleteCargoAtPositionEventListener = new DeleteCargoAtPositionEventListenerImpl(storage,management);

        AddCargoEventHandler addCargoEventHandler = new AddCargoEventHandler();
        AddCargoEventListenerImpl addCargoEventListener = new AddCargoEventListenerImpl(storage,management);

        ShowAllCargoEventHandler showAllCargoEventHandler = new ShowAllCargoEventHandler();
        ShowAllCargoEventListenerImpl showAllCargoEventListener = new ShowAllCargoEventListenerImpl(storage,management);

        GetHazardEventHandler getHazardEventHandler = new GetHazardEventHandler();
        GetHazardEventListenerImpl getHazardEventListener = new GetHazardEventListenerImpl(storage,management);

        SetInspectionDateEventHandler setInspectionDateEventHandler = new SetInspectionDateEventHandler();
        SetInspectionDateEventListenerImpl setInspectionDateEventListener = new SetInspectionDateEventListenerImpl(storage,management);

        SerializationJOSEventHandler serializationJOSEventHandler = new SerializationJOSEventHandler();
        SerializationJOSEventListenerImpl serializationJOSEventListener = new SerializationJOSEventListenerImpl(storage,management);

        DeserializationJOSEventHandler deserializationJOSEventHandler = new DeserializationJOSEventHandler();
        DeserializationJOSEventListenerImpl deserializationJOSEventListener = new DeserializationJOSEventListenerImpl(storage,management);

        SerializationJBPEventHandler serializationJBPEventHandler = new SerializationJBPEventHandler();
        SerializationJBPEventListenerImpl serializationJBPEventListener = new SerializationJBPEventListenerImpl(storage,management);

        DeserializationJBPEventHandler deserializationJBPEventHandler = new DeserializationJBPEventHandler();
        DeserializationJBPEventListenerImpl deserializationJBPEventListener = new DeserializationJBPEventListenerImpl(storage,management);

        RemoveObserverEventHandler removeObserverEventHandler = new RemoveObserverEventHandler();
        RemoveObserverEventListenerImpl removeObserverEventListener = new RemoveObserverEventListenerImpl(storage,management);

        AddObserverEventHandler addObserverEventHandler = new AddObserverEventHandler();
        AddObserverEventListenerImpl addObserverEventListener = new AddObserverEventListenerImpl(storage,management);

        AddListenerEventHandler addListenerEventHandler = new AddListenerEventHandler();
        AddListenerEventListenerImpl addListenerEventListener = new AddListenerEventListenerImpl(storage,management);

        GetNonHazardEventHandler getNonHazardEventHandler = new GetNonHazardEventHandler();
        GetNonHazardEventListenerImpl getNonHazardEventListener = new GetNonHazardEventListenerImpl(storage,management);

        ShowSpecificCargoTypEventHandler showSpecificCargoTypEventHandler = new ShowSpecificCargoTypEventHandler();
        ShowSpecificCargoTypEventListenerImpl showSpecificCargoTypEventListener = new ShowSpecificCargoTypEventListenerImpl(storage,management);

        PrintEventHandler printEventHandler = new PrintEventHandler();
        PrintEventListener printEventListener = new View();

        printEventHandler.add(printEventListener);
        cli.setPrintEventHandler(printEventHandler);
        addCustomerListener.setPrintEventHandler(printEventHandler);
        addCargoEventListener.setPrintEventHandler(printEventHandler);
        deleteCustomerEventListener.setPrintEventHandler(printEventHandler);
        deleteCargoAtPositionEventListener.setPrintEventHandler(printEventHandler);
        showCustomersAndCargosEventListener.setPrintEventHandler(printEventHandler);
        showAllCargoEventListener.setPrintEventHandler(printEventHandler);
        getNonHazardEventListener.setPrintEventHandler(printEventHandler);
        getHazardEventListener.setPrintEventHandler(printEventHandler);
        showSpecificCargoTypEventListener.setPrintEventHandler(printEventHandler);
        setInspectionDateEventListener.setPrintEventHandler(printEventHandler);
        deserializationJOSEventListener.setPrintEventHandler(printEventHandler);



        setInspectionDateEventHandler.add(setInspectionDateEventListener);
        cli.setSetInspectionDateEventHandler(setInspectionDateEventHandler);

        addCustomerHandler.add(addCustomerListener);
        cli.setAddCustomerHandler(addCustomerHandler);

        showCustomersAndCargosEventHandler.add(showCustomersAndCargosEventListener);
        cli.setShowCustomersHandler(showCustomersAndCargosEventHandler);

        deleteCustomerEventHandler.add(deleteCustomerEventListener);
        cli.setDeleteCustomerEventHandler(deleteCustomerEventHandler);

        deleteCargoAtPositionEventHandler.add(deleteCargoAtPositionEventListener);
        cli.setDeleteCargoAtPositionEventHandler(deleteCargoAtPositionEventHandler);

        addCargoEventHandler.add(addCargoEventListener);
        cli.setAddCargoEventHandler(addCargoEventHandler);

        showAllCargoEventHandler.add(showAllCargoEventListener);
        cli.setShowAllCargoEventHandler(showAllCargoEventHandler);

        getHazardEventHandler.add(getHazardEventListener);
        cli.setGetHazardEventHandler(getHazardEventHandler);

        serializationJOSEventHandler.add(serializationJOSEventListener);
        cli.setSerializationJOSEventHandler(serializationJOSEventHandler);

        deserializationJOSEventHandler.add(deserializationJOSEventListener);
        cli.setDeserializationJOSEventHandler(deserializationJOSEventHandler);

        serializationJBPEventHandler.add(serializationJBPEventListener);
        cli.setSerializationJBPEventHandler(serializationJBPEventHandler);

        deserializationJBPEventHandler.add(deserializationJBPEventListener);
        cli.setDeserializationJBPEventHandler(deserializationJBPEventHandler);

        removeObserverEventHandler.add(removeObserverEventListener);
        cli.setRemoveObserverEventHandler(removeObserverEventHandler);

        addObserverEventHandler.add(addObserverEventListener);
        cli.setAddObserverEventHandler(addObserverEventHandler);

        addListenerEventHandler.add(addListenerEventListener);
        cli.setAddListenerEventHandler(addListenerEventHandler);

        getNonHazardEventHandler.add(getNonHazardEventListener);
        cli.setGetNonHazardEventHandler(getNonHazardEventHandler);

        showSpecificCargoTypEventHandler.add(showSpecificCargoTypEventListener);
        cli.setShowSpecificCargoTypEventHandler(showSpecificCargoTypEventHandler);

        //------------------------- Observer -------------------------------
        ObserverSizeImpl observerSize = new ObserverSizeImpl(storage);
        ObserverHazardImpl observerHazard = new ObserverHazardImpl(storage);
        storage.signIn(observerHazard);
        storage.signIn(observerSize);
        //------------------------------------------------------------------


        if(args.length <= 1){
            LogStringsEnglish logStringsEnglish = new LogStringsEnglish();
            LogEventHandler logEventHandler = new LogEventHandler();
            Logger logger = new Logger(null,false,logStringsEnglish);
            logEventHandler.addListener(logger);
            addCargoEventListener.setLogEventHandler(logEventHandler);
            addCustomerListener.setLogEventHandler(logEventHandler);
            addListenerEventListener.setLogEventHandler(logEventHandler);
            addObserverEventListener.setLogEventHandler(logEventHandler);
            deleteCargoAtPositionEventListener.setLogEventHandler(logEventHandler);
            deleteCustomerEventListener.setLogEventHandler(logEventHandler);
            deserializationJBPEventListener.setLogEventHandler(logEventHandler);
            deserializationJOSEventListener.setLogEventHandler(logEventHandler);
            getHazardEventListener.setLogEventHandler(logEventHandler);
            getNonHazardEventListener.setLogEventHandler(logEventHandler);
            removeObserverEventListener.setLogEventHandler(logEventHandler);
            serializationJBPEventListener.setLogEventHandler(logEventHandler);
            serializationJOSEventListener.setLogEventHandler(logEventHandler);
            setInspectionDateEventListener.setLogEventHandler(logEventHandler);
            showAllCargoEventListener.setLogEventHandler(logEventHandler);
            showCustomersAndCargosEventListener.setLogEventHandler(logEventHandler);
            showSpecificCargoTypEventListener.setLogEventHandler(logEventHandler);
        } else if(args.length == 2){
            //default when not a language is specified
            LogStringsEnglish logStringsGerman = new LogStringsEnglish();

            String file_name = args[1];
            LogEventHandler logEventHandler = new LogEventHandler();
            Logger logger = new Logger(file_name,true,logStringsGerman);
            logEventHandler.addListener(logger);
            addCargoEventListener.setLogEventHandler(logEventHandler);
            addCustomerListener.setLogEventHandler(logEventHandler);
            addListenerEventListener.setLogEventHandler(logEventHandler);
            addObserverEventListener.setLogEventHandler(logEventHandler);
            deleteCargoAtPositionEventListener.setLogEventHandler(logEventHandler);
            deleteCustomerEventListener.setLogEventHandler(logEventHandler);
            deserializationJBPEventListener.setLogEventHandler(logEventHandler);
            deserializationJOSEventListener.setLogEventHandler(logEventHandler);
            getHazardEventListener.setLogEventHandler(logEventHandler);
            getNonHazardEventListener.setLogEventHandler(logEventHandler);
            removeObserverEventListener.setLogEventHandler(logEventHandler);
            serializationJBPEventListener.setLogEventHandler(logEventHandler);
            serializationJOSEventListener.setLogEventHandler(logEventHandler);
            setInspectionDateEventListener.setLogEventHandler(logEventHandler);
            showAllCargoEventListener.setLogEventHandler(logEventHandler);
            showCustomersAndCargosEventListener.setLogEventHandler(logEventHandler);
            showSpecificCargoTypEventListener.setLogEventHandler(logEventHandler);
        } else if (args.length == 3){
            //not implemented yet different language
        }


//        //----
//        Controller controller = new Controller();
//        controller.setDeleteCargoAtPositionEventHandler(deleteCargoAtPositionEventHandler);




        cli.start(System.in,System.out);
    }

    private static boolean isNumeric(String s){
        return s != null && s.matches("[+]?\\d*\\d+");
    }


}
