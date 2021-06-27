package log;

import log.log_ressources.LogStrings;
import log.log_ressources.LogStringsEnglish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LogStringsEnglishTest {

    private final LogStrings logStrings = new LogStringsEnglish();

    @Test
    public void getLogStringTest1(){
        String expected = "Observer added";
        String actual = logStrings.getLogString(LogTyp.ADD_OBSERVER);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest2(){
        String expected = "Observer removed";
        String actual = logStrings.getLogString(LogTyp.REMOVE_OBSERVER);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest3(){
        String expected = "Listener added";
        String actual = logStrings.getLogString(LogTyp.ADD_LISTENER);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest4(){
        String expected = "Listener removed";
        String actual = logStrings.getLogString(LogTyp.REMOVE_LISTENER);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest5(){
        String expected = "Cargo saved with JOS";
        String actual = logStrings.getLogString(LogTyp.SAVE_JOS);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest6(){
        String expected = "Cargo loaded with JOS";
        String actual = logStrings.getLogString(LogTyp.LOAD_JOS);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest7(){
        String expected = "User asked for hazard which are not inside storage";
        String actual = logStrings.getLogString(LogTyp.GET_NON_HAZARD);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest8(){
        String expected = "Cargo saved with JBP";
        String actual = logStrings.getLogString(LogTyp.SAVE_JBP);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest9(){
        String expected = "Inspection date has been updated";
        String actual = logStrings.getLogString(LogTyp.SET_INSPECTION);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest10(){
        String expected =  "User asked to see all cargo currently stored inside storage";
        String actual = logStrings.getLogString(LogTyp.SHOW_CARGO);
        assertEquals(expected,actual);
    }

    @Test
    public void getLogStringTest11(){
        String expected = "User asked to see customer and their number of cargo";
        String actual = logStrings.getLogString(LogTyp.SHOW_CUSTOMER_CARGO);
        assertEquals(expected,actual);
    }


}
