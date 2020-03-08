package ch.zhaw.swm.wall.contoller.exception;

import java.math.BigInteger;

public class NotFoundException extends RuntimeException {

    private BigInteger id;

    private String entityName;

    public NotFoundException(String entityName, BigInteger id) {
        super(entityName + " id not found : " + id);
    }

}
