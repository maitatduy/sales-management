package com.salesmanager.model;

import com.salesmanager.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long userId;
    private String userFullName;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderDetail> details = new ArrayList<>();

    private Order() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public void recalculateTotal() {
        this.totalAmount = details.stream()
                .map(OrderDetail::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addDetail(OrderDetail detail) {
        this.details.add(detail);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Order: Id = %d, Customer = %s, Status = %s, total = %s, items = %d\n",
                id, customerName, status, totalAmount, details.size());
    }

    public static class Builder {
        private final Order order = new Order();

        public Builder id(Long id) {
            order.id = id;
            return this;
        }

        public Builder customerId(Long customerId) {
            order.customerId = customerId;
            return this;
        }

        public Builder customerName(String customerName) {
            order.customerName = customerName;
            return this;
        }

        public Builder userId(Long userId) {
            order.userId = userId;
            return this;
        }

        public Builder userFullName(String userFullName) {
            order.userFullName = userFullName;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            order.orderDate = orderDate;
            return this;
        }

        public Builder status(OrderStatus status) {
            order.status = status;
            return this;
        }

        public Builder details(List<OrderDetail> details) {
            order.details = details;
            return this;
        }

        public Order build() {
            if (order.orderDate == null) {
                order.orderDate = LocalDateTime.now();
            }
            if (order.status == null) {
                order.status = OrderStatus.PENDING;
            }
            order.recalculateTotal();
            return order;
        }
    }
}
