package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.Image;
import ch.zhaw.swm.wall.model.post.Location;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.posts.CommentBuilder;
import ch.zhaw.swm.wall.model.posts.ImageBuilder;
import ch.zhaw.swm.wall.model.posts.LocationBuilder;
import ch.zhaw.swm.wall.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static ch.zhaw.swm.wall.model.post.PostType.COMMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private static final String TOPIC_ID_1 = "1";
    private static final String TOPIC_ID_2 = "5";

    private static final String PERSON_ID_1 = "4";
    private static final String PERSON_ID_2 = "9";

    private static final String TITLE_1 = "title";
    private static final String TITLE_2 = "another title";

    private static final String COMMENT_1 = "Amet";
    private static final String COMMENT_2 = "Cras Ipsum";
    private static final String COMMENT_3 = "Donec id elit non mi porta gravida at eget metus. Praesent commodo " +
        "cursus magna, vel scelerisque nisl consectetur et. Integer posuere erat a ante venenatis dapibus posuere" +
        " velit aliquet. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Aenean eu leo quam." +
        " Pellentesque ornare sem lacinia quam venenatis vestibulum.";

    private static final String EMPTY = "";


    @Test
    void shouldFindExistingCommentsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        when(postRepository.findAllByTopicId(TOPIC_ID_1)).thenReturn(Arrays.asList(comment1, comment2, image, location));
        when(postRepository.findAllByPostTypeIn(COMMENT, postRepository.findAllByTopicId(TOPIC_ID_1))).thenReturn(Arrays.asList(comment1, comment2));


        List<Comment> comments = postService.findPostsOfTypeByTopicId(COMMENT, TOPIC_ID_1);

        assertThat(comments).containsExactly(comment1, comment2);
    }

    @Test
    void shouldFindExistingPostsByTopicId() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();
        Comment comment2 = CommentBuilder.aComment().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).withComment(COMMENT_2).build();
        Image image = ImageBuilder.anImage().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        Location location = LocationBuilder.aLocation().withTopicId(TOPIC_ID_1).withPersonId(PERSON_ID_2).withTitle(TITLE_2).build();
        when(postRepository.findAllByTopicId(TOPIC_ID_1)).thenReturn(Arrays.asList(comment1, comment2, image, location));

        List<Post> comments = postService.findAllPostsByTopicId(TOPIC_ID_1);

        assertThat(comments).containsExactly(comment1, comment2, image, location);
    }

    @Test
    void shouldNotCreateCommentWhenPersonIdNotSet() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(TOPIC_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();


        assertThatThrownBy(() -> postService.createCommentPost(comment1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void shouldNotCreateCommentWhenTopicIdNotSet() {
        Comment comment1 = CommentBuilder.aComment().withPersonId(PERSON_ID_1).withTitle(TITLE_1).withComment(COMMENT_1).build();


        assertThatThrownBy(() -> postService.createCommentPost(comment1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void shouldNotCreateCommentWhenPersonIdIsEmpty() {
        Comment comment1 = CommentBuilder.aComment().withTopicId(EMPTY).withTitle(TITLE_1).withComment(COMMENT_1).build();


        assertThatThrownBy(() -> postService.createCommentPost(comment1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    @Test
    void shouldNotCreateCommentWhenTopicIdIsEmpty() {
        Comment comment1 = CommentBuilder.aComment().withPersonId(EMPTY).withTitle(TITLE_1).withComment(COMMENT_1).build();


        assertThatThrownBy(() -> postService.createCommentPost(comment1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Person or Topic not available.");
    }

    //TODO: extend tests for createCommentPost

}
