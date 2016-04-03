package com.rocketscience.stapone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orders")
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descriptions")
    private String descriptions;
    
    @Column(name = "total_price", precision=10, scale=2)
    private BigDecimal totalPrice;
    
    @Column(name = "total_products")
    private Integer totalProducts;
    
    @Column(name = "is_viewed")
    private Boolean isViewed;
    
    @Column(name = "is_delivered")
    private Boolean isDelivered;
    
    @Column(name = "is_received")
    private Boolean isReceived;
    
    @Column(name = "total_money_received", precision=10, scale=2)
    private BigDecimal totalMoneyReceived;
    
    @Column(name = "is_paid")
    private Boolean isPaid;
    
    @Column(name = "received_document")
    private String receivedDocument;
    
    @Column(name = "is_canceled")
    private Boolean isCanceled;
    
    @Column(name = "is_taken")
    private Boolean isTaken;
    
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "orders_products_id")
    private User ordersProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }
    
    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Boolean getIsViewed() {
        return isViewed;
    }
    
    public void setIsViewed(Boolean isViewed) {
        this.isViewed = isViewed;
    }

    public Boolean getIsDelivered() {
        return isDelivered;
    }
    
    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Boolean getIsReceived() {
        return isReceived;
    }
    
    public void setIsReceived(Boolean isReceived) {
        this.isReceived = isReceived;
    }

    public BigDecimal getTotalMoneyReceived() {
        return totalMoneyReceived;
    }
    
    public void setTotalMoneyReceived(BigDecimal totalMoneyReceived) {
        this.totalMoneyReceived = totalMoneyReceived;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
    
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getReceivedDocument() {
        return receivedDocument;
    }
    
    public void setReceivedDocument(String receivedDocument) {
        this.receivedDocument = receivedDocument;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }
    
    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public Boolean getIsTaken() {
        return isTaken;
    }
    
    public void setIsTaken(Boolean isTaken) {
        this.isTaken = isTaken;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getOrdersProducts() {
        return ordersProducts;
    }

    public void setOrdersProducts(User user) {
        this.ordersProducts = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if(orders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + id +
            ", descriptions='" + descriptions + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", totalProducts='" + totalProducts + "'" +
            ", isViewed='" + isViewed + "'" +
            ", isDelivered='" + isDelivered + "'" +
            ", isReceived='" + isReceived + "'" +
            ", totalMoneyReceived='" + totalMoneyReceived + "'" +
            ", isPaid='" + isPaid + "'" +
            ", receivedDocument='" + receivedDocument + "'" +
            ", isCanceled='" + isCanceled + "'" +
            ", isTaken='" + isTaken + "'" +
            ", createdAt='" + createdAt + "'" +
            ", updatedAt='" + updatedAt + "'" +
            '}';
    }
}
