package Core.NeuralNetwork.Optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.05.2017.
 */
public interface Optimizer {
    SimpleMatrix updateWeights(SimpleMatrix delta, SimpleMatrix thetas, SimpleMatrix previousDelta);
}
