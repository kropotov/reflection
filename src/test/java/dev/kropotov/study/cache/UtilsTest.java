package dev.kropotov.study.cache;

import dev.kropotov.study.fraction.Fractionable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    @DisplayName("Тестирование кэширования")
    public void cache() {
        int num = 3, denum = 2;

        FractionCachingTestClass fraction = new FractionCachingTestClass(num, denum);
        Fractionable fr = Utils.cache(fraction);
        assertEquals((double) num / denum, fr.doubleValue());
        assertEquals((double) num / denum, fr.doubleValue());
        assertEquals(1, ExecutionsCounter.getCountExecutions(fr));

        int oldNum = num;
        num = 5;
        fr.setNum(num);
        assertEquals((double) num / denum, fr.doubleValue());
        assertEquals((double) num / denum, fr.doubleValue());
        assertEquals(2, ExecutionsCounter.getCountExecutions(fr));

        num = oldNum;
        fr.setNum(num);
        assertEquals((double) num / denum, fr.doubleValue());
        assertEquals(2, ExecutionsCounter.getCountExecutions(fr));
    }
}
