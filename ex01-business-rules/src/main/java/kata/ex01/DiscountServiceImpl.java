package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.rule.DiscountRule;
import kata.ex01.model.rule.HoliDaysDiscountRule;
import kata.ex01.model.rule.MidnightDiscountRule;
import kata.ex01.model.rule.WeekDaysDiscountRule;

import java.util.Comparator;
import java.util.List;

/**
 * @author hakiba
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {
        List<DiscountRule> rules = List.of(new WeekDaysDiscountRule(), new HoliDaysDiscountRule(), new MidnightDiscountRule());
        return rules.stream()
                .filter(rule -> rule.isApplicable(drive))
                .map(rule -> rule.calcDiscountRate(drive))
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }
}
