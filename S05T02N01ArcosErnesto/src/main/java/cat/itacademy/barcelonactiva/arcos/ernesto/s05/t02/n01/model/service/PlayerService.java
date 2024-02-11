package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;


public interface PlayerService {
    PlayerDTO add(PlayerDTO player);
    PlayerDTO update(long id, PlayerDTO player);
    String delete(long id);
    Optional<PlayerDTO> getOne(long id);
    List<PlayerDTO>findAll();
}
