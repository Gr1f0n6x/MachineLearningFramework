package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Dense implements Layer {
    private SimpleMatrix neurons;
    private SimpleMatrix[] thetas;
    private int units;

    public Dense(int units) {
        // + bias
        this.units = units + 1;
    }

    @Override
    public void setNeurons(SimpleMatrix neurons) {
        this.neurons = neurons;
    }

    @Override
    public void setThetas(SimpleMatrix[] thetas) {
        this.thetas = thetas;
    }

    @Override
    public SimpleMatrix activate() {
        SimpleMatrix activation = new SimpleMatrix(thetas.length, 1);

        for(int i = 0; i < thetas.length; ++i) {
            activation.set(i, 0, neurons.transpose().mult(thetas[i]).elementSum());
        }

        return activation;
    }

    @Override
    public int getUnits() {
        return units;
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
            thetas[theta] = new SimpleMatrix(this.units, 1); // with bias
        }
    }

    @Override
    public String toString() {
        return "Dense{}";
    }
}
