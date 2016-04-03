package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.UserReviewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserReviews and its DTO UserReviewsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserReviewsMapper {

    @Mapping(source = "reviews.id", target = "reviewsId")
    @Mapping(source = "reviews.login", target = "reviewsLogin")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    UserReviewsDTO userReviewsToUserReviewsDTO(UserReviews userReviews);

    @Mapping(source = "reviewsId", target = "reviews")
    @Mapping(source = "productsId", target = "products")
    UserReviews userReviewsDTOToUserReviews(UserReviewsDTO userReviewsDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Products productsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
