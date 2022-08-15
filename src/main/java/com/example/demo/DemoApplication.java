package com.example.demo;

import com.example.demo.model.Player;
import com.example.demo.model.Position;
import com.example.demo.repository.PlayerRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(PlayerRepository playerRepository) {
        return args -> {
            playerRepository.saveAll(List.of(new Player("ilker", "adiguzel", Position.CENTER),
                    new Player("samet", "ozkale", Position.POINT_GUARD),
                    new Player("some", "other", Position.POINT_GUARD)));
        };
    }

}
