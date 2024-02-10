package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PlayerDTO update(long id, PlayerEntity player) {
        return null;
    }

    @Override
    public PlayerDTO findById(long id) {
        return null;
    }

    @Override
    public String delete(long id) {
        return null;
    }

    @Override
    public PlayerDTO getOne(long id) {
        return null;
    }

    @Override
    public List<PlayerDTO> findAll() {
        return null;
    }

    private PlayerEntity playerToDomain(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getPlayerName());
    }

    private PlayerDTO playerToDTO(PlayerEntity player) {
        return new PlayerDTO(player.getPlayerName());
    }
}
