package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private GameController gameController;
    @Mock
    private PlayerService playerService;
    @Mock
    private GameService gameService;
    @Autowired
    private ObjectMapper objectMapper;
    private PlayerDTO playerDTO1;
    private PlayerDTO playerDTO2;
    private GameEntity game1;
    private GameEntity game2;
    private GameDTO gameDTO1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        playerDTO1 = PlayerDTO.builder().playerId(1L).playerName("Chiquito").successRate(75d).creationDate(new Date()).build();
        playerDTO2 = PlayerDTO.builder().playerId(2L).playerName("P.Tinto").successRate(33.33d).creationDate(new Date()).build();
        game1 = GameEntity.builder().dice1(4).dice2(3).win(true).build();
        game2 = GameEntity.builder().dice1(6).dice2(5).win(false).build();
        gameDTO1 = GameDTO.builder().dice1(4).dice2(3).win(true).build();
    }

    @Test
    void should_add_new_player() throws Exception {
        when(playerService.addPlayer(any())).thenReturn(playerDTO1);
        mockMvc.perform(post("/diceGame/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerName", is(playerDTO1.getPlayerName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void should_update_existing_player() throws Exception {
        when(playerService.updatePlayer(any(Long.class), any(PlayerDTO.class))).thenReturn(playerDTO2);
        mockMvc.perform(put("/diceGame/v1/players/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerName", is(playerDTO2.getPlayerName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void should_return_superHero_list() throws Exception {
        when(playerService.findAll()).thenReturn(Arrays.asList(playerDTO1, playerDTO2));
        mockMvc.perform(get("/diceGame/v1/players"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_create_game_by_playerId() throws Exception {
        when(playerService.playGame(any(Long.class))).thenReturn(gameDTO1);
        mockMvc.perform(post("/diceGame/v1/players/1/games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dice1", is(gameDTO1.getDice1())))
                .andExpect(jsonPath("$.dice2", is(gameDTO1.getDice2())))
                .andExpect(jsonPath("$.win", is(gameDTO1.isWin())));
    }

    @Test
    void should_return_playerGames_list() throws Exception {
        when(gameService.getOnePlayerGames(any(Long.class))).thenReturn(Arrays.asList(game1, game2));
        mockMvc.perform(get("/diceGame/v1/players/1/games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_return_delete_message() throws Exception {
        when(gameService.deletePlayerGames(any(Long.class))).thenReturn("Borradas con éxito las partidas del jugador " + playerDTO1.getPlayerName());
        String msg = gameService.deletePlayerGames(playerDTO1.getPlayerId());
        mockMvc.perform(delete("/diceGame/v1/players/1/games"))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(msg, "Borradas con éxito las partidas del jugador " + playerDTO1.getPlayerName());
    }

    @Test
    void should_return_ranking() throws Exception {
        when(playerService.getRanking()).thenReturn(Arrays.asList(playerDTO1, playerDTO2));
        mockMvc.perform(get("/diceGame/v1/players/ranking"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_return_winner() throws Exception {
        when(playerService.getWinner()).thenReturn(playerDTO1);
        mockMvc.perform(get("/diceGame/v1/players/ranking/winner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerName", is(playerDTO1.getPlayerName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void should_return_loser() throws Exception {
        when(playerService.getLoser()).thenReturn(playerDTO2);
        mockMvc.perform(get("/diceGame/v1/players/ranking/loser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerName", is(playerDTO2.getPlayerName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
