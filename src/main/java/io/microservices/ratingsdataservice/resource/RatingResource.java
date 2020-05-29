package io.microservices.ratingsdataservice.resource;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.ratingsdataservice.model.Rating;
import io.microservices.ratingsdataservice.model.UserRating;
import io.microservices.ratingsdataservice.repsitory.MovieRatingRepository;


@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	

@Autowired	
MovieRatingRepository movieRatingRepository;

private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RatingResource.class);

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		logger.info("inside getRating");
		return new Rating(movieId,4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getRatingForUsers(@PathVariable("userId") String userId) {
		logger.info("inside getRatingForUsers");
		List<Rating> ratings = movieRatingRepository.findByUserId(userId);
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
	
	@RequestMapping("/user/{userId}")
	public Rating getRatingForUsers1(@PathVariable("userId") String userId) {
		logger.info("inside getRatingForUsers1");
		Rating ratings = movieRatingRepository.findById(userId).orElse(null);
		return ratings;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/users")
	public void addRating(@RequestBody Rating rating) {
		logger.info("inside addRating");
		movieRatingRepository.save(rating);
	}


}
