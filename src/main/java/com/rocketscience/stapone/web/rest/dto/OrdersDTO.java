package com.rocketscience.stapone.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Orders entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    private String descriptions;


    private BigDecimal totalPrice;


    private Integer totalProducts;


    private Boolean isViewed;


    private Boolean isDelivered;


    private Boolean isReceived;


    private BigDecimal totalMoneyReceived;


    private Boolean isPaid;


    private String receivedDocument;


    private Boolean isCanceled;


    private Boolean isTaken;


    private ZonedDateTime createdAt;


    private ZonedDateTime updatedAt;


    private Long ordersProductsId;

    private String ordersProductsLogin;

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

    public Long getOrdersProductsId() {
        return ordersProductsId;
    }

    public void setOrdersProductsId(Long userId) {
        this.ordersProductsId = userId;
    }

    public String getOrdersProductsLogin() {
        return ordersProductsLogin;
    }

    public void setOrdersProductsLogin(String userLogin) {
        this.ordersProductsLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;

        if ( ! Objects.equals(id, ordersDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
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
