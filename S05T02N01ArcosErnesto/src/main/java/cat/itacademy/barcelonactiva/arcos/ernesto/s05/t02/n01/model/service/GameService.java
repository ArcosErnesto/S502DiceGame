package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;

import java.util.List;
import java.util.Optional;

public interface GameService {
    GameDTO addGame(Optional<PlayerEntity> playerEntity);
    List<GameEntity> getAllGames();
    void deleteAllGames(PlayerEntity playerEntity);
    GameEntity gameDTOToEntity(Optional<PlayerEntity> playerDTO, GameDTO gameDTO);
    GameDTO gameEntityToDTO(GameEntity gameEntity);
}
