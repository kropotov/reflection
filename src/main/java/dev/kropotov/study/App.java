package dev.kropotov.study;

import dev.kropotov.study.cache.Utils;
import dev.kropotov.study.fraction.Fraction;
import dev.kropotov.study.fraction.Fractionable;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит

        num.setNum(5);
        System.out.println("Mutator to new value");
        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит

        num.setNum(2);
        System.out.println("Mutator to old value");
        System.out.println(num.doubleValue());// sout молчит, т.к. состояние кешировали раньше
        System.out.println(num.doubleValue());// sout молчит

        Thread.sleep(1500);
        System.out.println("Sleeped 1500 millisec");

        System.out.println(num.doubleValue());// sout сработал
        System.out.println(num.doubleValue());// sout молчит*/
    }
}
