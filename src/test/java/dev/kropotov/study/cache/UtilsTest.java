package dev.kropotov.study.cache;

import dev.kropotov.study.fraction.Fractionable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    @DisplayName("Тестирование кэширования")
    public void cache() {
        FractionCachingTestClass fraction = new FractionCachingTestClass(2, 3);
        Fractionable fr = Utils.cache(fraction);
        fr.doubleValue();
        fr.doubleValue();
        assertEquals(fraction.getCountExecutions(), 1);
        fr.setNum(5);
        fr.doubleValue();
        assertEquals(fraction.getCountExecutions(), 2);

    }
}
