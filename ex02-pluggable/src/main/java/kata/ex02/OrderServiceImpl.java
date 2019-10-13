package kata.ex02;

import com.google.inject.Inject;
import kata.ex02.hook.SendApiHook;
import kata.ex02.hook.SendEmailHook;
import kata.ex02.model.Order;
import kata.ex02.repository.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderRepository repository;
    private List<OrderHook> orderHooks;

    @Inject
    public OrderServiceImpl(OrderRepository repository, SendApiHook apiHook, SendEmailHook mailHook) {
        this.repository = repository;
        this.orderHooks = List.of(apiHook, mailHook);
    }

    @Override
    public void order(Order order) {
        repository.save(order);
        orderHooks.forEach(orderHook -> orderHook.execute(order));
    }
}
