package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import java.util.List;

public interface PlayerService {
    PlayerDTO addPlayer(PlayerDTO player);
    PlayerDTO updatePlayer(long id, PlayerDTO player);
    List<PlayerDTO>findAll();
    GameDTO playGame(long id);
    void resetSuccessRate(long id);
    List<PlayerDTO> getRanking();
    PlayerDTO getWinner();
    PlayerDTO getLoser();
    PlayerEntity playerToDomain(PlayerDTO playerDTO);
    PlayerDTO playerToDTO(PlayerEntity player);
}
