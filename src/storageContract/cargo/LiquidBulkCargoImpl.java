package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkCargoImpl extends CargoImpl implements LiquidBulkCargo {
    private boolean isPressurized;


    public LiquidBulkCargoImpl(Customer owner, BigDecimal value, Duration storageDuration,
                               Collection<Hazard> hazard, Date lastInspectionDate, boolean isPressurized) {
        super(owner, value, storageDuration, hazard, lastInspectionDate);
        this.isPressurized = isPressurized;
    }

    public LiquidBulkCargoImpl(Customer owner, BigDecimal value, Duration storageDuration, Collection<Hazard> hazard, Date lastInspectionDate, boolean hasStorageDateBeenSet, LocalDateTime storageDate, boolean isPressurized) {
        super(owner, value, storageDuration, hazard, lastInspectionDate, hasStorageDateBeenSet, storageDate);
        this.isPressurized = isPressurized;
    }


    @Override
    public boolean isPressurized() {
        return isPressurized;
    }

    public void setPressurized(boolean pressurized) {
        isPressurized = pressurized;
    }
}
