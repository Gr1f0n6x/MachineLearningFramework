package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class LRelu implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix result = new SimpleMatrix(Z);

        for(int row = 0; row < result.numRows(); ++row) {
            result.set(row, 0, result.get(row, 0) >= 0 ? result.get(row, 0) : result.get(row, 0) * 0.01);
        }

        return result;
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix derivative = new SimpleMatrix(Z);

        for(int row = 0; row < derivative.numRows(); ++row) {
            derivative.set(row, 0, derivative.get(row, 0) >= 0 ? 1 : 0.01);
        }

        return derivative;
    }
}
