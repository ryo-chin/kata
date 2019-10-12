package kata.ex01;

import kata.ex01.model.DiscountPeriod;
import kata.ex01.model.DiscountPeriod.AppliedPeriod;
import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * @author hakiba
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        long discountByWeekDaysRules = discountByWeekDaysRule(drive);
        long discountByHolidaysRule = discountByHolidaysRule(drive);
        return Stream.of(discountByWeekDaysRules, discountByHolidaysRule).max(Comparator.naturalOrder()).orElse(0L);
    }

    private long discountByWeekDaysRule(HighwayDrive drive) {
        if (RouteType.RURAL != drive.getRouteType()) {
            return 0;
        }
        List<DiscountPeriod> discountPeriods = List.of(
                new DiscountPeriod(LocalTime.of(6, 0), LocalTime.of(9, 0)),
                new DiscountPeriod(LocalTime.of(17, 0), LocalTime.of(20, 0)));
        Optional<AppliedPeriod> appliedPeriod = discountPeriods
                .stream()
                .map(p -> p.calcApplyPeriod(drive))
                .flatMap(Optional::stream)
                .filter(not(AppliedPeriod::isHoliDay))
                .findFirst();
        if (appliedPeriod.isPresent()) {
            int countPerMonth = drive.getDriver().getCountPerMonth();
            if (countPerMonth >= 10) {
                return 50;
            } else if (countPerMonth >= 5) {
                return 30;
            }
        }
        return 0;
    }

    private long discountByHolidaysRule(HighwayDrive drive) {
        boolean isHoliday = HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate());
        List<VehicleFamily> discountTargetVehicleFamilyList = Arrays.asList(VehicleFamily.STANDARD, VehicleFamily.MINI, VehicleFamily.MOTORCYCLE);
        boolean isTargetVehicleFamily = discountTargetVehicleFamilyList.contains(drive.getVehicleFamily());
        boolean isRural = RouteType.RURAL == drive.getRouteType();
        return isHoliday && isTargetVehicleFamily && isRural ? 30 : 0;
    }
}
