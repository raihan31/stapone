package com.rocketscience.stapone.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "products")
public class Products implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    
    @Column(name = "logo_content_type")        private String logoContentType;
    @Column(name = "descriptions")
    private String descriptions;
    
    @NotNull
    @Column(name = "measured_unit", nullable = false)
    private String measuredUnit;
    
    @NotNull
    @Column(name = "unit_item", nullable = false)
    private Integer unitItem;
    
    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;
    
    @Column(name = "available_item")
    private Integer availableItem;
    
    @Column(name = "sold_item")
    private Integer soldItem;
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "shared_no")
    private Integer sharedNo;
    
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Products products = (Products) o;
        if(products.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, products.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Products{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
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
