package kata.ex01.rule;

import kata.ex01.model.DiscountPeriod;
import kata.ex01.model.HighwayDrive;

/**
 * @author hakiba
 */
public class MidnightDiscountRule implements DiscountRule {
    private DiscountPeriod period = new DiscountPeriod(0, 4);

    @Override
    public boolean isApplicable(HighwayDrive drive) {
        return period.isIn(drive);
    }

    @Override
    public long calcDiscountRate(HighwayDrive drive) {
        return 30;
    }
}
