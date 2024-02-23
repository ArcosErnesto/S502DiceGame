package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = "playerName"))
@Builder
@Getter
@Setter
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Date creationDate;

    public PlayerEntity(String playerName, Double successRate) {
        this.playerName = playerName;
        this.successRate = successRate;
        this.creationDate = new Date();
    }

}
