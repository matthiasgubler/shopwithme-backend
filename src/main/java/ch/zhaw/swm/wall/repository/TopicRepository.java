package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.topic.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TopicRepository extends MongoRepository<Topic, String> {

    List<Topic> findByPersonId(String personId);

}
