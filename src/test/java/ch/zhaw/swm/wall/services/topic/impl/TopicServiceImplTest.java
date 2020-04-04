package ch.zhaw.swm.wall.services.topic.impl;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.Status;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;
import ch.zhaw.swm.wall.repository.TopicRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TopicServiceImplTest {

    public static final String PERSON_ID = "personId";
    public static final String TOPIC_ID = "topicId";
    @Mock
    private TopicRepository topicRepositoryMock;

    @Mock
    private PersonService personService;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void create_matching_person() {
        when(personService.findById(PERSON_ID)).thenReturn(Optional.of(new Person()));

        Topic topic = new Topic();
        topic.setTitle("MyTopic");
        topic.setPersonId(PERSON_ID);

        topicService.create(topic);
        verify(topicRepositoryMock).save(topic);
    }

    @Test()
    void create_no_matching_person() {
        assertThrows(NotFoundException.class, () -> {

            when(personService.findById(PERSON_ID)).thenReturn(Optional.empty());

            Topic topic = new Topic();
            topic.setTitle("MyTopic");
            topic.setPersonId(PERSON_ID);

            topicService.create(topic);
        });
    }

    @Test
    void patch_topic_not_found() {
        assertThrows(NotFoundException.class, () -> {
            TopicPatch topicPatch = new TopicPatch();
            topicPatch.setStatus(Status.ARCHIVED);

            topicService.patch(topicPatch, TOPIC_ID);
        });
    }

    @Test
    void patch_topic_status() {
        Topic topic = new Topic();
        topic.setId(TOPIC_ID);
        topic.setTitle("Le Title");

        when(topicRepositoryMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));

        TopicPatch topicPatch = new TopicPatch();
        topicPatch.setStatus(Status.ARCHIVED);

        topicService.patch(topicPatch, TOPIC_ID);

        verify(topicRepositoryMock).save(topic);
        assertEquals(Status.ARCHIVED, topic.getStatus());
    }
}
