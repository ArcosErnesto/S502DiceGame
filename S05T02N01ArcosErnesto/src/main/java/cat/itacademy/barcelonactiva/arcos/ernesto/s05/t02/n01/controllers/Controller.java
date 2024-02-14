package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diceGame/v1/players")
public class Controller {
    @Autowired
    PlayerService playerService;

    @PostMapping("")
    public ResponseEntity<PlayerDTO>addPlayer(@RequestBody PlayerDTO playerDTO){
        PlayerDTO newPlayer = playerService.addPlayer(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable long id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerService.updatePlayer(id, playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPlayer);
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> list = playerService.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> play(@PathVariable("id") Integer id){
        GameDTO newGame = playerService.playGame(id);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

}
