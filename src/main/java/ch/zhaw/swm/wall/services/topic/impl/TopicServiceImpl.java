package ch.zhaw.swm.wall.services.topic.impl;

import ch.zhaw.swm.wall.controller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.topic.Topic;
import ch.zhaw.swm.wall.model.topic.TopicPatch;
import ch.zhaw.swm.wall.repository.TopicRepository;
import ch.zhaw.swm.wall.services.EntityIdHandler;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.topic.TopicService;

import java.util.List;
import java.util.Optional;

public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final PersonService personService;

    private final EntityIdHandler entityIdHandler = new EntityIdHandler();

    public TopicServiceImpl(TopicRepository topicRepository, PersonService personService) {
        this.topicRepository = topicRepository;
        this.personService = personService;
    }

    @Override
    public Optional<Topic> findById(String id) {
        return topicRepository.findById(id);
    }

    @Override
    public Topic create(Topic topic) {
        Optional<Person> optionalPerson = personService.findById(topic.getPersonId());
        if (!optionalPerson.isPresent()) {
            throw new NotFoundException(Person.ENTITY_NAME, topic.getPersonId());
        }
        return topicRepository.save(topic);
    }

    @Override
    public void delete(String id) {
        entityIdHandler.consume(Topic.ENTITY_NAME, id, topicRepository::findById, topicRepository::delete);
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public List<Topic> findByPersonId(String personId) {
        entityIdHandler.checkExisting(Person.ENTITY_NAME, personId, personService::findById);
        return topicRepository.findByPersonId(personId);
    }

    @Override
    public Topic patch(TopicPatch topicPatch, String id) {
        return entityIdHandler.handle(Topic.ENTITY_NAME, id, topicRepository::findById, topic -> topicRepository.save(topicPatch.apply(topic)));
    }

}
