package dev.kropotov.study.cache;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;
import dev.kropotov.study.fraction.Fractionable;
import lombok.Getter;

public class FractionCachingTestClass implements Fractionable {
    private int num;
    private int denum;

    @Getter
    private int countExecutions;

    public FractionCachingTestClass(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache
    public double doubleValue() {
        countExecutions++;
        return (double) num / denum;
    }
}
