package kata.ex02;

import com.google.inject.Inject;
import kata.ex02.hook.OrderHookPoint;
import kata.ex02.model.Order;
import kata.ex02.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {
    private OrderRepository repository;
    private HookRegistry hookRegistry;

    @Inject
    public OrderServiceImpl(OrderRepository repository, HookRegistry hookRegistry) {
        this.repository = repository;
        this.hookRegistry = hookRegistry;
    }

    @Override
    public void order(Order order) {
        hookRegistry.run(OrderHookPoint.BeforeOrder, order);
        repository.save(order);
        hookRegistry.run(OrderHookPoint.AfterOrder, order);
    }
}
