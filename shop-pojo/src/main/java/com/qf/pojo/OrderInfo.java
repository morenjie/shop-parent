package com.qf.pojo;

public class OrderInfo {

    private String orderItem;
    private TbOrder tbOrder;
    private TbOrderShipping tbOrderShipping;

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public TbOrder getTbOrder() {
        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }

    public TbOrderShipping getTbOrderShipping() {
        return tbOrderShipping;
    }

    public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
        this.tbOrderShipping = tbOrderShipping;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderItem='" + orderItem + '\'' +
                ", tbOrder=" + tbOrder +
                ", tbOrderShipping=" + tbOrderShipping +
                '}';
    }
}
