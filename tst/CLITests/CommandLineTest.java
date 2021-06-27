package CLITests;

import cli.CommandLine;
import events.DeserializationJBPEvent.DeserializationJBPEventHandler;
import events.addCargoEvent.AddCargoEventHandler;
import events.addCustomerEvent.AddCustomerEventHandler;
import events.addListenerEvent.AddListenerEventHandler;
import events.addObserverEvent.AddObserverEventHandler;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventHandler;
import events.deleteCustomerEvent.DeleteCustomerEventHandler;
import events.deserializationJOSEvent.DeserializationJOSEventHandler;
import events.getHazardEvent.GetHazardEventHandler;
import events.getNonHazardEvent.GetNonHazardEventHandler;
import events.listener.PrintEventListener;
import events.printEvent.PrintEventHandler;
import events.removeObserverEvent.RemoveObserverEventHandler;
import events.serializationJBPEvent.SerializationJBPEventHandler;
import events.serializationJOSEvent.SerializationJOSEventHandler;
import events.setInspectionDateEvent.SetInspectionDateEventHandler;
import events.showAllCargoEvent.ShowAllCargoEventHandler;
import events.showCustomersAndCargosEvent.ShowCustomersAndCargosEventHandler;
import events.showSpecificCargoTypEvent.ShowSpecificCargoTypEventHandler;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommandLineTest {

    CommandLine cli = new CommandLine();
    private AddCustomerEventHandler addCustomerHandler;
    private ShowCustomersAndCargosEventHandler showCustomersHandler;
    private DeleteCustomerEventHandler deleteCustomerEventHandler;
    private DeleteCargoAtPositionEventHandler deleteCargoAtPositionEventHandler;
    private AddCargoEventHandler addCargoEventHandler;
    private ShowAllCargoEventHandler showAllCargoEventHandler;
    private GetHazardEventHandler getHazardEventHandler;
    private SetInspectionDateEventHandler setInspectionDateEventHandler;
    private PrintEventHandler printEventHandler;
    private SerializationJOSEventHandler serializationJOSEventHandler;
    private DeserializationJOSEventHandler deserializationJOSEventHandler;
    private SerializationJBPEventHandler serializationJBPEventHandler;
    private DeserializationJBPEventHandler deserializationJBPEventHandler;
    private RemoveObserverEventHandler removeObserverEventHandler;
    private AddObserverEventHandler addObserverEventHandler;
    private AddListenerEventHandler addListenerEventHandler;
    private GetNonHazardEventHandler getNonHazardEventHandler;
    private ShowSpecificCargoTypEventHandler showSpecificCargoTypEventHandler;


    @Test
    public void setter_setAddCustomerEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("addCargoEventHandler");
        field.setAccessible(true);
        cli.setAddCustomerHandler(addCustomerHandler);
        assertEquals(addCustomerHandler,field.get(cli));
    }

    @Test
    public void setter_setShowCustomerAndCargosEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("showCustomersHandler");
        field.setAccessible(true);
        cli.setShowCustomersHandler(showCustomersHandler);
        assertEquals(showCustomersHandler,field.get(cli));
    }

    @Test
    public void setter_setDeleteCustomerEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("deleteCustomerEventHandler");
        field.setAccessible(true);
        cli.setDeleteCustomerEventHandler(deleteCustomerEventHandler);
        assertEquals(deleteCustomerEventHandler,field.get(cli));
    }

    @Test
    public void setter_setDeleteCargoAtPositionEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("deleteCargoAtPositionEventHandler");
        field.setAccessible(true);
        cli.setDeleteCargoAtPositionEventHandler(deleteCargoAtPositionEventHandler);
        assertEquals(deleteCargoAtPositionEventHandler,field.get(cli));
    }

    @Test
    public void setter_setAddCargoEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("addCargoEventHandler");
        field.setAccessible(true);
        cli.setAddCargoEventHandler(addCargoEventHandler);
        assertEquals(addCargoEventHandler,field.get(cli));
    }

    @Test
    public void setter_setShowAllCargoEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("showAllCargoEventHandler");
        field.setAccessible(true);
        cli.setShowAllCargoEventHandler(showAllCargoEventHandler);
        assertEquals(showAllCargoEventHandler,field.get(cli));
    }

    @Test
    public void setter_setGetHazardEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("getHazardEventHandler");
        field.setAccessible(true);
        cli.setGetHazardEventHandler(getHazardEventHandler);
        assertEquals(getHazardEventHandler,field.get(cli));
    }

    @Test
    public void setter_setInspectionDateEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("setInspectionDateEventHandler");
        field.setAccessible(true);
        cli.setSetInspectionDateEventHandler(setInspectionDateEventHandler);
        assertEquals(setInspectionDateEventHandler,field.get(cli));
    }

    @Test
    public void setter_setPrintEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("printEventHandler");
        field.setAccessible(true);
        cli.setPrintEventHandler(printEventHandler);
        assertEquals(printEventHandler,field.get(cli));
    }

    @Test
    public void setter_setSerializationJOSEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("serializationJOSEventHandler");
        field.setAccessible(true);
        cli.setSerializationJOSEventHandler(serializationJOSEventHandler);
        assertEquals(serializationJOSEventHandler,field.get(cli));
    }

    @Test
    public void setter_setDeserializationJOSEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("deserializationJOSEventHandler");
        field.setAccessible(true);
        cli.setDeserializationJOSEventHandler(deserializationJOSEventHandler);
        assertEquals(deserializationJOSEventHandler,field.get(cli));
    }

    @Test
    public void setter_setSerializationJBPEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("serializationJBPEventHandler");
        field.setAccessible(true);
        cli.setSerializationJBPEventHandler(serializationJBPEventHandler);
        assertEquals(serializationJBPEventHandler,field.get(cli));
    }

    @Test
    public void setter_setSDeserializationJBPEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("deserializationJBPEventHandler");
        field.setAccessible(true);
        cli.setDeserializationJBPEventHandler(deserializationJBPEventHandler);
        assertEquals(deserializationJBPEventHandler,field.get(cli));
    }

    @Test
    public void setter_setRemoveObserverEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("removeObserverEventHandler");
        field.setAccessible(true);
        cli.setRemoveObserverEventHandler(removeObserverEventHandler);
        assertEquals(removeObserverEventHandler,field.get(cli));
    }

    @Test
    public void setter_setAddObserverEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("addObserverEventHandler");
        field.setAccessible(true);
        cli.setAddObserverEventHandler(addObserverEventHandler);
        assertEquals(addObserverEventHandler,field.get(cli));
    }

    @Test
    public void setter_setAddListenerEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("addListenerEventHandler");
        field.setAccessible(true);
        cli.setAddListenerEventHandler(addListenerEventHandler);
        assertEquals(addListenerEventHandler,field.get(cli));
    }


    @Test
    public void setter_setGetNonHazardEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("getNonHazardEventHandler");
        field.setAccessible(true);
        cli.setGetNonHazardEventHandler(getNonHazardEventHandler);
        assertEquals(getNonHazardEventHandler,field.get(cli));
    }

    @Test
    public void setter_setShowSpecificCargoEventHandlerTest() throws NoSuchFieldException, IllegalAccessException {
        final Field field = cli.getClass().getDeclaredField("showSpecificCargoTypEventHandler");
        field.setAccessible(true);
        cli.setShowSpecificCargoTypEventHandler(showSpecificCargoTypEventHandler);
        assertEquals(showSpecificCargoTypEventHandler,field.get(cli));
    }





















//    @Test
//    public void commandLineStartMethodTest(){
//        CommandLine commandLine = new CommandLine();
//
//        PrintEventHandler handler = mock(PrintEventHandler.class);
//        PrintEventListener listener = new View();
//        handler.add(listener);
//        commandLine.setPrintEventHandler(handler);
//
//        PrintStream originalOut = System.out;
//
//        OutputStream os = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(os);
//        System.setOut(ps);
//
//        InputStream originalIs = System.in;
//        String command = "exit";
//        ByteArrayInputStream byteArrayInputStream =new ByteArrayInputStream(command.getBytes());
//        System.setIn(byteArrayInputStream);
//
//        try {
//            commandLine.start(byteArrayInputStream,ps);
//
//            //wie teste ich dass System.exit() aufgerufen wurde? https://stefanbirkner.github.io/system-rules/
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            System.setIn(originalIs);
//            System.setOut(originalOut);
//        }
//    }



}
