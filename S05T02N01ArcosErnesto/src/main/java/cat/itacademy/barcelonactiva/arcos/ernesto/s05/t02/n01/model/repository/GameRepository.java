package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity,Long> {
}
