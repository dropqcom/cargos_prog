package cli;

import events.DeserializationJBPEvent.DeserializationJBPEvent;
import events.DeserializationJBPEvent.DeserializationJBPEventHandler;
import events.addCargoEvent.AddCargoEvent;
import events.addCargoEvent.AddCargoEventHandler;
import events.addCustomerEvent.AddCustomerEvent;
import events.addCustomerEvent.AddCustomerEventHandler;
import events.addListenerEvent.AddListenerEvent;
import events.addListenerEvent.AddListenerEventHandler;
import events.addObserverEvent.AddObserverEvent;
import events.addObserverEvent.AddObserverEventHandler;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEvent;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventHandler;
import events.deleteCustomerEvent.DeleteCustomerEvent;
import events.deleteCustomerEvent.DeleteCustomerEventHandler;
import events.deserializationJOSEvent.DeserializationJOSEvent;
import events.deserializationJOSEvent.DeserializationJOSEventHandler;
import events.getHazardEvent.GetHazardEvent;
import events.getHazardEvent.GetHazardEventHandler;
import events.getNonHazardEvent.GetNonHazardEvent;
import events.getNonHazardEvent.GetNonHazardEventHandler;
import events.listener.AddCargoEventListener;
import events.printEvent.PrintEvent;
import events.printEvent.PrintEventHandler;
import events.removeObserverEvent.RemoveObserverEvent;
import events.removeObserverEvent.RemoveObserverEventHandler;
import events.serializationJBPEvent.SerializationJBPEvent;
import events.serializationJBPEvent.SerializationJBPEventHandler;
import events.serializationJOSEvent.SerializationJOSEvent;
import events.serializationJOSEvent.SerializationJOSEventHandler;
import events.setInspectionDateEvent.SetInspectionDateEvent;
import events.setInspectionDateEvent.SetInspectionDateEventHandler;
import events.showAllCargoEvent.ShowAllCargoEvent;
import events.showAllCargoEvent.ShowAllCargoEventHandler;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEvent;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventHandler;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEvent;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventHandler;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;


public class CommandLine {

    //------------------------------ EVENTS ---------------------------------
    private AddCustomerEventHandler addCustomerHandler;

    public void setAddCustomerHandler(AddCustomerEventHandler addCustomerHandler) {
        this.addCustomerHandler = addCustomerHandler;
    }

    private ShowCustomersAndCargosEventHandler showCustomersHandler;

    public void setShowCustomersHandler(ShowCustomersAndCargosEventHandler showCustomersHandler) {
        this.showCustomersHandler = showCustomersHandler;
    }

    private DeleteCustomerEventHandler deleteCustomerEventHandler;

    public void setDeleteCustomerEventHandler(DeleteCustomerEventHandler deleteCustomerEventHandler) {
        this.deleteCustomerEventHandler = deleteCustomerEventHandler;
    }

    private DeleteCargoAtPositionEventHandler deleteCargoAtPositionEventHandler;

    public void setDeleteCargoAtPositionEventHandler(DeleteCargoAtPositionEventHandler deleteCargoAtPositionEventHandler) {
        this.deleteCargoAtPositionEventHandler = deleteCargoAtPositionEventHandler;
    }

    private AddCargoEventHandler addCargoEventHandler;

    public void setAddCargoEventHandler(AddCargoEventHandler addCargoEventHandler) {
        this.addCargoEventHandler = addCargoEventHandler;
    }

    private ShowAllCargoEventHandler showAllCargoEventHandler;

    public void setShowAllCargoEventHandler(ShowAllCargoEventHandler showAllCargoEventHandler) {
        this.showAllCargoEventHandler = showAllCargoEventHandler;
    }

    private GetHazardEventHandler getHazardEventHandler;

    public void setGetHazardEventHandler(GetHazardEventHandler getHazardEventHandler) {
        this.getHazardEventHandler = getHazardEventHandler;
    }

    private SetInspectionDateEventHandler setInspectionDateEventHandler;

    public void setSetInspectionDateEventHandler(SetInspectionDateEventHandler setInspectionDateEventHandler) {
        this.setInspectionDateEventHandler = setInspectionDateEventHandler;
    }

    private PrintEventHandler printEventHandler;

    public void setPrintEventHandler(PrintEventHandler printEventHandler) {
        this.printEventHandler = printEventHandler;
    }

    private SerializationJOSEventHandler serializationJOSEventHandler;

    public void setSerializationJOSEventHandler(SerializationJOSEventHandler serializationJOSEventHandler) {
        this.serializationJOSEventHandler = serializationJOSEventHandler;
    }

    private DeserializationJOSEventHandler deserializationJOSEventHandler;

    public void setDeserializationJOSEventHandler(DeserializationJOSEventHandler deserializationJOSEventHandler) {
        this.deserializationJOSEventHandler = deserializationJOSEventHandler;
    }

    private SerializationJBPEventHandler serializationJBPEventHandler;

    public void setSerializationJBPEventHandler(SerializationJBPEventHandler serializationJBPEventHandler) {
        this.serializationJBPEventHandler = serializationJBPEventHandler;
    }

    private DeserializationJBPEventHandler deserializationJBPEventHandler;

    public void setDeserializationJBPEventHandler(DeserializationJBPEventHandler deserializationJBPEventHandler) {
        this.deserializationJBPEventHandler = deserializationJBPEventHandler;
    }

    private RemoveObserverEventHandler removeObserverEventHandler;

    public void setRemoveObserverEventHandler(RemoveObserverEventHandler removeObserverEventHandler) {
        this.removeObserverEventHandler = removeObserverEventHandler;
    }

    private AddObserverEventHandler addObserverEventHandler;

    public void setAddObserverEventHandler(AddObserverEventHandler addObserverEventHandler) {
        this.addObserverEventHandler = addObserverEventHandler;
    }

    private AddListenerEventHandler addListenerEventHandler;

    public void setAddListenerEventHandler(AddListenerEventHandler addListenerEventHandler) {
        this.addListenerEventHandler = addListenerEventHandler;
    }

    private GetNonHazardEventHandler getNonHazardEventHandler;

    public void setGetNonHazardEventHandler(GetNonHazardEventHandler getNonHazardEventHandler) {
        this.getNonHazardEventHandler = getNonHazardEventHandler;
    }

    private ShowSpecificCargoTypEventHandler showSpecificCargoTypEventHandler;

    public void setShowSpecificCargoTypEventHandler(ShowSpecificCargoTypEventHandler showSpecificCargoTypEventHandler) {
        this.showSpecificCargoTypEventHandler = showSpecificCargoTypEventHandler;
    }


    //------------------------------------------------

    private final String INSERT = ":c";
    private final String DELETE = ":d";
    private final String SHOW = ":r";
    private final String CHANGE = ":u";
    private final String PERSISTENCE = ":p";
    private final String CONFIG = ":config";
    private final String NOTHING = "";
    private final String ERROR_MESSAGE = "Error unknown command";
    private final String EXIT = "exit";
    private final String MODES = "modes";
    private final String HELP = "help";

    private boolean keepGoing = true;
    private BufferedReader br;


    public void start(InputStream is, OutputStream os) throws IOException {
        PrintStream ps = new PrintStream(os);
        this.br = new BufferedReader(new InputStreamReader(is));
        showOptions();
        this.print("");
        while (keepGoing) {
            ps.print("> ");
            String command = br.readLine().trim();
            switch (command) {
                case EXIT:
                    this.print("[INFO] closing streams");
                    this.print("[INFO] Exiting program..");
                    br.close();
                    keepGoing = false;
                    terminate();
                    break;

                case INSERT:
                    this.print("[INFO] Entered Mode = insert");
                    insertMode();
                    break;

                case SHOW:
                    this.print("[INFO] Entered Mode = show");
                    showMode();
                    break;

                case DELETE:
                    this.print("[INFO] Entered Mode = delete");
                    deleteMode();
                    break;

                case CHANGE:
                    this.print("[INFO] Entered Mode = change");
                    changeMode();
                    break;

                case PERSISTENCE:
                    this.print("[INFO] Entered Mode = persistence");
                    persistenceMode();
                    break;

                case CONFIG:
                    this.print("[INFO] Entered Mode = configuration");
                    configurationMode();
                    break;

                case MODES:
                    showOptions();
                    break;

                case HELP:
                    help();
                    break;

                case NOTHING:
                    keepGoing = true;
                    break;


                default:
                    this.print(ERROR_MESSAGE);
                    keepGoing = true;          //hier auf true setzen wenn programm nicht beenden soll, probleme aber bei tests
                    break;

            }
        }

    }


    //String class_name useless, but would be needed if their would be more remove Listener options...
    private void configurationMode() throws IOException {
        this.print("[INFO] Allowed commands: add/remove ObserverSize");
        this.print("                         add/remove AddCargoEventListener");
        String input = br.readLine();
        String[] mode = input.split(" ");
        if (mode.length == 2) {
            String class_name = mode[1];
            switch (mode[0]) {
                case "add":
                    if (class_name.equals("ObserverSize")) {
                        addObserver(class_name);
                    } else if (class_name.equals("AddCargoEventListener")) {
                        addListener(class_name);
                    } else {
                        this.print("[ERROR] check your arguments. Only 'add CLASSNAME' or 'remove CLASSNAME' allowed.");
                        break;
                    }
                    break;

                case "remove":
                    if (class_name.equals("ObserverSize")) {
                        removeObserver(class_name);
                    } else if (class_name.equals("AddCargoEventListener")) {
                        removeListener(class_name);
                    } else {
                        this.print("[ERROR] check your arguments. Only 'add CLASSNAME' or 'remove CLASSNAME' allowed.");
                        break;
                    }
                    break;

                default:
                    break;
            }
        } else {
            this.print("[ERROR] check your arguments. Only 'add CLASSNAME' or 'remove CLASSNAME' allowed.");
        }
    }

    private void addObserver(String class_name) {
        AddObserverEvent event = new AddObserverEvent(this, class_name);
        addObserverEventHandler.handle(event);
    }

    private void removeObserver(String class_name) {
        RemoveObserverEvent event = new RemoveObserverEvent(this, class_name);
        removeObserverEventHandler.handle(event);
    }

    private void addListener(String class_name) {
        AddListenerEvent event = new AddListenerEvent(this, this.addCargoEventHandler);
        addListenerEventHandler.handle(event);
        this.print("[UPDATE] Listener for AddCargoEvent added. You can add cargo to storage!");
    }

    private void removeListener(String class_name) {
        List<AddCargoEventListener> list = addCargoEventHandler.getListenerList();
        if (null != list) {
            AddCargoEventListener listener = list.get(0);
            addCargoEventHandler.remove(listener);
            this.print("[UPDATE] Listener for AddCargoEvent removed.");
        }

    }

    private void persistenceMode() throws IOException {
        String mode = br.readLine();
        String[] extendedMode = mode.split(" ");
        if (extendedMode.length <= 1) {
            switch (mode) {
                case "saveJOS":
                    SerializationJOSEvent event = new SerializationJOSEvent(this);
                    serializationJOSEventHandler.handle(event);
                    this.print("[INFO] cargo have been saved to allCargo.ser");
                    break;

                case "loadJOS":
                    DeserializationJOSEvent event1 = new DeserializationJOSEvent(this);
                    deserializationJOSEventHandler.handle(event1);
                    this.print("[INFO] cargo has/have been loaded");
                    break;

                case "saveJBP":
                    SerializationJBPEvent event2 = new SerializationJBPEvent(this);
                    serializationJBPEventHandler.handle(event2);
                    this.print("[INFO] cargo have been saved to beanEncoded.xml");
                    break;

                case "loadJBP":
                    DeserializationJBPEvent event3 = new DeserializationJBPEvent(this);
                    deserializationJBPEventHandler.handle(event3);
                    this.print("[INFO] cargo has/have been loaded");
                    break;

                default:
                    this.print("[ERROR] check your input or type help for help");
                    break;
            }
        } else {
            StringTokenizer token = new StringTokenizer(mode);
            String firstWord = token.nextToken();
            if (token.hasMoreTokens()) {
                if (isNumeric(token.nextToken())) {
                    switch (firstWord) {
                        case "load":
                            // do your stuff;
                            break;

                        case "save":
                            // do your stuff
                            break;

                        default:
                            this.print("[ERROR]");
                    }
                } else {
                    this.print("[ERROR] second argument must be from type INT");
                }
            } else {
                this.print("[ERROR] second argument missing");
            }
        }
    }

    private void insertMode() throws IOException {
        String addCustomerOrAddCargo = br.readLine();
        String[] command = addCustomerOrAddCargo.split(" ");
        if (command.length > 1) {
            //addCargo
            AddCargoEvent event = new AddCargoEvent(this, command);
            addCargoEventHandler.handle(event);


        } else {
            //add customer
            if (!isNumeric(addCustomerOrAddCargo)) {
                AddCustomerEvent event = new AddCustomerEvent(this, addCustomerOrAddCargo);
                addCustomerHandler.handle(event);


            } else {
                this.print("Customer can not be numeric");
            }
        }
    }

    private void changeMode() throws IOException {
        String input = br.readLine();
        if (isNumeric(input)) {
            SetInspectionDateEvent event = new SetInspectionDateEvent(this, input);
            setInspectionDateEventHandler.handle(event);
        } else {
            System.err.println("[ERROR] wrong input type, allowed type: numeric");
        }
    }

    private void showMode() throws IOException {
        String whatToDo = br.readLine();
        StringTokenizer tokenizer = new StringTokenizer(whatToDo);
        String firstWord = tokenizer.nextToken();
        switch (firstWord) {
            case "customer":
                ShowCustomersAndCargosEvent event = new ShowCustomersAndCargosEvent(this, firstWord);
                showCustomersHandler.handle(event);
                break;

            case "hazard":
                if (tokenizer.hasMoreTokens()) {
                    String nextToken = tokenizer.nextToken();
                    if (nextToken.equals("i")) {
                        GetHazardEvent event2 = new GetHazardEvent(this, firstWord);
                        getHazardEventHandler.handle(event2);
                        break;
                    } else if (nextToken.equals("e")) {
                        GetNonHazardEvent event3 = new GetNonHazardEvent(this, firstWord);
                        getNonHazardEventHandler.handle(event3);
                        break;
                    } else {
                        System.err.println("Wrong argument");
                        break;
                    }
                } else {
                    System.err.println("Argument missing");
                    break;
                }

            case "cargo":
                if (tokenizer.hasMoreTokens()) {
                    String cargoTyp = tokenizer.nextToken();
                    ShowSpecificCargoTypEvent event2 = new ShowSpecificCargoTypEvent(this, cargoTyp);
                    showSpecificCargoTypEventHandler.handle(event2);
                } else {
                    ShowAllCargoEvent event1 = new ShowAllCargoEvent(this, firstWord);
                    showAllCargoEventHandler.handle(event1);
                }
                break;

            default:
                this.print(ERROR_MESSAGE);
                break;
        }
    }

    private void deleteMode() throws IOException {
        String input = br.readLine();
        if (isNumeric(input)) {
            //delete cargo at position
            DeleteCargoAtPositionEvent event = new DeleteCargoAtPositionEvent(this, input);
            deleteCargoAtPositionEventHandler.handle(event);
        } else {
            //delete customer
            DeleteCustomerEvent event = new DeleteCustomerEvent(this, input);
            deleteCustomerEventHandler.handle(event);
        }
    }

    private void print(String message) {
        PrintEvent printEvent = new PrintEvent(this, message);
        this.printEventHandler.handle(printEvent);
    }

    private static boolean isNumeric(String s) {
        return s != null && s.matches("-?[+]?\\d*\\d+");
    }

    private void terminate() {
        System.exit(0);
    }

    private void showOptions() {
        this.print(" ---------------- MODE ------------------ ");
        this.print("| :c --> change into insert-mode         |");
        this.print("| :d --> change into delete-mode         |");
        this.print("| :r --> change into show-mode           |");
        this.print("| :u --> change into change-mode         |");
        this.print("| :p --> change into persistence-mode    |");
        this.print("| :config --> change into config-mode    |");
        this.print("| exit --> exit the program              |");
        this.print("| modes --> shows this mode overview     |");
        this.print("| help --> shows a couple of syntax      |");
        this.print(" ---------------------------------------- ");
    }

    private void help() {
        this.print(" -------------------- HELP -------------------- ");
        this.print("| First type in the mode then press enter      |");
        this.print("| Add Cargo: [Cargotyp] [customer] [value]     |");
        this.print("|            [storageTime] [hazard,hazard]     |");
        this.print("|            or [,] [fragile(y/n)]             |");
        this.print("|            [pressurized(y/n)                 |");
        this.print("| Show Customer and amount of cargo: :r        |");
        this.print("|                                    customer  |");
        this.print("| Add Customer: :c [Customer]                  |");
        this.print("| Delete Customer: :d [Customer]               |");
        this.print("| Delete Cargo: :d [position]                  |");
        this.print("| more to come....                             |");
        this.print(" ---------------------------------------------- ");
    }

}
