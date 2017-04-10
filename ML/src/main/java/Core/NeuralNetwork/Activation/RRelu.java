package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class RRelu implements Activation {
    private double lowBoundaries;
    private double highBoundaries;
    private double parameter;

    public RRelu(double lowBoundaries, double highBoundaries) {
        this.lowBoundaries = lowBoundaries;
        this.highBoundaries = highBoundaries;
        parameter = ThreadLocalRandom.current().nextDouble(lowBoundaries, highBoundaries + 1);
    }

    public double getLowBoundaries() {
        return lowBoundaries;
    }

    public void setLowBoundaries(double lowBoundaries) {
        this.lowBoundaries = lowBoundaries;
    }

    public double getHighBoundaries() {
        return highBoundaries;
    }

    public void setHighBoundaries(double highBoundaries) {
        this.highBoundaries = highBoundaries;
    }

    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix result = new SimpleMatrix(Z);

        for(int row = 0; row < result.numRows(); ++row) {
            result.set(row, 0, result.get(row, 0) >= 0 ? result.get(row, 0) : result.get(row, 0) * parameter);
        }

        return result;
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix derivative = new SimpleMatrix(Z);

        for(int row = 0; row < derivative.numRows(); ++row) {
            derivative.set(row, 0, derivative.get(row, 0) >= 0 ? 1 : parameter);
        }

        return derivative;
    }
}
