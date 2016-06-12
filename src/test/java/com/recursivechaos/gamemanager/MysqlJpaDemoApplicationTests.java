package com.recursivechaos.gamemanager;

import com.recursivechaos.gamemanager.domain.Game;
import com.recursivechaos.gamemanager.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MysqlJpaDemoApplication.class)
public class MysqlJpaDemoApplicationTests {

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();
        Game pandemic = new Game("Pandemic", "Co-op game for wannabe disease control specailists");
        Game werewolf = new Game("Werewolf", "You must find out who's secretly the werewolf, before it's too late");
        Game camelUp = new Game("Camel Up", "A high stakes game of gambling and camel racing");
        gameRepository.save(pandemic);
        gameRepository.save(werewolf);
        gameRepository.save(camelUp);
    }

    @Test
    public void loadGames() {
        List<Game> games = (ArrayList<Game>) gameRepository.findAll();
        assertEquals("Did not get all games", 3, games.size());
    }

    @Test
    public void testFindGame() throws Exception {
        List<Game> camelUpList = gameRepository.findByName("Camel Up");
        assertEquals("Found wrong number of Camel Ups", 1, camelUpList.size());
        assertEquals("Found wrong name", "Camel Up", camelUpList.get(0).getName());
    }

    @Test
    public void testCRUD() throws Exception {
        // Create a new game
        Game munchkin = new Game("Munchkin", "A wild game for wild people.");
        gameRepository.save(munchkin);

        // Assert it was created
        List<Game> foundGame = gameRepository.findByName(munchkin.getName());
        assertEquals("Did not find Munchkin", munchkin.getName(), foundGame.get(0).getName());

        // Edit it's description
        String newDescription = "If you love rules that change, you'll love Munchkin!";
        foundGame.get(0).setDescription(newDescription);

        // Save to the database (note that we can save not just single Entities, but collections of them as well)
        gameRepository.save(foundGame);

        // Assert it updated
        List<Game> updatedGame = gameRepository.findByName(munchkin.getName());
        assertEquals("Did not update description", newDescription, updatedGame.get(0).getDescription());

        // Delete game
        gameRepository.delete(updatedGame);

        // Assert not found
        List<Game> emptyGame = gameRepository.findByName(munchkin.getName());
        assertEquals("Should have returned no games", 0, emptyGame.size());
    }
}
