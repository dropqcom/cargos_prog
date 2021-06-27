package storageContract.cargo;

import storageContract.administration.Customer;

import javax.crypto.Cipher;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

public class CargoImpl implements Cargo, Serializable {

    static final long serialVersionUID = 1L;

    private Customer owner;
    private BigDecimal value;
    private Date lastInspectionDate;
    private Duration storageDuration;
    private Collection<Hazard> hazard;

    private int storagePosition;
    private LocalDateTime storageDate;


    /**
     * hasStorageDateBeenSet
     * used when we reload Cargos with JOS that the storageDate is not set again when addCargo is called!!
     */
    private boolean hasStorageDateBeenSet = false;

    public CargoImpl(Customer owner, BigDecimal value, Duration storageDuration, Collection<Hazard> hazard, Date lastInspectionDate) {
        this.owner = owner;
        this.value = value;
        this.storageDuration = storageDuration;
        this.hazard = hazard;
        this.lastInspectionDate = lastInspectionDate;
    }

    public CargoImpl(Customer owner, BigDecimal value, Duration storageDuration, Collection<Hazard> hazard, Date lastInspectionDate, boolean hasStorageDateBeenSet, LocalDateTime storageDate) {
        this.owner = owner;
        this.value = value;
        this.storageDuration = storageDuration;
        this.hazard = hazard;
        this.lastInspectionDate = lastInspectionDate;
        this.hasStorageDateBeenSet = true;
        this.storageDate = storageDate;
    }


    @Override
    public Customer getOwner() {
        return this.owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public Date getLastInspectionDate() {
        return this.lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    @Override
    public Duration getDurationOfStorage() {
        return this.storageDuration;
    }

    public void setStorageDuration(Duration storageDuration) {
        this.storageDuration = storageDuration;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return this.hazard;
    }

    public void setHazard(Collection<Hazard> hazard) {
        this.hazard = hazard;
    }

    public void setStoragePosition(int storagePosition) {
        this.storagePosition = storagePosition;
    }

    public int getStoragePosition(){
        return this.storagePosition;
    }

    public void setStorageDate(LocalDateTime storageDate) {
        this.storageDate = storageDate;
    }

    public LocalDateTime getStorageDate(){
        return this.storageDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Duration getStorageDuration() {
        return storageDuration;
    }

    public Collection<Hazard> getHazard() {
        return hazard;
    }


    public boolean isHasStorageDateBeenSet() {
        return hasStorageDateBeenSet;
    }

    public void setHasStorageDateBeenSet(boolean hasStorageDateBeenSet) {
        this.hasStorageDateBeenSet = hasStorageDateBeenSet;
    }

    @Override
    public String toString() {
        return "CargoImpl{" +
                "owner=" + owner +
                ", value=" + value +
                ", lastInspectionDate=" + lastInspectionDate +
                ", storageDuration=" + storageDuration +
                ", hazard=" + hazard +
                ", storagePosition=" + storagePosition +
                ", storageDate=" + storageDate +
                '}';
    }





}
