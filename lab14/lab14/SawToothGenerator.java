package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int p) {
        period = p;
        state = 0;
    }

    @Override
    public double next() {
        state += 1;
        return normalize((double) (state % period) / period * -1);
    }

    private double normalize(double x) {
        return x * -2 - 1;
    }
}
