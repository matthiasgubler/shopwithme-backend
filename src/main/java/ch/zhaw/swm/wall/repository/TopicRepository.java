package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.topic.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopicRepository extends MongoRepository<Topic, String> {
}
