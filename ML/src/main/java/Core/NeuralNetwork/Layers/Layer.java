package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public interface Layer {
    void connect(int units);
    int getUnits();
    SimpleMatrix feedforward(SimpleMatrix A);
    SimpleMatrix computeError(SimpleMatrix Y);
    void updateWeights(double rate);
    SimpleMatrix getWeights();
}
