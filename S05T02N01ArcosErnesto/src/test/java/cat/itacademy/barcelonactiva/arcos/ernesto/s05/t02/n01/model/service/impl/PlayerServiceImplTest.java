package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    PlayerService playerService;
    @Mock
    private GameService gameService;

    private PlayerEntity player1;
    private PlayerEntity player2;
    private PlayerDTO playerDTO1;
    private PlayerDTO playerDTO2;


    @BeforeEach
    void setUp() {
        player1 = PlayerEntity.builder().playerId(1L).playerName("Chiquito").successRate(50d).creationDate(new Date()).build();
        player2 = PlayerEntity.builder().playerId(2L).playerName("Eugenio").successRate(12.5d).creationDate(new Date()).build();
        playerDTO1 = playerService.playerToDTO(player1);
        playerDTO2 = playerService.playerToDTO(player2);
    }

    @AfterEach
    void tearDown() {
        player1 = player2 = null;
    }

    @Test
    void addPlayer_Success() {
        PlayerDTO playerDTO = PlayerDTO.builder().playerId(1L).playerName("Chiquito").successRate(50d).creationDate(new Date()).build();
        player1 = playerService.playerToDomain(playerDTO);
        verify(playerRepository, times(1)).save(player1);
        ArgumentCaptor<PlayerEntity> playerEntityArgumentCaptorArgumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        verify(playerRepository).save(playerEntityArgumentCaptorArgumentCaptor.capture());
        PlayerEntity playerCreated = playerEntityArgumentCaptorArgumentCaptor.getValue();
        assertNotNull(playerCreated.getPlayerId());
        assertEquals("Chiquito", playerCreated.getPlayerName());

    }

    @Test
    void addPlayer_DuplicateName() {
        PlayerDTO playerDTO = PlayerDTO.builder().playerId(1L).playerName("Chiquito").successRate(50d).creationDate(new Date()).build();
        when(playerRepository.existsByPlayerName("Chiquito")).thenReturn(true);

        assertThrows(PlayerAlreadyExistsException.class, () -> playerService.addPlayer(playerDTO));

        verify(playerRepository, times(0)).save(Mockito.any(PlayerEntity.class));
    }

    @Test
    void updatePlayer() {
    }

    @Test
    void findAll() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        assertNotNull(playerService.findAll());
    }

    @Test
    void playGame() {
    }

    @Test
    void resetSuccessRate() {
    }

    @Test
    void getRanking() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void getLoser() {
    }
}