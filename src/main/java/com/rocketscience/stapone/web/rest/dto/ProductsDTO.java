package com.rocketscience.stapone.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the Products entity.
 */
public class ProductsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    @Lob
    private byte[] logo;

    private String logoContentType;

    private String descriptions;


    @NotNull
    private String measuredUnit;


    @NotNull
    private Integer unitItem;


    @NotNull
    private BigDecimal price;


    private Integer availableItem;


    private Integer soldItem;


    private Boolean status;


    private Integer sharedNo;


    private ZonedDateTime createdAt;


    private ZonedDateTime updatedAt;


    private Long categoryId;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }
    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    public String getMeasuredUnit() {
        return measuredUnit;
    }

    public void setMeasuredUnit(String measuredUnit) {
        this.measuredUnit = measuredUnit;
    }
    public Integer getUnitItem() {
        return unitItem;
    }

    public void setUnitItem(Integer unitItem) {
        this.unitItem = unitItem;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getAvailableItem() {
        return availableItem;
    }

    public void setAvailableItem(Integer availableItem) {
        this.availableItem = availableItem;
    }
    public Integer getSoldItem() {
        return soldItem;
    }

    public void setSoldItem(Integer soldItem) {
        this.soldItem = soldItem;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Integer getSharedNo() {
        return sharedNo;
    }

    public void setSharedNo(Integer sharedNo) {
        this.sharedNo = sharedNo;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductsDTO productsDTO = (ProductsDTO) o;

        if ( ! Objects.equals(id, productsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductsDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", logo='" + logo + "'" +
            ", descriptions='" + descriptions + "'" +
            ", measuredUnit='" + measuredUnit + "'" +
            ", unitItem='" + unitItem + "'" +
            ", price='" + price + "'" +
            ", availableItem='" + availableItem + "'" +
            ", soldItem='" + soldItem + "'" +
            ", status='" + status + "'" +
            ", sharedNo='" + sharedNo + "'" +
            ", createdAt='" + createdAt + "'" +
            ", updatedAt='" + updatedAt + "'" +
            '}';
    }
}
