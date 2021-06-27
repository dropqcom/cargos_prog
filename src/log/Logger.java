package log;

import events.listener.LogEventListener;
import events.logEvent.LogEvent;
import events.logEvent.LogEventUserInput;
import log.log_ressources.LogStrings;

import java.io.*;
import java.util.Date;

public class Logger implements LogEventListener {



    private String file_name;
    private boolean log_enable;
    private LogStrings logStrings;
    private File log_file;

    public Logger(String file_name, boolean log_enable, LogStrings logStrings) {
        this.file_name = file_name;
        this.log_enable = log_enable;
        this.logStrings = logStrings;
        if(log_enable){
            log_file = new File(file_name);
        }
    }

    @Override
    public void onLogEvent(LogEvent event) {
        if(isLog_enable()){
            Date date = new Date();
            String log_String = date + " : ";
            if(null != event.getLogTyp()){
                switch(event.getLogTyp()){
                    case ADD_OBSERVER:
                        log_String += this.logStrings.getLogString(LogTyp.ADD_OBSERVER);
                        break;
                    case REMOVE_OBSERVER:
                        log_String += this.logStrings.getLogString(LogTyp.REMOVE_OBSERVER);
                        break;
                    case ADD_LISTENER:
                        log_String += this.logStrings.getLogString(LogTyp.ADD_LISTENER);
                        break;
                    case REMOVE_LISTENER:
                        log_String += this.logStrings.getLogString(LogTyp.REMOVE_LISTENER);
                        break;
                    case SAVE_JOS:
                        log_String += this.logStrings.getLogString(LogTyp.SAVE_JOS);
                        break;
                    case LOAD_JOS:
                        log_String += this.logStrings.getLogString(LogTyp.LOAD_JOS);
                        break;
                    case SHOW_CARGO:
                        log_String += this.logStrings.getLogString(LogTyp.SHOW_CARGO);
                        break;
                    case SAVE_JBP:
                        log_String += this.logStrings.getLogString(LogTyp.SAVE_JBP);
                        break;
                    case LOAD_JBP:
                        log_String += this.logStrings.getLogString(LogTyp.LOAD_JBP);
                        break;
                    case GET_NON_HAZARD:
                        log_String += this.logStrings.getLogString(LogTyp.GET_NON_HAZARD);
                        break;
                    case GET_HAZARD:
                        log_String += this.logStrings.getLogString(LogTyp.GET_HAZARD);
                        break;
                    case SET_INSPECTION:
                        log_String += this.logStrings.getLogString(LogTyp.SET_INSPECTION);
                        break;
                    case SHOW_CUSTOMER_CARGO:
                        log_String += this.logStrings.getLogString(LogTyp.SHOW_CUSTOMER_CARGO);
                        break;
                    default:
                        break;
                }
                write_String_to_log(log_String);
            }
        }
    }

    @Override
    public void onLogEventInsideEvent(LogEventUserInput event) {
        if(null != event.getString()){
            if(log_enable){
                Date date = new Date();
                write_String_to_log(date + " : " + event.getString());
            }
        }
    }


    private void write_String_to_log(String string){
        FileWriter fw = null;
        try {
            fw = new FileWriter(file_name,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(string);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isLog_enable() {
        return log_enable;
    }
}
