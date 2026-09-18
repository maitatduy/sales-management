package com.salesmanager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product extends BaseEntity {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private BigDecimal price;
    private int quantity;

    private Product() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseStock(int amount) {
        if (amount > this.quantity) {
            throw new IllegalArgumentException("Số lượng trừ vượt quá tồn kho hiện có");
        }
        this.quantity -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Product: Id = %d, Name = %s, Price = %s, Quantity = %d\n", id, name, price, quantity);
    }

    public static class Builder {
        private final Product product = new Product();

        public Builder id(Long id) {
            product.id = id;
            return this;
        }

        public Builder name(String name) {
            product.name = name;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            product.categoryId = categoryId;
            return this;
        }

        public Builder categoryName(String categoryName) {
            product.categoryName = categoryName;
            return this;
        }

        public Builder price(BigDecimal price) {
            product.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            product.quantity = quantity;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            product.setCreatedAt(createdAt);
            return this;
        }

        public Product build() {
            Objects.requireNonNull(product.name, "Tên sản phẩm không được null");
            Objects.requireNonNull(product.price, "Giá sản phẩm không được null");
            return product;
        }
    }
}