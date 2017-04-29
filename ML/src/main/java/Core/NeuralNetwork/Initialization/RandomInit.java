package Core.NeuralNetwork.Initialization;

import org.ejml.simple.SimpleMatrix;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by GrIfOn on 29.04.2017.
 */
public class RandomInit implements Initialization {
    private static final double DEFAULT_BOUND = 0.5;

    private double lowerBound;
    private double upperBound;
    private long seed;
    private ThreadLocalRandom random;


    public RandomInit() {
        this.lowerBound = -DEFAULT_BOUND;
        this.upperBound = DEFAULT_BOUND;

        this.random = ThreadLocalRandom.current();
    }

    public RandomInit(double bound) {
        this.lowerBound = -bound;
        this.upperBound = bound;

        this.random = ThreadLocalRandom.current();
    }

    public RandomInit(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.random = ThreadLocalRandom.current();
    }

    public RandomInit(long seed) {
        this.lowerBound = -DEFAULT_BOUND;
        this.upperBound = DEFAULT_BOUND;
        this.seed = seed;

        this.random = ThreadLocalRandom.current();
        this.random.setSeed(seed);
    }

    public RandomInit(double bound, long seed) {
        this.lowerBound = -bound;
        this.upperBound = bound;

        this.random = ThreadLocalRandom.current();
        this.random.setSeed(seed);
    }

    public RandomInit(double upperBound, double lowerBound, long seed) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.seed = seed;

        this.random = ThreadLocalRandom.current();
        this.random.setSeed(seed);
    }

    @Override
    public SimpleMatrix init(int units, int bindings) {
        SimpleMatrix weights = new SimpleMatrix(units, bindings);

        for(int row = 0; row < weights.numRows(); ++row) {
            for(int col = 0; col < weights.numCols(); ++col) {
                weights.set(row, col, random.nextDouble(lowerBound, upperBound));
            }
        }

        return weights;
    }
}
