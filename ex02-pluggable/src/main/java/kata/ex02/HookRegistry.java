package kata.ex02;

import com.google.inject.Inject;
import kata.ex02.hook.SendApiHook;
import kata.ex02.hook.SendEmailHook;
import kata.ex02.model.Order;

import java.util.List;

/**
 * @author hakiba
 */
public class HookRegistry {

    private List<OrderHook> orderHooks;

    @Inject
    public HookRegistry(SendApiHook apiHook, SendEmailHook mailHook) {
        this.orderHooks = List.of(apiHook, mailHook);
    }

    public void run(Order order) {
        orderHooks.forEach(orderHook -> orderHook.execute(order));
    }
}
