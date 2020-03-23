package ch.zhaw.swm.wall.services.post;

import ch.zhaw.swm.wall.model.post.Image;
import ch.zhaw.swm.wall.model.post.PostStructure;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Image> findImagePostById(String id);
    List<PostStructure> findAllPostsStructured();
}
