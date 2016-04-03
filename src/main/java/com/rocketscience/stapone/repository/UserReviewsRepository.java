package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.UserReviews;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserReviews entity.
 */
public interface UserReviewsRepository extends JpaRepository<UserReviews,Long> {

    @Query("select userReviews from UserReviews userReviews where userReviews.reviews.login = ?#{principal.username}")
    List<UserReviews> findByReviewsIsCurrentUser();

}
