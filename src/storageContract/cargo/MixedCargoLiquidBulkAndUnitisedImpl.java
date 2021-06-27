package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class MixedCargoLiquidBulkAndUnitisedImpl extends CargoImpl implements MixedCargoLiquidBulkAndUnitised {
    private boolean isPressurized;
    private boolean isFragile;


    public MixedCargoLiquidBulkAndUnitisedImpl(Customer owner, BigDecimal value, Duration storageDuration,
                                               Collection<Hazard> hazard, Date lastInspectionDate, boolean isPressurized, boolean isFragile) {
        super(owner, value, storageDuration, hazard, lastInspectionDate);
        this.isPressurized = isPressurized;
        this.isFragile = isFragile;
    }

    public MixedCargoLiquidBulkAndUnitisedImpl(Customer owner, BigDecimal value, Duration storageDuration, Collection<Hazard> hazard,
                                               Date lastInspectionDate, boolean hasStorageDateBeenSet, LocalDateTime storageDate, boolean isPressurized, boolean isFragile) {
        super(owner, value, storageDuration, hazard, lastInspectionDate, hasStorageDateBeenSet, storageDate);
        this.isPressurized = isPressurized;
        this.isFragile = isFragile;
    }

    @Override
    public boolean isPressurized() {
        return this.isPressurized;
    }

    public void setPressurized(boolean pressurized) {
        isPressurized = pressurized;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    @Override
    public boolean isFragile() {
        return this.isFragile;
    }
}
