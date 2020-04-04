package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;
import ch.zhaw.swm.wall.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicsController extends BasicController {
    private final TopicService topicService;

    @Autowired
    public TopicsController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topics")
    public Topic newTopic(@RequestBody Topic newTopic) {
        return topicService.create(newTopic);
    }

    @GetMapping("/topics")
    public List<Topic> all() {
        return topicService.findAll();
    }

    @PatchMapping("/topics/{id}")
    public Topic patch(@RequestBody TopicPatch topicPatch, @PathVariable String id) {
        return this.topicService.patch(topicPatch, id);
    }

    @DeleteMapping("/topics/{id}")
    public void delete(@PathVariable String id) {
        this.topicService.delete(id);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> one(@PathVariable String id) {
        return handleSingleResourceResponse(topicService.findById(id));
    }

}
