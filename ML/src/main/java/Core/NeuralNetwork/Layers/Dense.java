package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Dense implements Layer {
    private SimpleMatrix neurons;
    private SimpleMatrix[] thetas;

    public Dense(int units) {
        // + bias
        neurons = new SimpleMatrix(units + 1, 1);
    }

    @Override
    public SimpleMatrix getNeurons() {
        return neurons;
    }

    @Override
    public SimpleMatrix[] getThetas() {
        return thetas;
    }

    // with another layer
    @Override
    public void connect(int units) {
        thetas = new SimpleMatrix[units]; // with bias

        for(int theta = 0; theta < thetas.length; ++theta) {
            thetas[theta] = new SimpleMatrix(neurons.numRows(), 1); // with bias
        }
    }

    @Override
    public String toString() {
        return "Dense{}";
    }
}
