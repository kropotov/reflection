package dev.kropotov.study.fraction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FractionTest {
    @Test
    @DisplayName("Проверка сохранения и восстановления состояния")
    public void saveAndRestore() {
        int num = 3, denum = 2;
        Fraction fraction = new Fraction(num, denum);
        assertEquals(fraction.doubleValue(), (double) num / denum);
        Fraction.FractionState state = fraction.save();
        fraction.setDenum(1);
        assertEquals(fraction.doubleValue(), (double) num);
        fraction.restore(state);
        assertEquals(fraction.doubleValue(), (double) num / denum);

    }
}
