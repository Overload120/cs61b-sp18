package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int p) {
        period = p;
        state = 0;
    }

    @Override
    public double next() {
        state += 1;
        int weirdState = state & (state >>> 3) % period;
        weirdState = state & (state >> 7) % period;
        return normalize((double) weirdState / period * -1);
    }

    private double normalize(double x) {
        return x * -2 - 1;
    }

}
