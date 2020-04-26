package ch.zhaw.swm.wall.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class BasicController {

    protected <T> ResponseEntity<T> handleSingleResourceResponse(Optional<T> returnObject) {
        return returnObject
            .map(object -> new ResponseEntity<>(object, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
