package Core.NeuralNetwork.Models;

import Core.NeuralNetwork.Layers.Layer;
import Core.NeuralNetwork.Optimizers.Optimizer;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public interface Model {
    void fit(SimpleMatrix X, SimpleMatrix Y, int batchSize, int epochNum);
    SimpleMatrix predict(SimpleMatrix X);
    ArrayList<Layer> getLayers();
    double test(SimpleMatrix X, SimpleMatrix Y);
    void compile(Optimizer optimizer);
}
