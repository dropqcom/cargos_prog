package gui;

import events.addCustomerEvent.AddCustomerEventHandler;
import events.addCustomerEvent.AddCustomerEventListenerImpl;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEvent;
import events.deleteCargoAtPosition.DeleteCargoAtPositionEventHandler;
import events.logEvent.LogEvent;
import events.logEvent.LogEventHandler;
import events.logEvent.LogEventUserInput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import persistence.Persistence;
import storageContract.Exceptions.CustomerDoesNotExistException;
import storageContract.Exceptions.StorageIsFullException;
import storageContract.Exceptions.StorageToSmallException;
import storageContract.administration.Customer;
import storageContract.administration.CustomerImpl;
import storageContract.administration.CustomerManagementImpl;
import storageContract.administration.StorageImpl;
import storageContract.cargo.*;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class Controller {

    private CustomerManagementImpl management = new CustomerManagementImpl();
    private StorageImpl storage;

    {
        try {
            storage = new StorageImpl(10);
        } catch (StorageToSmallException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public TextField customerTextBox;
    @FXML
    public CheckBox fragileCheckBox;
    @FXML
    public CheckBox pressurizedCheckBox;
    @FXML
    public Label durationOfCargo;
    @FXML
    public Label valueOfCargo;
    @FXML
    public TextField valueTextBox;
    @FXML
    public TextField durationTextBox;
    @FXML
    public CheckBox explosiveCheckBox;
    @FXML
    public CheckBox flammableCheckBox;
    @FXML
    public CheckBox radioactiveCheckBox;
    @FXML
    public CheckBox toxicCheckBox;
    @FXML
    public Button storeCargoButton;
    @FXML
    public ListView listView;
    @FXML
    public TextField positionTextBox;
    @FXML
    public Button deleteCargoButton;

    public TextField positionTextBoxInspectionDate;
    public Button setInspectionDate;
    public Button deleteCustomerButton;
    public TextField deleteCustomerTextBox;
    public Button saveJOSButton;
    public Button loadJOSButton;
    public Button saveJBPButton;
    public Button loadJBPButton;


    //---------- ComboBox CargoTyp
    @FXML
    public ComboBox cargoTypeBox;

    public ObservableList<Enum> createCargoTypList() {
        ObservableList<Enum> cargoTypList = FXCollections.observableArrayList();
        cargoTypList.add(CargoType.LiquidBulkCargo);
        cargoTypList.add(CargoType.UnitisedCargo);
        cargoTypList.add(CargoType.MixedCargoLiquidBulkAndUnitised);
        return cargoTypList;
    }


    @FXML
    public void onCargoTypeBoxSelected(ActionEvent actionEvent) {
        Enum cargoTyp = (Enum) this.cargoTypeBox.getValue();
        System.out.println(cargoTyp + " selected");
        if (CargoType.LiquidBulkCargo.equals(cargoTyp)) {
            fragileCheckBox.setDisable(true);
            pressurizedCheckBox.setDisable(false);
        } else if (CargoType.UnitisedCargo.equals(cargoTyp)) {
            pressurizedCheckBox.setDisable(true);
            fragileCheckBox.setDisable(false);
        } else {
            pressurizedCheckBox.setDisable(false);
            fragileCheckBox.setDisable(false);
        }
    }

    @FXML
    public void onStoreCargoClick(ActionEvent actionEvent) {
        CargoImpl cargo = null;
        Date lastInspectionDate = new Date();
        String customerName = customerTextBox.getText();
        CustomerImpl customer = new CustomerImpl(customerName);
        if (!management.checkIfCustomerExist(customerName)) {
            management.addCustomer(customer);
        }
        String valueStr = valueTextBox.getText();
        String durationStr = durationTextBox.getText();
        BigDecimal value = null;
        Duration duration = null;

        if (valueStr.matches("[0-9]+[.][0-9]+")) {
            double val = Double.parseDouble(valueStr);
            value = BigDecimal.valueOf(val);
        } else if (isNumeric(valueStr)) {
            value = BigDecimal.valueOf(Long.parseLong(valueStr));
        } else {
            valueTextBox.setStyle("-fx-background-color: Red;");
        }
        if (isNumeric(durationStr)) {
            duration = Duration.ofSeconds(Long.parseLong(durationStr));
        } else {
            durationTextBox.setStyle("-fx-background-color: Red;");
        }

        Collection<Hazard> hazards = new ArrayList<>();
        if (this.explosiveCheckBox.isSelected()) {
            hazards.add(Hazard.explosive);
        }
        if (this.flammableCheckBox.isSelected()) {
            hazards.add(Hazard.flammable);
        }
        if (this.toxicCheckBox.isSelected()) {
            hazards.add(Hazard.toxic);
        }
        if (this.radioactiveCheckBox.isSelected()) {
            hazards.add(Hazard.radioactive);
        }

        Enum cargoTyp = (Enum) this.cargoTypeBox.getValue();

        if (CargoType.UnitisedCargo.equals(cargoTyp)) {
            boolean fragile = this.fragileCheckBox.isSelected();
            cargo = new UnitisedCargoImpl(customer, value, duration, hazards, lastInspectionDate, fragile);
        } else if (CargoType.LiquidBulkCargo.equals(cargoTyp)) {
            boolean pressurized = this.pressurizedCheckBox.isSelected();
            cargo = new LiquidBulkCargoImpl(customer, value, duration, hazards, lastInspectionDate, pressurized);
        } else if (CargoType.MixedCargoLiquidBulkAndUnitised.equals(cargoTyp)) {
            boolean fragile = this.fragileCheckBox.isSelected();
            boolean pressurized = this.pressurizedCheckBox.isSelected();
            cargo = new MixedCargoLiquidBulkAndUnitisedImpl(customer, value, duration, hazards, lastInspectionDate, pressurized, fragile);
        }
        try {
            if ((null != value) && (null != duration) && (null != customer)) {
                this.management.addCargo(customer, cargo, this.storage);
                System.out.println("Cargo has been added");
                System.out.println(cargo);
                this.listView.setItems(this.storage.getStorageCargos());
                reset();
            }
        } catch (StorageIsFullException | CustomerDoesNotExistException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void onDeleteCargoClick(ActionEvent actionEvent) {
        String position = this.positionTextBox.getText();
        if (isNumeric(position)) {
            int pos = Integer.parseInt(position);
            if (null != this.storage.getCargo(pos)) {
                this.management.deleteCargo(pos, this.storage);
                reset();
                this.listView.setItems(this.storage.getStorageCargos());
            } else {
                // ERROR MESSAGE
                System.err.println("Something went wrong while deleting");
            }
        } else {
            this.positionTextBox.setStyle("-fx-background-color: Red;");
        }
    }


    private void updateView() {
        this.cargoTypeBox.setItems(this.createCargoTypList());
    }

    @FXML
    private void initialize() {
        this.updateView();
    }


    //helper methods

    private boolean isNumeric(String s) {
        return s != null && s.matches("[+]?\\d*\\d+");
    }

    private void reset() {
        this.customerTextBox.clear();
        this.valueTextBox.clear();
        this.durationTextBox.clear();
        this.toxicCheckBox.setSelected(false);
        this.explosiveCheckBox.setSelected(false);
        this.flammableCheckBox.setSelected(false);
        this.radioactiveCheckBox.setSelected(false);
        this.fragileCheckBox.setSelected(false);
        this.pressurizedCheckBox.setSelected(false);
        this.valueTextBox.setStyle("");
        this.durationTextBox.setStyle("");
        this.positionTextBox.setStyle("");
        this.positionTextBox.clear();
        this.positionTextBoxInspectionDate.setStyle("");
        this.positionTextBoxInspectionDate.clear();
    }

    public void onSetInspectionDateClick(ActionEvent actionEvent) throws CustomerDoesNotExistException, StorageIsFullException {
        String position = this.positionTextBoxInspectionDate.getText();
        if (isNumeric(position)) {
            int pos = Integer.parseInt(position);
            if (null != this.storage.getCargo(pos)) {
                CargoImpl cargo = storage.getCargo(pos);
                Date date = new Date();
                this.storage.getCargo(pos).setHasStorageDateBeenSet(true);
                this.storage.getCargo(pos).setLastInspectionDate(date);
                cargo.setHasStorageDateBeenSet(true);
                cargo.setLastInspectionDate(date);
                management.deleteCargo(pos, storage);
                management.addCargo(cargo.getOwner(), cargo, storage);
//                storage.delete(pos);
//                storage.add(cargo,pos);
                reset();
                this.listView.setItems(this.storage.getStorageCargos());
            } else {
                this.positionTextBoxInspectionDate.setStyle("-fx-background-color: Red;");
            }
        }
    }

    public void onDeleteCustomerClick(ActionEvent actionEvent) throws CustomerDoesNotExistException {
        String customerName = this.deleteCustomerTextBox.getText();
        if (management.checkIfCustomerExist(customerName)) {
            Customer customer = management.getCustomer(customerName);
            management.delete(customer, storage);
            reset();
            this.listView.setItems(this.storage.getStorageCargos());
        } else {
            this.deleteCustomerTextBox.setStyle("-fx-background-color: Red;");
        }
    }

    public void onSaveJOSButtonClick(ActionEvent actionEvent) {
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        Persistence.serialize("gui.ser", allCargo);
    }

    public void onLoadJOSButtonClick(ActionEvent actionEvent) {
        File file = new File("gui.ser");
        if (file.exists()) {
            Collection<CargoImpl> loadedCargo = Persistence.deserialize("gui.ser");
            assert loadedCargo != null;
            ArrayList<CargoImpl> cargoList = new ArrayList<>(loadedCargo);
            for (int i = 0; i < cargoList.size(); i++) {
                try {
                    management.addCargo(cargoList.get(i).getOwner(), cargoList.get(i), storage);
                    reset();
                    this.listView.setItems(this.storage.getStorageCargos());
                } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                    return;
                }
            }
        }
    }

    public void onSaveJBPButtonClick(ActionEvent actionEvent) {
        Collection<CargoImpl> allCargo = storage.getAllCargo();
        Persistence.encoderJBP("gui.xml", allCargo);
    }

    public void onLoadJBPButtonClick(ActionEvent actionEvent) {
        File file = new File("gui.xml");
        if (file.exists()) {
            Collection<CargoImpl> loadedCargo = Persistence.decoderJBP("gui.xml");
            assert loadedCargo != null;
            ArrayList<CargoImpl> cargoList = new ArrayList<>(loadedCargo);
            for (CargoImpl cargo : cargoList) {
                try {
                    management.addCargo(cargo.getOwner(), cargo, storage);
                    reset();
                    this.listView.setItems(this.storage.getStorageCargos());
                } catch (StorageIsFullException | CustomerDoesNotExistException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
