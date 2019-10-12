package kata.ex01.model.rule;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author hakiba
 */
public class HoliDaysDiscountRule implements DiscountRule {
    @Override
    public boolean isApplicable(HighwayDrive drive) {
        boolean isHoliday = HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate());
        List<VehicleFamily> discountTargetVehicleFamilyList = Arrays.asList(VehicleFamily.STANDARD, VehicleFamily.MINI, VehicleFamily.MOTORCYCLE);
        boolean isTargetVehicleFamily = discountTargetVehicleFamilyList.contains(drive.getVehicleFamily());
        boolean isRural = RouteType.RURAL == drive.getRouteType();
        return isHoliday && isTargetVehicleFamily && isRural;
    }

    @Override
    public long calcDiscountRate(HighwayDrive drive) {
        return 30;
    }
}
