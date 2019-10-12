package kata.ex01.model.rule;

import kata.ex01.model.DiscountPeriod;
import kata.ex01.model.HighwayDrive;

import java.time.LocalTime;

/**
 * @author hakiba
 */
public class MidnightDiscountRule implements DiscountRule {

    @Override
    public boolean isApplicable(HighwayDrive drive) {
        return new DiscountPeriod(LocalTime.of(0, 0), LocalTime.of(4, 0))
                .calcApplyPeriod(drive)
                .isPresent();
    }

    @Override
    public long calcDiscountRate(HighwayDrive drive) {
        return 30;
    }
}
