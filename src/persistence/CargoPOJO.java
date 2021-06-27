package persistence;

import storageContract.administration.Customer;
import storageContract.cargo.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CargoPOJO {


    private Customer owner;
    private String valueStr;
    private String storageDurationStr;
    private Collection<Hazard> hazard;
    private Date lastInspectionDate;
    private String storageDateStr;
    private Duration storageDuration;
    private BigDecimal value;
    private boolean hasStorageDateBeenSet = false;
    private LocalDateTime storageDate;
    private boolean isPressurized;
    private boolean isFragile;
    private String cargoTyp;


    public CargoPOJO(Customer owner, String valueStr, String storageDurationStr, Collection<Hazard> hazard,
                     Date lastInspectionDate, String storageDateStr, boolean isPressurized, boolean isFragile,String cargoTyp){
        this.owner = owner;
        this.valueStr = valueStr;
        this.storageDurationStr = storageDurationStr;
        this.hazard = hazard;
        this.lastInspectionDate = lastInspectionDate;
        this.storageDateStr = storageDateStr;
        this.isPressurized = isPressurized;
        this.isFragile = isFragile;
        this.cargoTyp = cargoTyp;
    }

    public CargoPOJO(Customer owner, BigDecimal value, Duration storageDuration,
                     Collection<Hazard> hazard, Date lastInspectionDate, boolean hasStorageDateBeenSet, LocalDateTime storageDate) {
        this.owner = owner;
        this.value = value;
        this.storageDuration = storageDuration;
        this.hazard = hazard;
        this.lastInspectionDate = lastInspectionDate;
        this.hasStorageDateBeenSet = true;
        this.storageDate = storageDate;
    }

    public CargoPOJO() {}


    public void setPressurized(boolean pressurized) {
        isPressurized = pressurized;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public void setCargoTyp(String cargoTyp) {
        this.cargoTyp = cargoTyp;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getStorageDurationStr() {
        return storageDurationStr;
    }

    public void setStorageDurationStr(String storageDurationStr) {
        this.storageDurationStr = storageDurationStr;
    }

    public Collection<Hazard> getHazard() {
        return this.hazard;
    }

    public void setHazard(Collection<Hazard> hazard) {
        this.hazard = hazard;
    }

    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public String getStorageDateStr() {
        return storageDateStr;
    }

    public void setStorageDateStr(String storageDateStr) {
        this.storageDateStr = storageDateStr;
    }

    public Duration getStorageDuration() {
        return storageDuration;
    }

    public void setStorageDuration(Duration storageDuration) {
        this.storageDuration = storageDuration;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isHasStorageDateBeenSet() {
        return hasStorageDateBeenSet;
    }

    public void setHasStorageDateBeenSet(boolean hasStorageDateBeenSet) {
        this.hasStorageDateBeenSet = hasStorageDateBeenSet;
    }

    public LocalDateTime getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(LocalDateTime storageDate) {
        this.storageDate = storageDate;
    }

    public String valueToString(BigDecimal value){
        return value.toString();
    }

    public String storageDateToString(LocalDateTime storageDate){
        return storageDate.toString();
    }

    public String storageDurationToString(Duration storageDuration){
        return storageDuration.toString();
    }

    public BigDecimal valueStringToBigDecimal(String valueStr){
        BigDecimal decimal;
        if(valueStr.contains(".")){
            double val = Double.parseDouble(valueStr);
            decimal = BigDecimal.valueOf(val);
        }else{
            decimal = BigDecimal.valueOf(Long.parseLong(valueStr));
        }
        return decimal;
    }

    public LocalDateTime storageDateStringToLocalDateTime(String storageDateStr){
        return LocalDateTime.parse(storageDateStr);
    }

    public Duration storageDurationStringToDuration(String storageDurationStr) {
        return Duration.parse(storageDurationStr);
    }

    public boolean isPressurized() {
        return isPressurized;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public String getCargoTyp() {
        return cargoTyp;
    }




    //method from CargoImpl to CargoPOJO
    public Collection<CargoPOJO> createPOJO(Collection<CargoImpl> cargoCollection){
        Collection<CargoPOJO> POJOCollection = new ArrayList<>();
        ArrayList<CargoImpl> arrayList = new ArrayList<>(cargoCollection);
        for(int i = 0; i < arrayList.size(); i++){
            boolean isFragile = false;
            boolean isPressurized = false;
            String cargoTyp = null;

            BigDecimal value = arrayList.get(i).getValue();
            String valueStr = valueToString(value);
            Duration duration = arrayList.get(i).getStorageDuration();
            String durationStr = storageDurationToString(duration);
            LocalDateTime dateTime = arrayList.get(i).getStorageDate();
            String storageDateStr = storageDateToString(dateTime);
            Customer customer = arrayList.get(i).getOwner();
            Collection<Hazard> hazardsCollection = arrayList.get(i).getHazard();
            Date lastInspectionDate = arrayList.get(i).getLastInspectionDate();

            if(arrayList.get(i).getClass().equals(UnitisedCargoImpl.class)){
                UnitisedCargoImpl unitisedCargo = (UnitisedCargoImpl) arrayList.get(i);
                isFragile = unitisedCargo.isFragile();
                cargoTyp = "UnitisedCargoImpl";
                CargoPOJO cargoPOJO = new CargoPOJO(customer,valueStr,durationStr,hazardsCollection,lastInspectionDate,storageDateStr,isPressurized,isFragile,cargoTyp);
                POJOCollection.add(cargoPOJO);
            }
            if(arrayList.get(i).getClass().equals(LiquidBulkCargoImpl.class)){
                LiquidBulkCargoImpl liquidBulkCargo = (LiquidBulkCargoImpl) arrayList.get(i);
                isPressurized = liquidBulkCargo.isPressurized();
                cargoTyp = "LiquidBulkCargoImpl";
                CargoPOJO cargoPOJO = new CargoPOJO(customer,valueStr,durationStr,hazardsCollection,lastInspectionDate,storageDateStr,isPressurized,isFragile,cargoTyp);
                POJOCollection.add(cargoPOJO);
            }
            if(arrayList.get(i).getClass().equals(MixedCargoLiquidBulkAndUnitisedImpl.class)){
                MixedCargoLiquidBulkAndUnitisedImpl mixedCargoLiquidBulkAndUnitised = (MixedCargoLiquidBulkAndUnitisedImpl) arrayList.get(i);
                isFragile = mixedCargoLiquidBulkAndUnitised.isFragile();
                isPressurized = mixedCargoLiquidBulkAndUnitised.isPressurized();
                cargoTyp = "MixedLiquidBulkAndUnitisedImpl";
                CargoPOJO cargoPOJO = new CargoPOJO(customer,valueStr,durationStr,hazardsCollection,lastInspectionDate,storageDateStr,isPressurized,isFragile,cargoTyp);
                POJOCollection.add(cargoPOJO);
            }
        }
        return  POJOCollection;
    }

    public Collection<CargoImpl> createCargoImpl(Collection<CargoPOJO> POJOCollection){
        Collection<CargoImpl> cargoCollection = new ArrayList<>();
        ArrayList<CargoPOJO> arrayList = new ArrayList<>(POJOCollection);

        for(int i = 0; i < arrayList.size(); i++){
            boolean isFragile = false;
            boolean isPressurized = false;

            BigDecimal value = arrayList.get(i).valueStringToBigDecimal(arrayList.get(i).getValueStr());
            Duration duration = arrayList.get(i).storageDurationStringToDuration(arrayList.get(i).getStorageDurationStr());
            Date date = arrayList.get(i).getLastInspectionDate();
            Customer customer = arrayList.get(i).getOwner();
            Collection<Hazard> hazardCollection = arrayList.get(i).getHazard();
            LocalDateTime storageDate = arrayList.get(i).storageDateStringToLocalDateTime(arrayList.get(i).getStorageDateStr());

            if(arrayList.get(i).getCargoTyp().equals("UnitisedCargoImpl")){
                CargoPOJO cargoPOJO = arrayList.get(i);
                isFragile = cargoPOJO.isFragile();
                UnitisedCargoImpl unitisedCargo = new UnitisedCargoImpl(customer,value,duration,hazardCollection,date,true,storageDate,isFragile);
                cargoCollection.add(unitisedCargo);
            }
            if(arrayList.get(i).getCargoTyp().equals("LiquidBulkCargoImpl")){
                CargoPOJO cargoPOJO = arrayList.get(i);
                isPressurized = cargoPOJO.isPressurized();
                LiquidBulkCargoImpl liquidBulkCargo = new LiquidBulkCargoImpl(customer,value,duration,hazardCollection,date,true,storageDate,isPressurized);
                cargoCollection.add(liquidBulkCargo);
            }
            if(arrayList.get(i).getCargoTyp().equals("MixedLiquidBulkAndUnitisedImpl")){
                CargoPOJO cargoPOJO = arrayList.get(i);
                isFragile = cargoPOJO.isFragile();
                isPressurized = cargoPOJO.isPressurized();
                MixedCargoLiquidBulkAndUnitisedImpl mixedCargoLiquidBulkAndUnitised = new MixedCargoLiquidBulkAndUnitisedImpl(customer,value,duration,hazardCollection,date,true,storageDate,isPressurized,isFragile);
                cargoCollection.add(mixedCargoLiquidBulkAndUnitised);
            }
        }
        return cargoCollection;
    }

}
