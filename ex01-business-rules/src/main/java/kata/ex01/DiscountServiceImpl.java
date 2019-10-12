package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.rule.DiscountRule;
import kata.ex01.rule.HolidaysDiscountRule;
import kata.ex01.rule.MidnightDiscountRule;
import kata.ex01.rule.WeekDaysDiscountRule;

import java.util.Comparator;
import java.util.List;

/**
 * @author hakiba
 */
public class DiscountServiceImpl implements DiscountService {
    private List<DiscountRule> rules;

    public DiscountServiceImpl() {
        this.rules = List.of(
                new WeekDaysDiscountRule(),
                new HolidaysDiscountRule(),
                new MidnightDiscountRule());
    }

    @Override
    public long calc(HighwayDrive drive) {
        return rules.stream()
                .filter(rule -> rule.isApplicable(drive))
                .map(rule -> rule.calcDiscountRate(drive))
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }
}
