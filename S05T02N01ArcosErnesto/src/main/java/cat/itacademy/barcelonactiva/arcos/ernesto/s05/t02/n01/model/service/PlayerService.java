package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PlayerService {
    PlayerDTO add(PlayerDTO player);
    PlayerDTO update(long id, PlayerEntity player);
    PlayerDTO findById(long id);
    String delete(long id);
    PlayerDTO getOne(long id);
    List<PlayerDTO>findAll();
}
