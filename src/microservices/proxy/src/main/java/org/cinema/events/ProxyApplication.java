package org.cinema.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProxyApplication.class);
        app.setDefaultProperties(Map.of(
                "server.port", "8000"
        ));
        app.run(args);
    }
}
