package dev.kropotov.study.fraction;

import dev.kropotov.study.annotations.Cache;
import dev.kropotov.study.annotations.Mutator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Fraction implements Fractionable {
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
}
