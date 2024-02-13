package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = "playerName"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playerId;

    private String playerName;

    private Double successRate;

    public PlayerEntity(String playerName, Double successRate) {
        this.playerName = playerName;
        this.successRate = successRate;
    }

}
