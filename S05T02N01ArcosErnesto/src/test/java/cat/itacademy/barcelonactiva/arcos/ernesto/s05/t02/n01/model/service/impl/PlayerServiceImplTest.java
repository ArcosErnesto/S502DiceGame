package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerUpdateException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.utils.DiceRoll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {
    @Mock
    PlayerRepository playerRepository;
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    PlayerServiceImpl playerService;
    @InjectMocks
    GameServiceImpl gameService;
    @InjectMocks
    DiceRoll diceRoll;
    private PlayerEntity player1;
    private PlayerEntity player2;
    private PlayerDTO playerDTO1;
    private PlayerDTO playerDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerServiceImpl(playerRepository, gameService);
        player1 = PlayerEntity.builder().playerId(1L).playerName("Chiquito").successRate(75d).creationDate(new Date()).build();
        player2 = PlayerEntity.builder().playerId(2L).playerName("P.Tinto").successRate(33.33d).creationDate(new Date()).build();
        playerDTO1 = playerService.playerToDTO(player1);
        playerDTO2 = playerService.playerToDTO(player2);
    }

    @Test
    void save_should_insert_new_superHero() {
        when(playerRepository.existsByPlayerName("Chiquito")).thenReturn(false);
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        playerService.addPlayer(playerDTO1);
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));
        ArgumentCaptor<PlayerEntity> playerEntityArgumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        verify(playerRepository).save(playerEntityArgumentCaptor.capture());
        PlayerEntity savedPlayerEntity = playerEntityArgumentCaptor.getValue();
        assertEquals("Chiquito", savedPlayerEntity.getPlayerName());
    }

    @Test
    void save_should_return_superHero_already_exist() {
        when(playerRepository.existsByPlayerName("Chiquito")).thenReturn(true);
        PlayerAlreadyExistsException playerAlreadyExistsException = assertThrows(PlayerAlreadyExistsException.class,
                () -> playerService.addPlayer(playerDTO1));
        assertEquals("Ya existe un jugador con el nombre: " + playerDTO1.getPlayerName(), playerAlreadyExistsException.getMessage());
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }

    @Test
    void save_should_update_existing_superHero() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PlayerDTO playerDTO1 = playerService.playerToDTO(player1);
        playerService.addPlayer(playerDTO1);
        playerDTO1.setPlayerName("Gila");
        PlayerDTO updatedPlayer = playerService.updatePlayer(1L, playerDTO1);
        assertEquals("Gila", updatedPlayer.getPlayerName());
    }

    @Test
    void whenUpdatePlayerAlreadyExists_thenPlayerAlreadyExistsExceptionIsThrown() {
        PlayerDTO player = PlayerDTO.builder().playerId(10L).playerName("Eugenio").successRate(12.5d).creationDate(new Date()).build();
        when(playerRepository.existsByPlayerName(player.getPlayerName())).thenReturn(true);
        PlayerAlreadyExistsException playerAlreadyExistsException = assertThrows(PlayerAlreadyExistsException.class,
                () -> playerService.updatePlayer(player.getPlayerId(), player));
        assertEquals("Ya existe un jugador con el nombre: " + player.getPlayerName(), playerAlreadyExistsException.getMessage());
    }

    @Test
    void findAll_should_return_player_list() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        List<PlayerDTO> playersList = playerService.findAll();
        assertEquals(2, playersList.size());
        verify(playerRepository).findAll();
    }


    @Test
    void whenPlayGame_thenSuccess() {
        GameServiceImpl mockGameService = mock(GameServiceImpl.class);
        PlayerEntity player = player1;
        GameDTO gameDTO = new GameDTO(5, 2, true);
        when(playerRepository.findById(player1.getPlayerId())).thenReturn(Optional.of(player));
        lenient().when(mockGameService.addGame(any())).thenReturn(gameDTO);
        lenient().when(mockGameService.gameDTOToEntity(any(), any()))
                .thenReturn(new GameEntity(player1, gameDTO.getDice1(), gameDTO.getDice2(), gameDTO.isWin()));
        GameDTO result = playerService.playGame(player1.getPlayerId());
        assertNotNull(result);
    }

    @Test
    void whenPlayGamePlayerNotFound_thenExceptionThrown() {
        when(playerRepository.findById(player1.getPlayerId())).thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, () -> playerService.playGame(1L));
    }

    @Test
    void ranking_should_return_player_list_order_by_successRate() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        List<PlayerDTO> playersList = playerService.getRanking();
        assertEquals(2, playersList.size());
        assertEquals("Chiquito", playersList.get(0).getPlayerName());
        assertEquals("P.Tinto", playersList.get(1).getPlayerName());
    }

    @Test
    void getWinner_should_return_player_with_highest_successRate(){
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        PlayerDTO winner = playerService.getWinner();
        assertEquals("Chiquito", winner.getPlayerName());
    }

    @Test
    void getLoser_should_return_player_with_lowest_successRate() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        PlayerDTO loser = playerService.getLoser();
        assertEquals("P.Tinto", loser.getPlayerName());
    }
}