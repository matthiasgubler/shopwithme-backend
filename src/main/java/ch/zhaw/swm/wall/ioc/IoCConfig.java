package ch.zhaw.swm.wall.ioc;

import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.repository.PostRepository;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.repository.TopicRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import ch.zhaw.swm.wall.services.person.impl.PersonServiceImpl;
import ch.zhaw.swm.wall.services.person.impl.RelationshipServiceImpl;
import ch.zhaw.swm.wall.services.person.impl.RelationshipStateChangeStrategyProvider;
import ch.zhaw.swm.wall.services.post.PostService;
import ch.zhaw.swm.wall.services.post.impl.PostServiceImpl;
import ch.zhaw.swm.wall.services.topic.TopicService;
import ch.zhaw.swm.wall.services.topic.impl.TopicServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCConfig {

    @Bean
    public PersonService personService(PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public RelationshipService relationshipService(RelationshipRepository relationshipRepository, PersonService personService) {
        return new RelationshipServiceImpl(relationshipRepository, personService, relationshipStateChangeStrategyProvider(relationshipRepository));
    }

    @Bean
    public TopicService topicService(TopicRepository topicRepository, PersonService personService) {
        return new TopicServiceImpl(topicRepository, personService);
    }

    @Bean
    public PostService postService(PostRepository postRepository, TopicService topicService, PersonService personService, RelationshipService relationshipService) {
        return new PostServiceImpl(postRepository, topicService, personService, relationshipService);
    }

    @Bean
    public RelationshipStateChangeStrategyProvider relationshipStateChangeStrategyProvider(RelationshipRepository relationshipRepository) {
        return new RelationshipStateChangeStrategyProvider(relationshipRepository);
    }

}
