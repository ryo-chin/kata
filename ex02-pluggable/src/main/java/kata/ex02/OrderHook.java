package kata.ex02;

import kata.ex02.model.Order;

/**
 * @author hakiba
 */
public interface OrderHook {
    void execute(Order order);
}
