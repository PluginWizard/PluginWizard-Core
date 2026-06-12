package net.kalbskinder.helpers.math;

import java.util.List;

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

    public double listSum(List<Float> floats) {
        return floats.stream().reduce(0f, Float::sum);
    }

    public double listMin(List<Float> floats) {
        return floats.stream().min(Float::compare).orElse(0f);
    }

    public double listMax(List<Float> floats) {
        return floats.stream().max(Float::compare).orElse(0f);
    }

    public double listAverage(List<Float> floats) {
        return floats.stream().mapToDouble(Float::doubleValue).average().orElse(0);
    }

    public double listMedian(List<Float> floats) {
        int size = floats.size();
        if (size == 0) {
            return 0;
        }
        List<Float> sorted = floats.stream().sorted().toList();
        if (size % 2 == 1) {
            return sorted.get(size / 2);
        } else {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        }
    }

    public double listStandardDeviation(List<Float> floats) {
        int size = floats.size();
        if (size == 0) {
            return 0;
        }
        double mean = listAverage(floats);
        double variance = floats.stream()
                .mapToDouble(f -> Math.pow(f - mean, 2))
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }

    public double listModes(List<Float> floats) {
        if (floats.isEmpty()) {
            return 0;
        }
        return floats.stream()
                .reduce((a, b) -> java.util.Collections.frequency(floats, a) >= java.util.Collections.frequency(floats, b) ? a : b)
                .orElse(0f);
    }

    public Object listRandomItem(List<?> list) {
        if (list.isEmpty()) {
            return null;
        }
        int index = randomRange(0, list.size() - 1);
        return list.get(index);
    }
}
