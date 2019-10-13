package kata.ex02.hook;

import com.google.inject.Inject;
import kata.ex02.OrderHook;
import kata.ex02.model.Order;
import kata.ex02.util.MailSender;

/**
 * @author hakiba
 */
public class SendEmailHook implements OrderHook {
    private MailSender mailSender;

    @Inject
    public SendEmailHook(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(Order order) {
        order.getOrderDetails().stream()
                .filter(orderDetail -> "B".equals(orderDetail.getProductProvider().getName()))
                .forEach(orderDetail -> mailSender.send(orderDetail));
    }
}
