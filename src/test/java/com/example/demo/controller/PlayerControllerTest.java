package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureGraphQlTester
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void whenGetAllPlayers_thenShouldReturnAllPlayers() {
        // language=GraphQL
        String document = """
                query {
                  getAllPlayers {
                    id
                    name
                    surname
                    position
                  }
                }
                             """;

        graphQlTester.document(document).execute().path("getAllPlayers").entityList(Player.class).hasSize(3);
    }

    @Test
    void whenGetPlayerById_thenShouldReturnExistingPlayer() {
        // language=GraphQL
        String document = """
                query getPlayerById($id: ID!){
                   getPlayerById(id: $id ) {
                    id
                    name
                    surname
                    position
                 }
                }
                             """;

        graphQlTester.document(document).variable("id", 1).execute().path("getPlayerById").entity(Player.class).satisfies(player -> {
            Assertions.assertEquals("ilker", player.getName());
            Assertions.assertEquals("adiguzel", player.getSurname());
            Assertions.assertEquals(Position.CENTER, player.getPosition());
        });
    }


    @Test
    void whenGetNonExistingPlayerById_thenShouldThrowException() {
        // language=GraphQL
        String document = """
                query getPlayerById($id: ID!){
                   getPlayerById(id: $id ) {
                    id
                    name
                    surname
                    position
                 }
                }
                             """;


            graphQlTester.document(document).variable("id", 134).execute().errors().expect(responseError -> true);
    }

    @Test
    void whemAddExceedingPlayer_thenShouldThrowException() {

        for (int i = 0; i < 7; i++) {
            String playerName = String.format("newly added player %s", i);


            // language=GraphQL
            String document = String.format(" mutation addPlayer {\n" +
                    "                    addPlayer(\n" +
                    "                      input: {name: \"%s\", surname: \"newly added surname\", position: POINT_GUARD}\n" +
                    "                    ) {\n" +
                    "                      id\n" +
                    "                      name\n" +
                    "                      surname\n" +
                    "                      position\n" +
                    "                    }\n" +
                    "                }", playerName);

            graphQlTester.document(document).execute();
        }

        String document ="""
                mutation {
                   addPlayer(
                     input: {name: "10th added player", surname: "newly added surname", position: POINT_GUARD}
                   ) {
                     id
                     name
                     surname
                     position
                   }
                 }
           """;

            graphQlTester.document(document).execute().errors().expect(responseError -> true);
    }

    @Test
    void whenAddPlayerWithSameName_thenShouldThrowException() {
        String document ="""
                mutation {
                   addPlayer(
                     input: {name: "ilker", surname: "newly added surname", position: POINT_GUARD}
                   ) {
                     id
                     name
                     surname
                     position
                   }
                 }
           """;

        graphQlTester.document(document).execute().errors().expect(responseError -> true);
    }

    @Test
    void whenAddPlayer_thenShouldAddPlayer() {
        String document ="""
                mutation {
                   addPlayer(
                     input: {name: "ilker v2", surname: "newly added surname", position: POINT_GUARD}
                   ) {
                     id
                     name
                     surname
                     position
                   }
                 }
           """;

        graphQlTester.document(document).execute().path("addPlayer").entity(Player.class).satisfies(player -> {
            Assertions.assertEquals("ilker v2", player.getName());
            Assertions.assertEquals("newly added surname", player.getSurname());
            Assertions.assertEquals(Position.POINT_GUARD, player.getPosition());
        });
    }

    @Test
    void whenDeletePlayer_thenShouldDeletePlayerAndReturnTrue() {
        // language=GraphQL
        String document = """
                mutation deletePlayer($id: ID!){
                   deletePlayer(id: $id )
                }
                             """;

        graphQlTester.document(document).variable("id", 1).execute().path("deletePlayer").entity(Boolean.class).satisfies(bool -> {
            Assertions.assertEquals(true, bool);
        });
    }


}
