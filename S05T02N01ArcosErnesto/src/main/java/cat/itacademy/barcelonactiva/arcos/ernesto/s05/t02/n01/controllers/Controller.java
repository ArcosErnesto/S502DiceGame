package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diceGame/v1")
public class Controller {
    @Autowired
    PlayerService playerService;

    @PostMapping("/addPlayer")
    public ResponseEntity<PlayerDTO>addPlayer(@RequestBody PlayerDTO playerDTO){
        PlayerDTO newPlayer = playerService.add(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @PutMapping("/updatePlayer/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable long id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerService.update(id, playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPlayer);
    }


    @DeleteMapping("/deletePlayer/{id}")
    public ResponseEntity<String>deletePlayer(@PathVariable long id){
        String msg = playerService.delete(id);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/getPlayer/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable long id) {
        Optional<PlayerDTO> player = playerService.getOne(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllPlayers")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> list = playerService.findAll();
        return ResponseEntity.ok(list);
    }

}
