package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.post.PostType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findAllByTopicId(String topicId);

    List<Post> findAllByTopicIdOrderByCreateDateTimeAsc(String topicId);

    <T extends Post> List<T> findAllByPostTypeIn(PostType postType, List<Post> posts);

}
