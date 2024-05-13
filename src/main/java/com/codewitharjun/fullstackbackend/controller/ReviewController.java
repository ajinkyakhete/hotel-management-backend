package com.codewitharjun.fullstackbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewitharjun.fullstackbackend.exception.ReviewNotFoundException;
import com.codewitharjun.fullstackbackend.model.Review;
import com.codewitharjun.fullstackbackend.model.User;
import com.codewitharjun.fullstackbackend.repository.HotelRepository;
import com.codewitharjun.fullstackbackend.repository.ReviewRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HotelRepository hotelRepository;
    
    @PostMapping("/review")
    Review newReview(@RequestBody Review newReview) {
        return reviewRepository.save(newReview);
    }
    
    @GetMapping("/reviews")
    List<Review> getAllReview() {
        return reviewRepository.findAll();
    }
    @GetMapping("/allreview/{userId}")
    public List<Review> getAllReviewsByUserId(@PathVariable Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }
    @GetMapping("/review/{reviewid}")
    Review getReviewById(@PathVariable Long reviewid) {
        return reviewRepository.findById(reviewid)
                .orElseThrow(() -> new ReviewNotFoundException(reviewid));
    }
    @GetMapping("/user_hotel/{hotelId}")
    public User getUserIdByHotelId(@PathVariable Long hotelId) {
        try {
            User user = reviewRepository.findUserByHotelId(hotelId);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load user");
        }
    }

    @PutMapping("/review/{reviewid}")
    Review updateReview(@RequestBody Review newReview, @PathVariable Long reviewid) {
        return reviewRepository.findById(reviewid)
                .map(review -> {
                	review.setRating(newReview.getRating());
                	review.setReviewText(newReview.getReviewText());
                    return reviewRepository.save(review);
                }).orElseThrow(() -> new ReviewNotFoundException(reviewid));
    }

    @DeleteMapping("/review/{reviewid}")
    String deleteReview(@PathVariable Long reviewid){
        if(!reviewRepository.existsById(reviewid)){
            throw new ReviewNotFoundException(reviewid);
        }
        reviewRepository.deleteById(reviewid);
        return  "Review with id "+reviewid+" has been deleted success.";
    }
}

