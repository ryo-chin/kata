package kata.ex01.model;

import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author hakiba
 */
public class DiscountPeriod {
    private LocalTime startTime;
    private LocalTime endTime;

    public DiscountPeriod(int startHour, int endHour) {
        this.startTime = LocalTime.of(startHour, 0);
        this.endTime = LocalTime.of(endHour, 0);
    }

    public boolean isIn(HighwayDrive drive) {
        LocalDate discountDate =  LocalDate.from(drive.getEnteredAt().plusDays(calcOffset(drive)));
        LocalDateTime startAt = LocalDateTime.of(discountDate, startTime);
        LocalDateTime endAt = LocalDateTime.of(discountDate, endTime);
        // このロジックで実装 : https://qiita.com/kawasima/items/9e23544c95385658292a#2-割引期間に利用期間が少しでも入っていればよいケース
        return drive.getEnteredAt().isBefore(endAt) && drive.getExitedAt().isAfter(startAt);
    }

    public boolean isHoliday(HighwayDrive drive) {
        return HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate().plusDays(calcOffset(drive)));
    }

    private int calcOffset(HighwayDrive drive) {
        return drive.getEnteredAt().toLocalTime().isAfter(endTime) ? 1 : 0;
    }
}
