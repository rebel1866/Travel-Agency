package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderDiscount implements Serializable {
    private static final long SerialVersionUID = 8564165415641564948L;
    private int orderDiscountId;
    private int amountOrders;
    private int discountPercents;
    private int priceFrom;
    private int priceTo;

    public OrderDiscount(){

    }

    public int getOrderDiscountId() {
        return orderDiscountId;
    }

    public void setOrderDiscountId(int orderDiscountId) {
        this.orderDiscountId = orderDiscountId;
    }

    public int getAmountOrders() {
        return amountOrders;
    }

    public void setAmountOrders(int amountOrders) {
        this.amountOrders = amountOrders;
    }

    public int getDiscountPercents() {
        return discountPercents;
    }

    public void setDiscountPercents(int discountPercents) {
        this.discountPercents = discountPercents;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    @Override
    public String toString() {
        return "OrderDiscount{" +
                "orderDiscountId=" + orderDiscountId +
                ", amountOrders=" + amountOrders +
                ", discountPercents=" + discountPercents +
                ", priceFrom=" + priceFrom +
                ", priceTo=" + priceTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDiscount that = (OrderDiscount) o;
        return orderDiscountId == that.orderDiscountId && amountOrders == that.amountOrders && discountPercents ==
                that.discountPercents && priceFrom == that.priceFrom && priceTo == that.priceTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDiscountId, amountOrders, discountPercents, priceFrom, priceTo);
    }
}
