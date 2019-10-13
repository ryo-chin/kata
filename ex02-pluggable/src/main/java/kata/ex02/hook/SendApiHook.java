package kata.ex02.hook;

import com.google.inject.Inject;
import kata.ex02.OrderHook;
import kata.ex02.model.Order;
import kata.ex02.util.ApiSender;

/**
 * @author hakiba
 */
public class SendApiHook implements OrderHook {
    private ApiSender apiSender;

    @Inject
    public SendApiHook(ApiSender apiSender) {
        this.apiSender = apiSender;
    }

    @Override
    public void execute(Order order) {
        order.getOrderDetails()
                .stream()
                .filter(detail -> "A".equals(detail.getProductProvider().getName()))
                .forEach(orderDetail -> apiSender.send(orderDetail));
    }
}
