package net.kalbskinder.helpers.math;

public class MathHelper {
    public int randomRange(int from, int to) {
        return (int) (Math.random() * (to - from + 1)) + from;
    }

    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
