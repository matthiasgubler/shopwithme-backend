package ch.zhaw.swm.wall.ioc;

import ch.zhaw.swm.wall.repository.PersonRepository;
import ch.zhaw.swm.wall.repository.PostRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.impl.PersonServiceImpl;
import ch.zhaw.swm.wall.services.post.PostService;
import ch.zhaw.swm.wall.services.post.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCConfig {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PostRepository postRepository;

    @Bean
    public PersonService personService() {
        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public PostService postService() {
        return new PostServiceImpl(postRepository);
    }
}
