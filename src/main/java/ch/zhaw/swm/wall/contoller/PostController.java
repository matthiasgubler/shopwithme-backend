package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.PostStructure;
import ch.zhaw.swm.wall.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/comments/{id}")
    public Comment getComment(@PathVariable BigInteger id) {
        return new Comment();
    }

    @GetMapping("/posts")
    public List<PostStructure> getPosts() {
        return postService.findAllPostsStructured();
    }
}
