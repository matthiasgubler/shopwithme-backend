package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.services.person.RelationshipStateChangeStrategy;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StateStrategyProviderTest {

    @Mock
    private RelationshipRepository relationshipRepositoryMock;

    @InjectMocks
    private RelationshipStateChangeStrategyProvider acceptStateStrategy;

    @Test
    public void checkAccept() {
       Assert.assertEquals(AcceptStateStrategy.class, acceptStateStrategy.getStrategy(RelationshipStatus.ACCEPTED).getClass());
    }

    @Test
    public void checkPending() {
        Assert.assertEquals(PendingStateStrategy.class, acceptStateStrategy.getStrategy(RelationshipStatus.PENDING).getClass());
    }
}
