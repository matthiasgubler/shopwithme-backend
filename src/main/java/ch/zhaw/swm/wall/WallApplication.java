package ch.zhaw.swm.wall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WallApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(WallApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(WallApplication.class, args);
    }

}
