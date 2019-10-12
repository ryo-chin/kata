package kata.ex01.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;

/**
 * @author hakiba
 */
class DiscountPeriodTest {
    @Test
    public void testXxxx(){
        HighwayDrive drive = new HighwayDrive();
        DiscountPeriod period = new DiscountPeriod(LocalTime.of(6, 0), LocalTime.of(9, 0));
        // input: enteredAt, exitedAt
        Optional<DiscountPeriod.AppliedPeriod> result = period.calcApplyPeriod(drive);
    }
}