package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.model.post.Image;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.post.PostStructure;
import ch.zhaw.swm.wall.repository.PostRepository;
import ch.zhaw.swm.wall.services.post.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Image> findImagePostById(String id) {
        //TODO richtige Type-Check usw..
        return postRepository.findById(id).map(post -> ((Image) post));
    }

    @Override
    public List<PostStructure> findAllPostsStructured() {
        return postRepository.findAll().stream().
            map(Post::createStructure).collect(Collectors.toList());
    }

}
