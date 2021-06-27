package log.log_ressources;

import log.LogTyp;

public class LogStringsEnglish implements LogStrings {


    private final String ADD_OBSERVER = "Observer added";
    private final String REMOVE_OBSERVER = "Observer removed";
    private final String ADD_LISTENER = "Listener added";
    private final String REMOVE_LISTENER = "Listener removed";
    private final String SAVE_JOS = "Cargo saved with JOS";
    private final String LOAD_JOS = "Cargo loaded with JOS";
    private final String GET_NON_HAZARD = "User asked for hazard which are not inside storage";
    private final String SAVE_JBP = "Cargo saved with JBP";
    private final String SET_INSPECTION = "Inspection date has been updated";
    private final String SHOW_CARGO = "User asked to see all cargo currently stored inside storage";
    private final String SHOW_CUSTOMER_CARGO = "User asked to see customer and their number of cargo";


    @Override
    public String getLogString(LogTyp logTyp) {

        switch(logTyp){
            case ADD_OBSERVER:
                return ADD_OBSERVER;
            case REMOVE_OBSERVER:
                return REMOVE_OBSERVER;
            case ADD_LISTENER:
                return ADD_LISTENER;
            case REMOVE_LISTENER:
                return REMOVE_LISTENER;
            case SAVE_JOS:
                return SAVE_JOS;
            case LOAD_JOS:
                return LOAD_JOS;
            case GET_NON_HAZARD:
                return GET_NON_HAZARD;
            case SAVE_JBP:
                return SAVE_JBP;
            case SET_INSPECTION:
                return SET_INSPECTION;
            case SHOW_CARGO:
                return SHOW_CARGO;
            case SHOW_CUSTOMER_CARGO:
                return SHOW_CUSTOMER_CARGO;
            default:
                break;
        }
        return null;
    }
}
