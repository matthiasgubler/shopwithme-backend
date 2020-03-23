package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.rating.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RatingRepository extends MongoRepository<Rating, String> {
}
