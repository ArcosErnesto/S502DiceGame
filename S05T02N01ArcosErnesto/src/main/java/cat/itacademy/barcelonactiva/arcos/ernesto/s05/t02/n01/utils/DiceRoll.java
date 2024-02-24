package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DiceRoll {
    private final Random random = new Random();
    private int dice1;
    private int dice2;

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void roll() {
        this.dice1 = random.nextInt(6) + 1;
        this.dice2 = random.nextInt(6) + 1;

    }
}