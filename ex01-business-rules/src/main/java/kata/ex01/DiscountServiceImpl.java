package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        long discountByWeekDaysRules = discountByWeekDaysRule(drive);
        long discountByHolidaysRule = discountByHolidaysRule(drive);
        return Stream.of(discountByWeekDaysRules, discountByHolidaysRule).max(Comparator.naturalOrder()).orElse(0L);
    }

    private long discountByWeekDaysRule(HighwayDrive drive) {
        LocalDateTime morningEnd, morningStart;
        if (drive.getEnteredAt().getHour() >= 9) {
            morningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt().plusDays(1)),
                    LocalTime.of(6, 0));
            morningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt().plusDays(1)),
                    LocalTime.of(9, 0));
        } else {
            morningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt()),
                    LocalTime.of(6, 0));
            morningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt()),
                    LocalTime.of(9, 0));
        }
        LocalDateTime eveningEnd, eveningStart;
        if (drive.getEnteredAt().getHour() >= 9) {
            eveningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt().plusDays(1)),
                    LocalTime.of(17, 0));
            eveningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt().plusDays(1)),
                    LocalTime.of(20, 0));
        } else {
            eveningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt()),
                    LocalTime.of(17, 0));
            eveningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt()),
                    LocalTime.of(20, 0));
        }
        boolean isNotHoliday = !HolidayUtils.isHoliday(morningStart.toLocalDate()) && !HolidayUtils.isHoliday(eveningStart.toLocalDate());
        boolean isMorning = drive.getEnteredAt().isAfter(morningEnd) && drive.getExitedAt().isBefore(morningStart);
        boolean isEvening = drive.getEnteredAt().isAfter(eveningEnd) && drive.getExitedAt().isBefore(eveningStart);
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

    private long discountByHolidaysRule(HighwayDrive drive) {
        boolean isHoliday = HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate());
        List<VehicleFamily> discountTargetVehicleFamilyList = Arrays.asList(VehicleFamily.STANDARD, VehicleFamily.MINI, VehicleFamily.MOTORCYCLE);
        boolean isTargetVehicleFamily = discountTargetVehicleFamilyList.contains(drive.getVehicleFamily());
        boolean isRural = RouteType.RURAL == drive.getRouteType();
        return isHoliday && isTargetVehicleFamily && isRural ? 30 : 0;
    }
}
