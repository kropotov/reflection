package dev.kropotov.study.cache;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;
import dev.kropotov.study.fraction.Fractionable;
import dev.kropotov.study.state.Savable;
import dev.kropotov.study.state.State;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class FractionCachingTestClass implements Fractionable, Savable {
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

    @Override
    public FractionCachingTestClassState save() {
        return new FractionCachingTestClassState(this);
    }

    public void restore(FractionCachingTestClassState state) {
        num = state.num;
        denum = state.denum;
    }

    @EqualsAndHashCode
    class FractionCachingTestClassState implements State {
        private final int num;
        private final int denum;

        FractionCachingTestClassState(FractionCachingTestClass fraction) {
            this.num = fraction.num;
            this.denum = fraction.denum;
        }
    }
}
