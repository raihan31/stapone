package com.rocketscience.stapone.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the OrderDetails entity.
 */
public class OrderDetailsDTO implements Serializable {

    private Long id;

    private Integer noOfProducts;


    private Long ordersId;

    private String ordersDescriptions;

    private Long productsId;

    private String productsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(Integer noOfProducts) {
        this.noOfProducts = noOfProducts;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersDescriptions() {
        return ordersDescriptions;
    }

    public void setOrdersDescriptions(String ordersDescriptions) {
        this.ordersDescriptions = ordersDescriptions;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productsId) {
        this.productsId = productsId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailsDTO orderDetailsDTO = (OrderDetailsDTO) o;

        if ( ! Objects.equals(id, orderDetailsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
            "id=" + id +
            ", noOfProducts='" + noOfProducts + "'" +
            '}';
    }
}
