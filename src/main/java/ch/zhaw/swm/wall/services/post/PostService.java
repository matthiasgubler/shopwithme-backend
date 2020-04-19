package ch.zhaw.swm.wall.services.post;

import ch.zhaw.swm.wall.model.post.*;

import java.util.List;
import java.util.Optional;

public interface PostService {

    <T extends Post> Optional<T> findById(String postId);

    Comment createCommentPost(CommentCreation comment);

    void deletePost(String postId);

    List<Post> findAllPostsByTopicId(String topicId);

    <T extends Post> List<T> findPostsByTypeAndTopicId(PostType postType, String topicId);

    List<PostStructure> findAllPostsByTopicIdStructured(String topicId);

}
