package com.rocketscience.stapone.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the UserReviews entity.
 */
public class UserReviewsDTO implements Serializable {

    private Long id;

    private BigDecimal rating;


    private String comments;


    private Boolean isFavourite;


    private ZonedDateTime createdAt;


    private ZonedDateTime updatedAt;


    private Long reviewsId;

    private String reviewsLogin;

    private Long productsId;

    private String productsName;

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

    public Long getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Long userId) {
        this.reviewsId = userId;
    }

    public String getReviewsLogin() {
        return reviewsLogin;
    }

    public void setReviewsLogin(String userLogin) {
        this.reviewsLogin = userLogin;
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

        UserReviewsDTO userReviewsDTO = (UserReviewsDTO) o;

        if ( ! Objects.equals(id, userReviewsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserReviewsDTO{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", comments='" + comments + "'" +
            ", isFavourite='" + isFavourite + "'" +
            ", createdAt='" + createdAt + "'" +
            ", updatedAt='" + updatedAt + "'" +
            '}';
    }
}
