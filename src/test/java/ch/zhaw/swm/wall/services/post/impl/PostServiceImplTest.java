package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.contoller.exception.NotAuthorizedException;
import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.TopicBuilder;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.Image;
import ch.zhaw.swm.wall.model.post.Location;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.posts.CommentBuilder;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static ch.zhaw.swm.wall.model.person.RelationshipStatus.ACCEPTED;
import static ch.zhaw.swm.wall.model.post.PostType.COMMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceImplTest {

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

    private static final String TITLE_1 = "title";
    private static final String TITLE_2 = "another title";

    private static final String COMMENT_1 = "Amet";
    private static final String COMMENT_2 = "Cras Ipsum";

    private static final String EMPTY = "";


    @Test
    void findPostsOfTypeByTopicId_shouldFindExistingCommentsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        when(postRepositoryMock.findAllByTopicId(TOPIC_ID)).thenReturn(Arrays.asList(comment1, comment2, image, location));
        when(postRepositoryMock.findAllByPostTypeIn(COMMENT, postRepositoryMock.findAllByTopicId(TOPIC_ID))).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = postService.findPostsOfTypeByTopicId(COMMENT, TOPIC_ID);

        assertThat(comments).containsExactly(comment1, comment2);
    }

    @Test
    void findAllPostsByTopicId_shouldFindExistingPostsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        when(postRepositoryMock.findAllByTopicId(TOPIC_ID)).thenReturn(Arrays.asList(comment1, comment2, image, location));

        List<Post> comments = postService.findAllPostsByTopicId(TOPIC_ID);

        assertThat(comments).containsExactly(comment1, comment2, image, location);
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenPersonIdNotSet() {
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withTitle(TITLE_1).withComment(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenTopicIdNotSet() {
        Comment comment = CommentBuilder.aComment().withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenPersonIdIsEmpty() {
        Comment comment = CommentBuilder.aComment().withTopicId(EMPTY).withTitle(TITLE_1).withComment(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenTopicIdIsEmpty() {
        Comment comment = CommentBuilder.aComment().withPersonId(EMPTY).withTitle(TITLE_1).withComment(COMMENT_1).build();

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenTopicNotExisting() {
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Topic.ENTITY_NAME + " id not found : " + TOPIC_ID);
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenPersonNotExisting() {
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).build();
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_1).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.ofNullable(topic));
        when(personServiceMock.findById(PERSON_ID_2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createCommentPost(comment))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(Person.ENTITY_NAME + " id not found : " + PERSON_ID_2);
    }

    @Test
    void createCommentPost_shouldCreateCommentWhenNoRelationshipButTopicOwnerComments() {
        Person person = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).build();
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_2).withComment(COMMENT_1).build();
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.ofNullable(topic));
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(person));
        when(relationshipServiceMock.findByRequestingPersonIdAndStatus(PERSON_ID_1, ACCEPTED)).thenReturn(Collections.emptyList());
        when(postRepositoryMock.save(comment)).thenReturn(comment);

        Comment resultComment = postService.createCommentPost(comment);

        assertThat(resultComment).isEqualTo(comment);
    }

    @Test
    void createCommentPost_shouldNotCreateCommentWhenNoValidRelationshipPresent() {
        Person topicOwner = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Person commentOwner = PersonBuilder.aPerson().withId(PERSON_ID_2).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).build();
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_1).build();
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
    void createCommentPost_shouldCreateCommentWhenValidRelationshipPresent() {
        Person topicOwner = PersonBuilder.aPerson().withId(PERSON_ID_1).build();
        Person commentOwner = PersonBuilder.aPerson().withId(PERSON_ID_2).build();
        Topic topic = TopicBuilder.aTopic().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_1).withTitle(TITLE_1).build();
        Comment comment = CommentBuilder.aComment().withTopicId(TOPIC_ID).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_1).build();
        Relationship relationship21 = relationshipAccepted(PERSON_ID_2, PERSON_ID_1);
        Relationship relationship23 = relationshipAccepted(PERSON_ID_2, PERSON_ID_3);
        List<Relationship> relationships = new LinkedList<>();
        relationships.add(relationship23);
        relationships.add(relationship21);
        when(topicServiceMock.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(personServiceMock.findById(PERSON_ID_1)).thenReturn(Optional.of(topicOwner));
        when(personServiceMock.findById(PERSON_ID_2)).thenReturn(Optional.of(commentOwner));
        when(relationshipServiceMock.findByRequestingPersonIdAndStatus(PERSON_ID_2, ACCEPTED)).thenReturn(relationships);
        when(postRepositoryMock.save(comment)).thenReturn(comment);

        Comment resultComment = postService.createCommentPost(comment);

        assertThat(resultComment).isEqualTo(comment);
    }

    private Relationship relationshipAccepted(String requestingPersonId, String requestedPersonId) {
        return new Relationship(requestingPersonId,requestedPersonId, ACCEPTED);
    }

}
