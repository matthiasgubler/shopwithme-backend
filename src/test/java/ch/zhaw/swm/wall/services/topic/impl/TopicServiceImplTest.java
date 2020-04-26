package ch.zhaw.swm.wall.services.topic.impl;

import ch.zhaw.swm.wall.controller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.Status;
import ch.zhaw.swm.wall.model.TopicBuilder;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;
import ch.zhaw.swm.wall.repository.TopicRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TopicServiceImplTest {

    public static final String PERSON_ID = "personId";
    public static final String TOPIC_ID_1 = "1";
    public static final String TOPIC_ID_2 = "2";

    @Mock
    private TopicRepository topicRepositoryMock;

    @Mock
    private PersonService personService;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void create_matching_person() {
        when(personService.findById(PERSON_ID)).thenReturn(Optional.of(new Person("tim", "tim@bluewin.ch")));

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

            topicService.patch(topicPatch, TOPIC_ID_1);
        });
    }

    @Test
    void patch_topic_status() {
        Topic topic = new Topic();
        topic.setId(TOPIC_ID_1);
        topic.setTitle("Le Title");

        when(topicRepositoryMock.findById(TOPIC_ID_1)).thenReturn(Optional.of(topic));

        TopicPatch topicPatch = new TopicPatch();
        topicPatch.setStatus(Status.ARCHIVED);

        topicService.patch(topicPatch, TOPIC_ID_1);

        verify(topicRepositoryMock).save(topic);
        assertEquals(Status.ARCHIVED, topic.getStatus());
    }

    @Test
    void findByPersonId_no_matching_person() {
        when(personService.findById(PERSON_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> topicService.findByPersonId(PERSON_ID))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found : " + PERSON_ID);
    }

    @Test
    void findByPersonId_matching_person() {
        Person person = PersonBuilder.aPerson().withId(PERSON_ID).withUsername("tim").withEmail("tim@bluewin.ch").build();
        Topic topic1 = TopicBuilder.aTopic().withTopicId(TOPIC_ID_1).withTitle("A Title").withPersonId(PERSON_ID).build();
        Topic topic2 = TopicBuilder.aTopic().withTopicId(TOPIC_ID_2).withTitle("Another Title").withPersonId(PERSON_ID).build();
        List<Topic> topicList = Arrays.asList(topic1, topic2);
        when(personService.findById(PERSON_ID)).thenReturn(Optional.of(person));
        when(topicRepositoryMock.findByPersonId(PERSON_ID)).thenReturn(topicList);

        List<Topic> topicsResult = topicService.findByPersonId(PERSON_ID);

        assertThat(topicsResult).isEqualTo(topicList);
    }

}
