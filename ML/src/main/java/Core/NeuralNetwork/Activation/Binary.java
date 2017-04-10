package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class Binary implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix activation = new SimpleMatrix(Z);
        for(int row = 0; row < activation.numRows(); ++row) {
            activation.set(row, 0, activation.get(row, 0) >= 0 ? 1 : 0);
        }
        return activation;
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix derivative = new SimpleMatrix(Z.numRows(), Z.numCols());
        derivative.set(0);

        return derivative;
    }
}
