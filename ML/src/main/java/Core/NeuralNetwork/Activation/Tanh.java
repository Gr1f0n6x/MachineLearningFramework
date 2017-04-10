package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
// 2 ./ (1 + exp(-2*Z)) - 1
public class Tanh implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix result = new SimpleMatrix(Z);
        SimpleMatrix twos = new SimpleMatrix(Z.numRows(), Z.numCols());
        twos.set(2);

        return twos.elementDiv(result.scale(2).negative().elementExp().plus(1)).minus(1);
    }

    // 1 - g(Z)^2
    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        return activation(Z).elementPower(2).minus(1).negative();
    }
}
