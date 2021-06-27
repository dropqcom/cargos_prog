package storageContract.cargo;

import storageContract.administration.Customer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class UnitisedCargoImpl extends CargoImpl implements UnitisedCargo {
    private boolean isFragile;


    public UnitisedCargoImpl(Customer owner, BigDecimal value, Duration storageDuration,
                             Collection<Hazard> hazard, Date lastInspectionDate, boolean isFragile) {
        super(owner, value, storageDuration, hazard, lastInspectionDate);
        this.isFragile = isFragile;
    }


//    public UnitisedCargoImpl(Customer owner, String valueStr, String storageDurationStr, Collection<Hazard> hazard,
//                             Date lastInspectionDate, String storageDateStr, boolean isFragile) {
//        super(owner, valueStr, storageDurationStr, hazard, lastInspectionDate, storageDateStr);
//        this.isFragile = isFragile;
//    }

    public UnitisedCargoImpl(Customer owner, BigDecimal value, Duration storageDuration, Collection<Hazard> hazard,
                             Date lastInspectionDate, boolean hasStorageDateBeenSet, LocalDateTime storageDate, boolean isFragile) {
        super(owner, value, storageDuration, hazard, lastInspectionDate, hasStorageDateBeenSet, storageDate);
        this.isFragile = isFragile;
    }



    //public UnitisedCargoImpl(){}


    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    @Override
    public boolean isFragile() {
        return this.isFragile;
    }


}
