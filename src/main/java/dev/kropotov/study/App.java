package dev.kropotov.study;

import dev.kropotov.study.cache.GlobalCacheRepository;
import dev.kropotov.study.cache.Utils;
import dev.kropotov.study.fraction.Fraction;
import dev.kropotov.study.fraction.Fractionable;

public class App {
    public static void main(String[] args) throws InterruptedException {

        Runnable cacheCleanerRunning = () -> {
            //TODO: тестовые значения, ограничиваюшие время работы потока
            final int MAX_COUNT = 3;
            int count = 0;
            //

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                GlobalCacheRepository.clearExpiredCaches();

                //TODO: тестовые значения, ограничиваюшие время работы потока
                if (count++ > MAX_COUNT) {
                    break;
                }
                //
            }
        };
        Thread cacheCLeanerThread = new Thread(cacheCleanerRunning);
        cacheCLeanerThread.start();

        Thread.sleep(500); // дадим время запуститься потоку очистки кэша

        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        System.out.println(num.doubleValue());// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(5);
        System.out.println("Mutator to new value");
        System.out.println(num.doubleValue());// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(2);
        System.out.println("Mutator to old value");
        System.out.println(num.doubleValue());// sout молчит, т.к. состояние кешировали раньше
        num.doubleValue();// sout молчит

        Thread.sleep(1500);
        System.out.println("Sleeped 1500 millisec");

        System.out.println(num.doubleValue());// sout сработал
        num.doubleValue();// sout молчит
    }
}
