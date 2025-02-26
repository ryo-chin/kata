package kata.ex01.rule;

import kata.ex01.model.HighwayDrive;

/**
 * @author hakiba
 */
public interface DiscountRule {
    boolean isApplicable(HighwayDrive drive);
    long calcDiscountRate(HighwayDrive drive);
}
