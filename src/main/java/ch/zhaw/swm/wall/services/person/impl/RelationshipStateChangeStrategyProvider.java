package ch.zhaw.swm.wall.services.person.impl;


import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.services.person.RelationshipStateChangeStrategy;
import org.springframework.stereotype.Component;

@Component
public class RelationshipStateChangeStrategyProvider {
    private final RelationshipRepository relationshipRepository;

    public RelationshipStateChangeStrategyProvider(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    public RelationshipStateChangeStrategy getStrategy(RelationshipStatus status) {
        switch (status) {
            case PENDING:
                return new PendingStateStrategy();
            case ACCEPTED:
                return new AcceptStateStrategy(relationshipRepository);
            default:
                throw new IllegalArgumentException("Invalid state");
        }
    }
}
