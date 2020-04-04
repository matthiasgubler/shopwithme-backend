package ch.zhaw.swm.wall.services;

import ch.zhaw.swm.wall.contoller.exception.NotFoundException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityIdHandler {

    public <T> void consume(String entityName, String id, Function<String, Optional<T>> optionalFunction, Consumer<T> entityConsumer) {
        Optional<T> tOptional = optionalFunction.apply(id);
        T entity = tOptional.orElseThrow(() -> new NotFoundException(entityName, id));
        entityConsumer.accept(entity);
    }

    public <T, R> R handle(String entityName, String id, Function<String, Optional<T>> optionalFunction, Function<T, R> entityFunction) {
        Optional<T> tOptional = optionalFunction.apply(id);
        T nonNullResultT = tOptional.orElseThrow(() -> new NotFoundException(entityName, id));
        return entityFunction.apply(nonNullResultT);
    }

}
