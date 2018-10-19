package com.example.matirozen.printmaxtest.Model;

public class Order {
    private long orderid;
    private float orderprice;
    private String orderdetail, ordercomment, userphone;

    public Order() {
    }

    public Order(long orderid, float orderprice, String orderdetail, String ordercomment, String userphone) {
        this.orderid = orderid;
        this.orderprice = orderprice;
        this.orderdetail = orderdetail;
        this.ordercomment = ordercomment;
        this.userphone = userphone;
    }

    public String getorderdetail() {
        return orderdetail;
    }

    public void setorderdetail(String orderdetail) {
        this.orderdetail = orderdetail;
    }

    public long getorderid() {
        return orderid;
    }

    public void setorderid(long orderid) {
        this.orderid = orderid;
    }

    public float getorderprice() {
        return orderprice;
    }

    public void setorderprice(float orderprice) {
        this.orderprice = orderprice;
    }

    public String getordercomment() {
        return ordercomment;
    }

    public void setordercomment(String ordercomment) {
        this.ordercomment = ordercomment;
    }

    public String getuserphone() {
        return userphone;
    }

    public void setuserphone(String userphone) {
        this.userphone = userphone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderid +
                ", orderprice=" + orderprice +
                ", orderdetail='" + orderdetail + '\'' +
                ", ordercomment='" + ordercomment + '\'' +
                ", userphone='" + userphone + '\'' +
                '}';
    }
}
