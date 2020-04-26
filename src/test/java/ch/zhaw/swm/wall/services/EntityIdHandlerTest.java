package ch.zhaw.swm.wall.services;

import ch.zhaw.swm.wall.controller.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityIdHandlerTest {

    public static final String ENTITY_NAME = "entity";
    public static final String ID = "ID";
    private EntityIdHandler entityIdHandler = new EntityIdHandler();

    @Test
    void handle_exception_case() {
        assertThrows(NotFoundException.class, () -> entityIdHandler.handle(ENTITY_NAME, ID, (id) -> Optional.empty(), (data) -> fail("Should not be called")),
            ENTITY_NAME + " id not found : " + ID);
    }

    @Test
    void handle_happy_case() {
        String result = entityIdHandler.handle(ENTITY_NAME, ID, (id) -> Optional.of("Subject"), (data) -> data + " handled");
        assertEquals("Subject handled", result);
    }

    @Test
    void consume_exception_case() {
        assertThrows(NotFoundException.class, () -> entityIdHandler.consume(ENTITY_NAME, ID, (id) -> Optional.empty(), (data) -> fail("Should not be called")),
            ENTITY_NAME + " id not found : " + ID);
    }

    @Test
    void consume_happy_case() {
        entityIdHandler.consume(ENTITY_NAME, ID, (id) -> Optional.of("Subject"), (result) -> assertEquals("Subject", result));
    }

}
