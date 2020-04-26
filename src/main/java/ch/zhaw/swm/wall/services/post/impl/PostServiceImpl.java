package ch.zhaw.swm.wall.services.post.impl;

import ch.zhaw.swm.wall.controller.exception.NotAuthorizedException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.post.*;
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
    @SuppressWarnings("unchecked")
    public <T extends Post> Optional<T> findById(String postId) {
        return (Optional<T>) postRepository.findById(postId);
    }

    @Override
    public Comment createCommentPost(CommentCreation commentCreation) {
        String topicId = commentCreation.getTopicId();
        String commentOwnerId = commentCreation.getPersonId();

        entityIdHandler.checkExisting(Topic.ENTITY_NAME, topicId, topicService::findById);
        entityIdHandler.checkExisting(Person.ENTITY_NAME, commentOwnerId, personService::findById);

        // TODO: Check if person is authenticated

        Topic topic = entityIdHandler.handle(Topic.ENTITY_NAME, topicId, topicService::findById, t -> t);
        String topicOwnerId = entityIdHandler.handle(Person.ENTITY_NAME, topic.getPersonId(), personService::findById, p -> p).getId();
        List<Relationship> relationships = relationshipService.findByRequestingPersonIdAndStatus(commentOwnerId, ACCEPTED);
        List<Relationship> relevantRelationship = relationships.stream()
            .filter(relationship -> relationship.getRequestedPersonId().equals(topicOwnerId))
            .collect(Collectors.toList());
        if (!commentOwnerId.equals(topicOwnerId) && relevantRelationship.isEmpty()) {
            throw new NotAuthorizedException(commentOwnerId, topicId);
        }

        Comment comment = new Comment(commentCreation);
        return postRepository.save(comment);
    }

    @Override
    public Location createLocationPost(final Location location) {
        return entityIdHandler.handle(Person.ENTITY_NAME, location.getPersonId(),
            personService::findById, person -> postRepository.save(location));
    }

    @Override
    public Optional<Location> findLocationById(String postId) {
        return postRepository.findByIdAndPostType(postId, PostType.LOCATION).map(post -> (Location) post);
    }

    @Override
    public void deletePost(String postId) {
        // TODO: Determine if posts can be deleted
        //  if yes, who can delete what posts => poster and owner of topic?
        //  if authenticated user matches personId of topic or post
        //  delete post and dependencies like ratings
        //  else throw exception
        entityIdHandler.consume(Post.ENTITY_NAME, postId, postRepository::findById, postRepository::delete);
    }

    @Override
    public List<Post> findAllPostsByTopicId(String topicId) {
        return postRepository.findAllByTopicId(topicId);
    }

    @Override
    public <T extends Post> List<T> findPostsByTypeAndTopicId(PostType postType, String topicId) {
        return postRepository.findAllByPostTypeIn(postType, findAllPostsByTopicId(topicId));
    }

    @Override
    public List<PostStructure> findAllPostsByTopicIdStructured(String topicId) {
        return postRepository
            .findAllByTopicIdOrderByCreateDateTimeAsc(topicId)
            .stream()
            .map(Post::createStructure)
            .collect(Collectors.toList());
    }

}
