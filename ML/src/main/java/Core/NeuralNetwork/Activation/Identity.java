package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class Identity implements Activation {
    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        return Z;
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix derivative = new SimpleMatrix(Z);
        derivative.set(1.0);

        return derivative;
    }
}
