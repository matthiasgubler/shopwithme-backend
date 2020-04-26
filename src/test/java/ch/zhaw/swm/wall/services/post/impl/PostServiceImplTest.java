package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.controller.exception.NotAuthorizedException;
import ch.zhaw.swm.wall.controller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.TopicBuilder;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.post.*;
import ch.zhaw.swm.wall.model.posts.CommentBuilder;
import ch.zhaw.swm.wall.model.posts.CommentCreationBuilder;
import ch.zhaw.swm.wall.model.posts.ImageBuilder;
import ch.zhaw.swm.wall.model.posts.LocationBuilder;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.repository.PostRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import ch.zhaw.swm.wall.services.topic.TopicService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static ch.zhaw.swm.wall.model.person.RelationshipStatus.ACCEPTED;
import static ch.zhaw.swm.wall.model.post.PostType.COMMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceImplTest {

    @Mock
    private PostRepository postRepositoryMock;

    @Mock
    private TopicService topicServiceMock;

    @Mock
    private PersonService personServiceMock;

    @Mock
    private RelationshipService relationshipServiceMock;

    @InjectMocks
    private PostServiceImpl postService;

    private static final String TOPIC_ID = "1";

    private static final String PERSON_ID_1 = "4";
    private static final String PERSON_ID_2 = "9";
    private static final String PERSON_ID_3 = "11";

    private static final String TITLE = "title";

    private static final String COMMENT_1 = "Amet";
    private static final String COMMENT_2 = "Cras Ipsum";

    private static final String INVALID_ID = "";

    @Test
    void shouldFindExistingCommentsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).build();
        when(postRepositoryMock.findAllByTopicId(TOPIC_ID)).thenReturn(Arrays.asList(comment1, comment2, image, location));
        when(postRepositoryMock.findAllByPostTypeIn(COMMENT, postRepositoryMock.findAllByTopicId(TOPIC_ID))).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = postService.findPostsByTypeAndTopicId(COMMENT, TOPIC_ID);

        assertThat(comments).containsExactly(comment1, comment2);
    }

    @Test
    void shouldFindExistingPostsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).build();
        when(postRepositoryMock.findAllByTopicId(TOPIC_ID)).thenReturn(Arrays.asList(comment1, comment2, image, location));

        List<Post> comments = postService.findAllPostsByTopicId(TOPIC_ID);

        assertThat(comments).containsExactly(comment1, comment2, image, location);
    }

    @Test
    void shouldNotCreateCommentWhenPersonIdNotSet() {
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withMessage(COMMENT_1).build();
        Topic topic = TopicBuilder.aTopic().withTitle(TITLE).withPersonId(PERSON_ID_2).withTopicId(TOPIC_ID).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found");
    }

    @Test
    void shouldNotCreateCommentWhenTopicIdNotSet() {
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Topic.ENTITY_NAME + " id not found");
}

    @Test
    void shouldNotCreateCommentWhenPersonIdIsInvalid() {
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(INVALID_ID).withMessage(COMMENT_1).build();
        Topic topic = TopicBuilder.aTopic().withTitle(TITLE).withPersonId(PERSON_ID_2).withTopicId(TOPIC_ID).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found");
    }

    @Test
    void shouldNotCreateCommentWhenTopicIdIsInvalid() {
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(INVALID_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Topic.ENTITY_NAME + " id not found");
    }

    @Test
    void shouldNotCreateCommentWhenTopicNotExisting() {
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(PERSON_ID_1).build()));
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Topic.ENTITY_NAME + " id not found : " + TOPIC_ID);
    }

    @Test
    void shouldNotCreateCommentWhenPersonNotExisting() {
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE).build();
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_1).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.ofNullable(topic));
        when(personServiceMock.findById(PERSON_ID_2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createCommentPost(commentCreation))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found : " + PERSON_ID_2);
    }

    @Test
    void shouldCreateCommentWhenNoRelationshipButTopicOwnerCreatesComment() {
        Person person = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE).build();
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();
        Comment comment = new Comment(commentCreation);
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.ofNullable(topic));
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(person));
        when(relationshipServiceMock.findByRequestingPersonIdAndStatus(PERSON_ID_1, ACCEPTED)).thenReturn(Collections.emptyList());
        when(postRepositoryMock.save(Mockito.any())).thenReturn(comment);

        Comment resultComment = postService.createCommentPost(commentCreation);

        assertThat(resultComment).isEqualTo(comment);
    }

    @Test
    void shouldNotCreateCommentWhenNoValidRelationshipPresent() {
        Person topicOwner = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Person commentOwner = PersonBuilder.aPerson().withId(PERSON_ID_2).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE).build();
        CommentCreation comment = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_1).build();
        Relationship relationship = relationshipAccepted(PERSON_ID_2, PERSON_ID_3);
        List<Relationship> relationships = new LinkedList<>();
        relationships.add(relationship);
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(topicOwner));
        when(personServiceMock.findById(PERSON_ID_2)).thenReturn(Optional.of(commentOwner));
        when(relationshipServiceMock.findByRequestedPersonIdAndStatus(PERSON_ID_2, ACCEPTED)).thenReturn(relationships);

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(NotAuthorizedException.class)
            .hasMessageContaining("Person with id " + PERSON_ID_2 + " not authorized to post in Topic: " + TOPIC_ID);
    }

    @Test
    void shouldCreateCommentWhenValidRelationshipPresent() {
        Person topicOwner = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Person commentOwner = PersonBuilder.aPerson().withId(PERSON_ID_2).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE).build();
        CommentCreation commentCreation = CommentCreationBuilder.aCommentCreation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_1).build();
        Comment commentToSave = new Comment(commentCreation);
        Relationship relationship21 = relationshipAccepted(PERSON_ID_2, PERSON_ID_1);
        Relationship relationship23 = relationshipAccepted(PERSON_ID_2, PERSON_ID_3);
        List<Relationship> relationships = new LinkedList<>();
        relationships.add(relationship23);
        relationships.add(relationship21);
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(topicOwner));
        when(personServiceMock.findById(PERSON_ID_2)).thenReturn(Optional.of(commentOwner));
        when(relationshipServiceMock.findByRequestingPersonIdAndStatus(PERSON_ID_2, ACCEPTED)).thenReturn(relationships);
        when(postRepositoryMock.save(Mockito.any())).thenReturn(commentToSave);

        Comment resultComment = postService.createCommentPost(commentCreation);

        assertThat(resultComment).isEqualTo(commentToSave);
    }

    @Test
    void shouldThrowExceptionWhenPersonNotFound() {
        Location location = new Location();
        location.setPersonId("99");
        Double[] locationCoord = new Double[2];
        locationCoord[0] = 1.0;
        locationCoord[1] = 2.0;
        location.setLocation(locationCoord);
        when(personServiceMock.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createLocationPost(location))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found");
    }

    @Test
    void shouldReturnOrderedPostStructure() {
        Comment comment1 = CommentBuilder.aComment().withPostId("10").withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withMessage(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withPostId("20").withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withMessage(COMMENT_2).build();
        comment1.setCreateDateTime(LocalDateTime.now().minusDays(1));
        comment2.setCreateDateTime(LocalDateTime.now());
        List<PostStructure> expectedPostStructure = Arrays.asList(comment1.createStructure(), comment2.createStructure());
        PostStructure expectedPostStructure0 = expectedPostStructure.get(0);
        PostStructure expectedPostStructure1 = expectedPostStructure.get(1);
        when(postRepositoryMock.findAllByTopicIdOrderByCreateDateTimeAsc(TOPIC_ID)).thenReturn(Arrays.asList(comment1, comment2));

        List<PostStructure> resultPostStructure = postService.findAllPostsByTopicIdStructured(TOPIC_ID);

        PostStructure resultPostStructure0 = resultPostStructure.get(0);
        PostStructure resultPostStructure1 = resultPostStructure.get(1);
        assertThat(resultPostStructure).hasSameSizeAs(expectedPostStructure);
        assertThat(resultPostStructure0).isEqualToComparingFieldByField(expectedPostStructure0);
        assertThat(resultPostStructure1).isEqualToComparingFieldByField(expectedPostStructure1);
    }

    private Relationship relationshipAccepted(String requestingPersonId, String requestedPersonId) {
        return new Relationship(requestingPersonId,requestedPersonId, ACCEPTED);
    }

}
