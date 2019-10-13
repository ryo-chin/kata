package kata.ex02.hook;

import com.google.inject.Inject;
import kata.ex02.OrderHook;
import kata.ex02.model.Order;
import kata.ex02.util.ApiSender;

/**
 * @author hakiba
 */
public class CheckStockApiHook implements OrderHook<Order> {
    private ApiSender apiSender;

    @Inject
    public CheckStockApiHook(ApiSender apiSender) {
        this.apiSender = apiSender;
    }

    @Override
    public void execute(Order order) {
        order.getOrderDetails().stream()
                .filter(orderDetail -> "A".equals(orderDetail.getProductProvider().getName()))
                .forEach(orderDetail -> apiSender.checkStock(orderDetail));
    }
}
