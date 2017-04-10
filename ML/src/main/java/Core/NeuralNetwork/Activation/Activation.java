package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public interface Activation {
    SimpleMatrix activation(SimpleMatrix Z);
    SimpleMatrix derivative(SimpleMatrix Z);
}
