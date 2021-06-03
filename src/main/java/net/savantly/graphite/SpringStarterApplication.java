package net.savantly.graphite;

import net.savantly.graphite.impl.GraphiteServerImpl;
import net.savantly.graphite.server.GraphiteServerBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterApplication.class, args);
    }

    @Bean
    public GraphiteServerBase getgaphite() {
        return new GraphiteServerImpl();
    }

}
