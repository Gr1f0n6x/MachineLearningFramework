package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
// 1 ./ (1 + exp(-Z))
public class Sigmoid implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix result = new SimpleMatrix(Z);
        SimpleMatrix ones = new SimpleMatrix(Z.numRows(), Z.numCols());
        ones.set(1);

        return ones.elementDiv(result.negative().elementExp().plus(1));
    }

    // g(Z) .* (1 - g(Z))
    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        return activation(Z).elementMult(activation(Z).minus(1).negative());
    }
}
