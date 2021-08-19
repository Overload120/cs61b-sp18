package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;

    public AcceleratingSawToothGenerator(int p, double f) {
        period = p;
        factor = f;
        state = 0;

    }

    @Override
    public double next() {
        state += 1;
        if (state > period) {
            state = 0;
            period *= factor;
        }

        return normalize((double) state / period * -1);
    }

    private double normalize(double x) {
        return x * -2 - 1;
    }
}
