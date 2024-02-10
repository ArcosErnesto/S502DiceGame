package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String playerName;

    public PlayerDTO(String playerName) {
        this.playerName = (playerName != null && !playerName.isEmpty()) ? playerName : "ANONYMOUS";

    }
}
