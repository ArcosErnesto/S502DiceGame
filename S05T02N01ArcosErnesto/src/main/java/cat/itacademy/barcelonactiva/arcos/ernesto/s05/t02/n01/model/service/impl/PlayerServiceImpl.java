package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerUpdateException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameService gameService;

    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        PlayerEntity playerEntity = playerToDomain(playerDTO);
        playerEntity = playerRepository.save(playerEntity);
        return playerToDTO(playerEntity);
    }

    @Override
    public PlayerDTO updatePlayer(long id, PlayerDTO playerDTO) {
        try {
            Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);

            if (updatedPlayer.isPresent()) {
                PlayerEntity playerDb = updatedPlayer.get();
                playerDb.setPlayerName(playerDTO.getPlayerName());
                playerRepository.save(playerDb);
                return playerToDTO(playerDb);
            } else {
                throw new PlayerNotFoundException("Jugador no encontrado con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlayerUpdateException("Error al actualizar el jugador con ID: " + id, e);
        }
    }

    @Override
    public String delete(long id) {
        PlayerEntity deletedPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Jugador no encontrado con el ID: " + id));
        playerRepository.delete(deletedPlayer);
        return "Jugador eliminado con éxito con el id: " + id;
    }


    @Override
    public Optional<PlayerDTO> getOne(long id) {
        return playerRepository.findById(id).map(this::playerToDTO);
    }

    @Override
    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream()
                .map(this::playerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO playGame(long id) {
        Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);
        GameDTO gameDTO = gameService.addGame(updatedPlayer);
        try {
            if (updatedPlayer.isPresent()) {
                PlayerEntity playerDb = updatedPlayer.get();
                playerDb = updateSuccessRate(updatedPlayer, gameDTO);
                playerRepository.save(playerDb);
                return gameDTO;
            } else {
                throw new PlayerNotFoundException("Jugador no encontrado con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlayerUpdateException("Error al actualizar el jugador con ID: " + id, e);
        }
    }

    private PlayerEntity updateSuccessRate(Optional<PlayerEntity> playerOptional, GameDTO gameDTO) {
        PlayerEntity player = playerOptional.orElseThrow(() -> new PlayerNotFoundException("No se proporcionó un jugador válido para actualizar la tasa de éxito."));

        Double successRate = player.getSuccessRate();
        double winGame = gameDTO.isWin() ? 1.0 : 0.0;

        if (successRate == null) {
            successRate = winGame * 100;
        } else {
            List<GameEntity> games = gameService.getAllGames();
            List<GameEntity> playerGames = games.stream()
                    .filter(game -> game.getPlayerEntity().getPlayerId()==player.getPlayerId())
                    .collect(Collectors.toList());
            double gamesPlayed = playerGames.size();
            List<GameEntity> winGames = playerGames.stream().filter(game-> game.isWin()).collect(Collectors.toList());
            double winedGames = winGames.size();

            if (gamesPlayed>0){
                successRate = winedGames/gamesPlayed*100;
            } else{
                successRate = winGame * 100;
            }
        }
        player.setSuccessRate(successRate);

        return player;
    }

    public PlayerEntity playerToDomain(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getPlayerName(), playerDTO.getSuccessRate());
    }

    public PlayerDTO playerToDTO(PlayerEntity player) {
        return new PlayerDTO(player.getPlayerId(), player.getPlayerName(), player.getCreationDate(), player.getSuccessRate());
    }

}
