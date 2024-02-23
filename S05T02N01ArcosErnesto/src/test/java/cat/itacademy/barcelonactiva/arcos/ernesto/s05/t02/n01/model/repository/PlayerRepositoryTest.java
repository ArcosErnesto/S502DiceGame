package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;

    @Test
    void findAll_should_return_player_list() {
        List<PlayerEntity> playersList = playerRepository.findAll();
        assertEquals(playersList.size(), 5);
    }

    @Test
    void findById_should_return_superHero() {
        Optional<PlayerEntity> player = playerRepository.findById(1L);
        assertTrue(player.isPresent());
    }

    @Test
    void findById_no_should_return_superHero() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            playerRepository.findById(10L).get();
        });
        assertNotNull(exception);
        assertEquals(exception.getClass(), NoSuchElementException.class);
        assertEquals(exception.getMessage(), "No value present");
    }

    @Test
    void existsByPlayerName_should_return_true() {
        boolean exists = playerRepository.existsByPlayerName("Chiquito");
        assertTrue(exists);
    }

    @Test
    void existsByPlayerName_should_return_false() {
        boolean exists = playerRepository.existsByPlayerName("Mairena");
        assertFalse(exists);
    }

    @Test
    void save_should_insert_new_superHero() {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .playerName("Gila")
                .creationDate(new Date())
                .build();

        PlayerEntity returnedPlayer = playerRepository.save(newPlayer);
        assertNotNull(returnedPlayer);
        assertEquals(6L, returnedPlayer.getPlayerId());
        assertEquals(newPlayer.getPlayerId(), returnedPlayer.getPlayerId());

    }

    @Test
    void save_should_update_existing_player(){
        Optional<PlayerEntity> existingPlayer =playerRepository.findById(1L);
        assertTrue(existingPlayer.isPresent());
        existingPlayer.get().setPlayerName("Arévalo");
        PlayerEntity updatedPlayer = playerRepository.save(existingPlayer.get());
        assertNotNull(updatedPlayer);
        assertEquals("Arévalo", updatedPlayer.getPlayerName());

    }
}