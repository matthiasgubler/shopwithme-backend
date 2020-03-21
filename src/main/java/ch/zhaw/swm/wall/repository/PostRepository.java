package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface PostRepository extends MongoRepository<Post, BigInteger> {
}
