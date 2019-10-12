package kata.ex01.model.rule;

import kata.ex01.model.DiscountPeriod;
import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

/**
 * @author hakiba
 */
public class WeekDaysDiscountRule implements DiscountRule {
    @Override
    public boolean isApplicable(HighwayDrive drive) {
        if (RouteType.RURAL != drive.getRouteType()) {
            return false;
        }
        List<DiscountPeriod> discountPeriods = List.of(
                new DiscountPeriod(LocalTime.of(6, 0), LocalTime.of(9, 0)),
                new DiscountPeriod(LocalTime.of(17, 0), LocalTime.of(20, 0)));
        return discountPeriods
                .stream()
                .map(p -> p.calcApplyPeriod(drive))
                .flatMap(Optional::stream)
                .anyMatch(not(DiscountPeriod.AppliedPeriod::isHoliDay));
    }

    @Override
    public long calcDiscountRate(HighwayDrive drive) {
        int countPerMonth = drive.getDriver().getCountPerMonth();
        if (countPerMonth >= 10) {
            return 50;
        } else if (countPerMonth >= 5) {
            return 30;
        }
        return 0;
    }
}
