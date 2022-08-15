package com.example.demo.controller;

import com.example.demo.dto.CreatePlayerInput;
import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class PlayerController {
    private final Integer TEAM_CAPACITY = 10;
    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @QueryMapping
    Iterable<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @QueryMapping
    Player getPlayerById(@Argument Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user with the given id!"));
    }

    @MutationMapping
    Player addPlayer(@Argument CreatePlayerInput input) {
        if (playerRepository.findAll().size() >= 10) {
            throw new RuntimeException("Sorry, team is full!");
        }

        playerRepository.findByName(input.getName()).ifPresent(player -> {
            throw new RuntimeException("Player already exists!");
        });

        Player newPlayer = new Player(input.getName(), input.getSurname(), input.getPosition());

        playerRepository.save(newPlayer);

        return newPlayer;
    }

    @MutationMapping
    boolean deletePlayer(@Argument Long id) {
        playerRepository.findById(id).ifPresent(playerRepository::delete);

        return true;
    }

}
