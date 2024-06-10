package dev.kropotov.study;

import dev.kropotov.study.cache.Utils;
import dev.kropotov.study.fraction.Fraction;
import dev.kropotov.study.fraction.Fractionable;

public class App
{
    public static void main( String[] args )
    {
        Fraction fr = new Fraction(2,3);
        Fractionable num = Utils.cache(fr);
        System.out.println(num.doubleValue());// sout сработал
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит
        num.setNum(5);
        System.out.println(num.doubleValue());// sout сработал
        num.doubleValue();// sout молчит

    }
}
