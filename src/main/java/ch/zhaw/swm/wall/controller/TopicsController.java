package ch.zhaw.swm.wall.controller;

import ch.zhaw.swm.wall.model.post.PostStructure;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;
import ch.zhaw.swm.wall.services.post.PostService;
import ch.zhaw.swm.wall.services.topic.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicsController extends BasicController {

    private final TopicService topicService;
    private final PostService postService;

    private static final String URI_TOPICS = "/topics";
    private static final String URI_POSTS = "/posts";
    private static final String ID = "/{id}";

    @Autowired
    public TopicsController(TopicService topicService, PostService postService) {
        this.topicService = topicService;
        this.postService = postService;
    }

    @PostMapping(URI_TOPICS)
    public Topic newTopic(@RequestBody Topic newTopic) {
        return topicService.create(newTopic);
    }

    @GetMapping(URI_TOPICS)
    public List<Topic> all() {
        return topicService.findAll();
    }

    @PatchMapping(URI_TOPICS + ID)
    public Topic patch(@RequestBody TopicPatch topicPatch, @PathVariable String id) {
        return this.topicService.patch(topicPatch, id);
    }

    @DeleteMapping(URI_TOPICS + ID)
    public void delete(@PathVariable String id) {
        this.topicService.delete(id);
    }

    @GetMapping(URI_TOPICS + ID)
    public ResponseEntity<Topic> one(@PathVariable String id) {
        return handleSingleResourceResponse(topicService.findById(id));
    }

    @GetMapping(URI_TOPICS + ID + URI_POSTS)
    @Operation(description = "Get posts by topic id ordered by create datetime.")
    public List<PostStructure> getPostsByTopicIdStructured(@PathVariable String id) {
        return postService.findAllPostsByTopicIdStructured(id);
    }

}
