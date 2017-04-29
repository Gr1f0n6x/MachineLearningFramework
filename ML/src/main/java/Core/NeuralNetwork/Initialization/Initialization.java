package Core.NeuralNetwork.Initialization;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 29.04.2017.
 */

@FunctionalInterface
public interface Initialization {
    SimpleMatrix init(int units, int bindings);
}
