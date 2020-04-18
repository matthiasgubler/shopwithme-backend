package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.contoller.exception.NotAuthorizedException;
import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.post.Comment;
import ch.zhaw.swm.wall.model.post.Post;
import ch.zhaw.swm.wall.model.post.PostStructure;
import ch.zhaw.swm.wall.model.post.PostType;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.repository.PostRepository;
import ch.zhaw.swm.wall.services.EntityIdHandler;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import ch.zhaw.swm.wall.services.post.PostService;
import ch.zhaw.swm.wall.services.topic.TopicService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ch.zhaw.swm.wall.model.person.RelationshipStatus.ACCEPTED;
import static java.util.Objects.isNull;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TopicService topicService;
    private final PersonService personService;
    private final RelationshipService relationshipService;
    private final EntityIdHandler entityIdHandler = new EntityIdHandler();

    public PostServiceImpl(PostRepository postRepository, TopicService topicService, PersonService personService, RelationshipService relationshipService) {
        this.postRepository = postRepository;
        this.topicService = topicService;
        this.personService = personService;
        this.relationshipService = relationshipService;
    }

    @Override
    public List<Post> findAllPostsByTopicId(String topicId) {
        return postRepository.findAllByTopicId(topicId);
    }

    @Override
    public <T extends Post> List<T> findPostsOfTypeByTopicId(PostType postType, String topicId) {
        return postRepository.findAllByPostTypeIn(postType, findAllPostsByTopicId(topicId));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Post> Optional<T> findPostByPostId(String postId) {
        return (Optional<T>) postRepository.findById(postId);
    }

    @Override
    public Comment createCommentPost(Comment comment) {
        String topicId = comment.getTopicId();
        String commentOwnerId = comment.getPersonId();

        if (isNull(topicId) || topicId.isEmpty()) {
            throw new IllegalArgumentException("Person or Topic not available.");
        }
        if (isNull(commentOwnerId) || commentOwnerId.isEmpty()) {
            throw new IllegalArgumentException("Person or Topic not available.");
        }

        Optional<Topic> optionalTopic = topicService.findById(topicId);
        if (!optionalTopic.isPresent()) {
            throw new NotFoundException(Topic.ENTITY_NAME, topicId);
        }
        Optional<Person> optionalCommentOwner = personService.findById(commentOwnerId);
        if (!optionalCommentOwner.isPresent()) {
            throw new NotFoundException(Person.ENTITY_NAME, commentOwnerId);
        }

        //TODO: Check if person is authenticated

        Optional<Person> optionalTopicOwner = personService.findById(optionalTopic.get().getPersonId());
        String topicOwnerId = optionalTopicOwner.get().getId();
        List<Relationship> relationships = relationshipService.findByRequestingPersonIdAndStatus(commentOwnerId, ACCEPTED);
        List<Relationship> relevantRelationship = relationships.stream()
            .filter(relationship -> relationship.getRequestedPersonId().equals(topicOwnerId))
            .collect(Collectors.toList());
        if (!commentOwnerId.equals(topicOwnerId) && relevantRelationship.isEmpty()) {
            throw new NotAuthorizedException(commentOwnerId, topicId);
        }

        return postRepository.save(comment);
    }

    @Override
    public void deleteByPostId(String postId) {
        // TODO: Determine if Posts can be deleted
        //  if yes, who can delete what posts
        //  => poster and owner of topic?
        //  if authenticated user matches
        //  personId of topic or post
        //  delete else throw
        entityIdHandler.consume(Post.ENTITY_NAME, postId, postRepository::findById, postRepository::delete);
    }

    @Override
    public List<PostStructure> findAllPostsStructured() {
        return postRepository.findAll().stream().
            map(Post::createStructure).collect(Collectors.toList());
    }

}
