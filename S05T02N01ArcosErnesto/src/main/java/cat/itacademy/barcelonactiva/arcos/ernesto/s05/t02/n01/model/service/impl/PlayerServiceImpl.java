package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerUpdateException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
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
    @Override
    public PlayerDTO add(PlayerDTO playerDTO) {
        PlayerEntity playerEntity = playerToDomain(playerDTO);
        playerEntity = playerRepository.save(playerEntity);
        return playerToDTO(playerEntity);
    }

    @Override
    public PlayerDTO update(long id, PlayerDTO playerDTO) {
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


    private PlayerEntity playerToDomain(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getPlayerName());
    }

    private PlayerDTO playerToDTO(PlayerEntity player) {
        return new PlayerDTO(player.getPlayerName());
    }
}
