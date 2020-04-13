package ch.zhaw.swm.wall.services.post;

import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.post.PostStructure;
import ch.zhaw.swm.wall.model.post.PostType;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostStructure> findAllPostsStructured();
    List<Post> findAllPostsByTopicId(String topicId);
    <T extends Post> List<T> findPostsOfTypeByTopicId(PostType postType, String topicId);
    <T extends Post> Optional<T> findPostByPostId(String postId);
    void deleteByPostId(String postId);

    Comment createCommentPost(Comment comment);
}
