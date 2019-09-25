package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.util.HolidayUtils;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        return discountByWeekDaysRule(drive);
    }

    private long discountByWeekDaysRule(HighwayDrive drive) {
        boolean isNotHoliday = !HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate());
        boolean isMorning =
                6 <= drive.getEnteredAt().getHour() && drive.getEnteredAt().getHour() <= 9
                || 6 <= drive.getExitedAt().getHour() && drive.getExitedAt().getHour() <= 9;
        boolean isEvening =
                17 <= drive.getEnteredAt().getHour() && drive.getEnteredAt().getHour() <= 20
                        || 17 <= drive.getExitedAt().getHour() && drive.getExitedAt().getHour() <= 20;
        boolean isRural = RouteType.RURAL == drive.getRouteType();
        if (isNotHoliday && (isMorning || isEvening) && isRural) {
            int countPerMonth = drive.getDriver().getCountPerMonth();
            if (countPerMonth >= 10) {
                return 50;
            } else if (countPerMonth >= 5) {
                return 30;
            }
        }
        return 0;
    }
}
