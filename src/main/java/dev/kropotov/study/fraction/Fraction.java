package dev.kropotov.study.fraction;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;
import dev.kropotov.study.state.Savable;
import dev.kropotov.study.state.State;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;


@AllArgsConstructor
public class Fraction implements Fractionable, Savable {
    private int num;
    private int denum;

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
    @Cache(1000)
    public double doubleValue() {
        System.out.println("invoke double value");
        return (double) num / denum;
    }

    public FractionState save() {
        return new FractionState(this);
    }

    public void restore(FractionState state) {
        num = state.num;
        denum = state.denum;
    }

    @EqualsAndHashCode
    class FractionState implements State {
        private final int num;
        private final int denum;

        FractionState(Fraction fraction) {
            this.num = fraction.num;
            this.denum = fraction.denum;
        }
    }
}
