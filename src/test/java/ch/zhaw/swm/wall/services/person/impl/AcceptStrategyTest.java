package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AcceptStrategyTest {
    private RelationshipRepository relationshipRepositoryMock;

    private AcceptStateStrategy acceptStateStrategy;

    @Before
    public void before() {
        relationshipRepositoryMock = Mockito.mock(RelationshipRepository.class);
        acceptStateStrategy = new AcceptStateStrategy(relationshipRepositoryMock);
    }

    @Test
    public void testAcceptStrategy() {
        Relationship relationship = new Relationship("1", "2", RelationshipStatus.PENDING);
        acceptStateStrategy.performStateChange(relationship);
        Assert.assertEquals(RelationshipStatus.ACCEPTED, relationship.getStatus());
        Mockito.verify(relationshipRepositoryMock).save(relationship);
    }
}
