package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class Gaussian implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        return Z.elementPower(2).negative().elementExp();
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        return Z.scale(-2).elementMult(Z.elementPower(2).negative().elementExp());
    }
}
