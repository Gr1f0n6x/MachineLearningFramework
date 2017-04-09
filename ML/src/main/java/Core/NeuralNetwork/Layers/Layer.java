package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public interface Layer {
    void connect(int units);
    SimpleMatrix[] getThetas();
    int getUnits();
    SimpleMatrix activate();
    void setNeurons(SimpleMatrix neurons);
    void setThetas(SimpleMatrix[] thetas);
}
