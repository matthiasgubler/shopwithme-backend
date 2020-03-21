package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.rating.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface RatingRepository extends MongoRepository<Rating, BigInteger> {
}
