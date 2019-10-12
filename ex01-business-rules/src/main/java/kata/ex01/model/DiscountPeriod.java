package kata.ex01.model;

import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * @author hakiba
 */
public class DiscountPeriod {
    private LocalTime startTime;
    private LocalTime endTime;

    public static class AppliedPeriod {
        public AppliedPeriod(LocalDateTime startAt, LocalDateTime endAt) {
            this.startAt = startAt;
            this.endAt = endAt;
        }

        public LocalDateTime getStartAt() {
            return startAt;
        }

        public LocalDateTime getEndAt() {
            return endAt;
        }

        public boolean isHoliDay() {
            return HolidayUtils.isHoliday(getStartAt().toLocalDate());
        }

        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }

    public DiscountPeriod(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private boolean isNextDay(HighwayDrive drive) {
        return drive.getEnteredAt().getHour() >= endTime.getHour();
    }

    public Optional<AppliedPeriod> calcApplyPeriod(HighwayDrive drive) {
        LocalDate discountDate = isNextDay(drive) ? LocalDate.from(drive.getEnteredAt().plusDays(1)) : LocalDate.from(drive.getEnteredAt());
        LocalDateTime startAt = LocalDateTime.of(discountDate, startTime);
        LocalDateTime endAt = LocalDateTime.of(discountDate, endTime);
        // このロジックで実装 : https://qiita.com/kawasima/items/9e23544c95385658292a#2-割引期間に利用期間が少しでも入っていればよいケース
        return drive.getEnteredAt().isBefore(endAt) && drive.getExitedAt().isAfter(startAt)
                ? Optional.of(new AppliedPeriod(startAt, endAt))
                : Optional.empty();
    }

}
