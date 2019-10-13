package kata.ex02;

import com.google.inject.Inject;
import kata.ex02.hook.CheckStockApiHook;
import kata.ex02.hook.CheckStockEmailHook;
import kata.ex02.hook.OrderHookPoint;
import kata.ex02.hook.SendApiHook;
import kata.ex02.hook.SendEmailHook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hakiba
 */
public class HookRegistry {

    private Map<OrderHookPoint, List<OrderHook<?>>> orderHooks;

    @Inject
    public HookRegistry(CheckStockApiHook checkStockApiHook, CheckStockEmailHook checkStockEmailHook, SendApiHook apiHook, SendEmailHook mailHook) {
        // init
        this. orderHooks = new HashMap<>();
        orderHooks.put(OrderHookPoint.BeforeOrder, new ArrayList<>());
        orderHooks.put(OrderHookPoint.AfterOrder, new ArrayList<>());
        // register
        orderHooks.get(OrderHookPoint.BeforeOrder).add(checkStockApiHook);
        orderHooks.get(OrderHookPoint.BeforeOrder).add(checkStockEmailHook);
        orderHooks.get(OrderHookPoint.AfterOrder).add(apiHook);
        orderHooks.get(OrderHookPoint.AfterOrder).add(mailHook);

    }

    public <V> void run(OrderHookPoint hookPoint, V order) {
        orderHooks.get(hookPoint).forEach(orderHook -> ((OrderHook<V>) orderHook).execute(order));
    }
}
