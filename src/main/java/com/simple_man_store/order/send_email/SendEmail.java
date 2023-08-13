package com.simple_man_store.order.send_email;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.order_detail.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Set;

@Service
public class SendEmail {
    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String email, Order order, Set<OrderDetail> orderDetailSet) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Simple Man");

        String htmlContent = "<h1>Đơn hàng từ Simple Man</h1>" +
                "<p>Kính gửi khách hàng:" + order.getName() + "</p>" +
                "<p>Dear Mr/Ms</p>" +
                "<p>Tôi xin gửi lời chào trân trọng đến bạn từ Simple Man. Tôi xin thông báo rằng thông tin của bạn đã được lưu lại thành công trong hệ thống của chúng tôi. Chúng tôi xin cam đoan rằng thông tin cá nhân của bạn sẽ được bảo mật và sử dụng duy nhất cho các mục đích liên quan đến dịch vụ và sản phẩm của chúng tôi.</p>"
                + "<p>Thông tin bao gồm: </p>"
                + "<p>Tên: " + order.getName() + " </p>"
                + "<p>Số điện thoạn: " + order.getPhone_number() + " </p>"
                + "<p>Email: " + order.getEmail() + "</p>"
                + "<p>Địa chỉ: " + order.getAddress() + " </p>"
                + "<p>Ngày đặt hàng: " + order.getOrder_date() + "</p>"
                + "<p>Đơn hàng gồm </p>"
                + "<table style=\"border: 1px solid black;border-collapse: collapse\">\n" +
                "  <tr style=\"border: 1px solid black;border-collapse: collapse\">\n" +
                "    <th style=\"border: 1px solid black;border-collapse: collapse\">STT</th>\n" +
                "    <th style=\"border: 1px solid black;border-collapse: collapse\">Tên sản phẩm</th>\n" +
                "    <th style=\"border: 1px solid black;border-collapse: collapse\">Size</th>\n" +
                "    <th style=\"border: 1px solid black;border-collapse: collapse\">Số lượng</th>\n" +
                "    <th style=\"border: 1px solid black;border-collapse: collapse\">Giá</th>\n" +
                "  </tr>\n";
        int n = 1;
        int total = 0;
        for (OrderDetail orderDetail : orderDetailSet) {
            htmlContent +=
                    "  <tr style=\"border: 1px solid black;border-collapse: collapse\">\n" +
                            "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + (n++) + "</td>\n" +
                            "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + orderDetail.getProduct().getName() + "</td>\n" +
                            "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + orderDetail.getSize() + "</td>\n" +
                            "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + orderDetail.getQuantity() + "</td>\n" +
                            "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + orderDetail.getPrice() + "</td>\n" +
                            "  </tr>\n";
            total += (orderDetail.getQuantity() * orderDetail.getPrice());
        }

        htmlContent += "<tr style=\"border: 1px solid black;border-collapse: collapse\">\n" +
                "    <td style=\"border: 1px solid black;border-collapse: collapse; text-align: right\" colspan=\"4\"><strong>Tổng</strong></td>\n" +
                "    <td style=\"border: 1px solid black;border-collapse: collapse\">" + total + "đ </td>\n" +
                "  </tr>" +
                "</table>"+
        "<p>Một lần nữa, chúng tôi xin cam đoan rằng thông tin của bạn sẽ được bảo mật tuyệt đối và chỉ được sử dụng cho các mục đích trong phạm vi quan hệ khách hàng với chúng tôi. Chúng tôi xin cảm ơn sự tin tưởng và ủng hộ của bạn.</p>\n" +
                "<span>Trân trọng,</span>\n" +
                "<H4 style=\"font-family: 'Saira', sans-serif;\">SIMPLE MAN</H4>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
