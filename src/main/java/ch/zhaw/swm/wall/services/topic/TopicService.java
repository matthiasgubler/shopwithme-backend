package ch.zhaw.swm.wall.services.topic;

import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    Optional<Topic> findById(String id);

    Topic create(Topic topic);

    void delete(String id);

    List<Topic> findAll();

    List<Topic> findByPersonId(String personId);

    Topic patch(TopicPatch topicPatch, String id);

}
