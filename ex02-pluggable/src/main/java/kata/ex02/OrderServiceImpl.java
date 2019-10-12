package kata.ex02;

import com.google.inject.Inject;
import kata.ex02.model.Order;
import kata.ex02.model.OrderDetail;
import kata.ex02.repository.OrderRepository;
import kata.ex02.util.ApiSender;
import kata.ex02.util.MailSender;

import java.util.Map;
import java.util.function.Consumer;

public class OrderServiceImpl implements OrderService {
    private OrderRepository repository;
    private ApiSender apiSender;
    private MailSender mailSender;
    private Map<String, Consumer<OrderDetail>> notificationOrderMap;

    @Inject
    public OrderServiceImpl(OrderRepository repository, ApiSender apiSender, MailSender mailSender) {
        this.repository = repository;
        this.apiSender = apiSender;
        this.mailSender = mailSender;
        this.notificationOrderMap = Map.of(
                "A", apiSender::send,
                "B", mailSender::send
        );
    }

    @Override
    public void order(Order order) {
        repository.save(order);
        order.getOrderDetails()
                .forEach(orderDetail -> notificationOrderMap.get(orderDetail.getProductProvider().getName()).accept(orderDetail));
    }
}
