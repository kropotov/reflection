package dev.kropotov.study.state;

import dev.kropotov.study.fraction.Fraction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTest {
    @Test
    @DisplayName("Проверка сохранения и восстановления состояния")
    public void saveAndRestore() {
        int num = 3, denum = 2;
        Fraction fraction = new Fraction(num, denum);
        State stateBefore = new State(fraction);
        fraction.setDenum(1);
        fraction.setDenum(denum);
        State stateAfter = new State(fraction);
        assertEquals(stateBefore, stateAfter);

    }
}
