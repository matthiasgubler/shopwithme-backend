package ch.zhaw.swm.wall.controller;

import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.CommentCreation;
import ch.zhaw.swm.wall.model.post.Location;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController extends BasicController {

    private final PostService postService;
    private static final String URI_POSTS = "/posts";
    private static final String URI_COMMENTS = "/comments";
    private static final String URI_LOCATION = "/locations";
    private static final String ID = "/{id}";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(URI_POSTS + URI_COMMENTS)
    public Comment newComment(@RequestBody CommentCreation newComment) {
        return postService.createCommentPost(newComment);
    }

    @GetMapping(URI_POSTS + ID)
    public <T extends Post> ResponseEntity<T> one(@PathVariable String id) {
        return handleSingleResourceResponse(postService.findById(id));
    }

    @GetMapping(URI_POSTS + URI_LOCATION + ID)
    public ResponseEntity<Location> findLocation(String id) {
        return handleSingleResourceResponse(postService.findLocationById(id));
    }

    @PostMapping(URI_POSTS + URI_LOCATION)
    public Location newLocation(@RequestBody Location location) {
        return postService.createLocationPost(location);
    }

}
