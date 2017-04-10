package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class SoftPlus implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        return Z.elementExp().plus(1).elementLog();
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix ones = new SimpleMatrix(Z.numRows(), Z.numCols());
        ones.set(1);

        return ones.elementDiv(Z.negative().elementExp().plus(1));
    }
}
