package eventTest;

import cli.CommandLine;
import events.listener.PrintEventListener;
import events.listener.ShowAllCargoEventListener;
import events.printEvent.PrintEventHandler;
import events.showAllCargoEvent.ShowAllCargoEvent;
import events.showAllCargoEvent.ShowAllCargoEventHandler;
import events.showAllCargoEvent.ShowAllCargoEventListenerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManagement;
import storageContract.administration.Storage;
import storageContract.cargo.CargoImpl;
import storageContract.cargo.Hazard;
import view.View;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.*;

public class OnShowAllCargoEventTest2 {

    private Storage storage;
    private CustomerManagement management;

    private final String NAME_1 = "Freddy";
    private Customer customer = new CustomerImpl(NAME_1);
    private BigDecimal bd = new BigDecimal(23);
    private Duration d = Duration.ofSeconds(2222);
    private Collection<Hazard> hazards = new ArrayList<>();
    private Date date = new Date();
    private Date nullDate = null;
    private CargoImpl cargo;



    @BeforeEach
    public void init() throws CustomerDoesNotExistException, StorageIsFullException {
        hazards.add(Hazard.explosive);
        cargo = new CargoImpl(customer,bd,d,hazards,date);
        management.addCargo(customer,cargo,storage);
    }





}
