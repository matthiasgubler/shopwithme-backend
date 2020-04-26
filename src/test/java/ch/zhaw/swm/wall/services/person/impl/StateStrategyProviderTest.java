package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StateStrategyProviderTest {

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
