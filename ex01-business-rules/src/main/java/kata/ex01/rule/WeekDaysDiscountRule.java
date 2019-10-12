package kata.ex01.rule;

import kata.ex01.model.DiscountPeriod;
import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;

/**
 * @author hakiba
 */
public class WeekDaysDiscountRule implements DiscountRule {
    private DiscountPeriod morning = new DiscountPeriod(6, 9);
    private DiscountPeriod evening = new DiscountPeriod(17, 20);

    @Override
    public boolean isApplicable(HighwayDrive drive) {
        boolean inMorning = !morning.isHoliday(drive) && morning.isIn(drive);
        boolean inEvening = !evening.isHoliday(drive) && evening.isIn(drive);
        return RouteType.RURAL == drive.getRouteType() && (inMorning || inEvening);
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
