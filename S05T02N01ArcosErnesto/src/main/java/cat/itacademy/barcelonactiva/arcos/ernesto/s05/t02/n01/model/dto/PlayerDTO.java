package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long playerId;
    private String playerName;
    private Date creationDate;
    private Double successRate;

    public PlayerDTO(long playerId, String playerName, Date creationDate, Double successRate) {
        this.playerId = playerId;
        this.playerName = playerName != null ? playerName : "ANONYMOUS";
        this.creationDate = creationDate;
        this.successRate = successRate;
    }
}
