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
 * A UserReviews.
 */
@Entity
@Table(name = "user_reviews")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userreviews")
public class UserReviews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rating", precision=10, scale=2)
    private BigDecimal rating;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "is_favourite")
    private Boolean isFavourite;
    
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "reviews_id")
    private User reviews;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Products products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRating() {
        return rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }
    
    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
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

    public User getReviews() {
        return reviews;
    }

    public void setReviews(User user) {
        this.reviews = user;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserReviews userReviews = (UserReviews) o;
        if(userReviews.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userReviews.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserReviews{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", comments='" + comments + "'" +
            ", isFavourite='" + isFavourite + "'" +
            ", createdAt='" + createdAt + "'" +
            ", updatedAt='" + updatedAt + "'" +
            '}';
    }
}
