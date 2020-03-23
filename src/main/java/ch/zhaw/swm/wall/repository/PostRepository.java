package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PostRepository extends MongoRepository<Post, String> {
}
