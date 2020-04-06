package ch.zhaw.swm.wall.contoller;

import ch.zhaw.swm.wall.model.person.AcceptFriendshipStatus;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipCreation;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RelationshipController extends BasicController {

    private final RelationshipService relationshipService;
    private static final String URI_FRAGMENT = "/relationships";
    private static final String ID = "/{id}";

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping(URI_FRAGMENT)
    @Operation(description = "Creates a new relationship with status PENDING. Returns error if relationship with status ACCEPTED or the reverse relationship already exists.")
    public Relationship newRelationship(@RequestBody RelationshipCreation newRelationship) {
        return relationshipService.createRelationship(newRelationship);
    }

    @DeleteMapping(URI_FRAGMENT + ID)
    @Operation(description = "Deletes relationship and reverse relationship if it exists.")
    public void delete(@PathVariable String id) {
        this.relationshipService.deleteRelationship(id);
    }

    @PatchMapping(URI_FRAGMENT + ID)
    @Operation(description = "Sets the status of a relationship if state change is valid. Used to accept a friend request. Valid state change: PENDING to ACCEPTED")
    public void acceptFriendship(@PathVariable String id, @RequestBody AcceptFriendshipStatus status) {
        this.relationshipService.acceptFriendship(id, status.getStatus());
    }

}
