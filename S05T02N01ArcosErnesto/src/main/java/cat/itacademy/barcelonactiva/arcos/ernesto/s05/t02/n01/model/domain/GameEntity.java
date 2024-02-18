package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "games")
@NoArgsConstructor
@Getter
@Setter
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id")
    private PlayerEntity playerEntity;

    @Column(nullable = false)
    private int dice1;

    @Column(nullable = false)
    private int dice2;

    @Column(nullable = false)
    private boolean win;

    public GameEntity(PlayerEntity playerEntity, int dice1, int dice2, boolean win) {
        this.playerEntity = playerEntity;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.win = win;
    }
}
