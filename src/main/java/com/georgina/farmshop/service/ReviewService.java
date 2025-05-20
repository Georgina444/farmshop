package com.georgina.farmshop.service;

import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.Review;
import com.georgina.farmshop.model.User;
import com.georgina.farmshop.model.request.CreateReviewRequest;


import java.util.List;

public interface ReviewService {

  Review createReview(CreateReviewRequest req,
      User user,
      Product product);

  List<Review> getReviewByProductId(Long productId);

  Review updateReview(Long reviewId, String reviewText, double rating, Long userId)
      throws Exception;

  void deleteReview(Long reviewId, Long userId) throws Exception;

  Review getReviewById(Long reviewId) throws Exception;
}
