package ch.zhaw.swm.wall.services;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityIdHandler {

    public <T> void consume(String entityName, String id, Function<String, Optional<T>> optionalFunction, Consumer<T> entityConsumer) {
        T nonNullResultT = checkAndReturnExisting(entityName, id, optionalFunction);
        entityConsumer.accept(nonNullResultT);
    }

    public <T, R> R handle(String entityName, String id, Function<String, Optional<T>> optionalFunction, Function<T, R> entityFunction) {
        T nonNullResultT = checkAndReturnExisting(entityName, id, optionalFunction);
        return entityFunction.apply(nonNullResultT);
    }

    public <T> void checkExisting(String entityName, String id, Function<String, Optional<T>> optionalFunction) {
        Optional<T> tOptional = optionalFunction.apply(id);
        if (!tOptional.isPresent()) {
            throw new NotFoundException(entityName, id);
        }
    }

    private <T> T checkAndReturnExisting(String entityName, String id, Function<String, Optional<T>> optionalFunction) {
        Optional<T> tOptional = optionalFunction.apply(id);
        return tOptional.orElseThrow(() -> new NotFoundException(entityName, id));
    }

}
