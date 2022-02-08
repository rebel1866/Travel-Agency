package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    private static final long SerialVersionUID = 7416541564156146L;
    private int orderId;
    private User user;
    private Tour tour;
    private String orderStatus;
    private OrderDiscount discount;
    private int finalPrice;
    private String comment;

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(OrderDiscount discount) {
        this.discount = discount;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", tour=" + tour +
                ", orderStatus='" + orderStatus + '\'' +
                ", discount=" + discount +
                ", finalPrice=" + finalPrice +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && finalPrice == order.finalPrice && Objects.equals(user, order.user) && Objects.
                equals(tour, order.tour) && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(discount,
                order.discount) && Objects.equals(comment, order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, tour, orderStatus, discount, finalPrice, comment);
    }
}
