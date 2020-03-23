package ch.zhaw.swm.wall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ch.zhaw.swm.wall.ioc", "ch.zhaw.swm.wall.contoller", "ch.zhaw.swm.wall.repository"})
public class WallApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallApplication.class, args);
    }

}
