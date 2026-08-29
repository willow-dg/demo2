package com.example.demo.services;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${WebsiteUrl}")
    private String websiteUrl;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())/*可以直接填写 网站网址 */
                    .setCancelUrl(websiteUrl + "/cancel.html");/*但是实际情况中我们会选择将网址存储 在我们的配置文件中 */

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());

            return new CheckoutSession(session.getUrl());
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new PaymentException();
        }
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item)).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice())
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
