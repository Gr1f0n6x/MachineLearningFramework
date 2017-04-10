package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Input implements Layer {
    private SimpleMatrix neurons; // input
    private SimpleMatrix[] thetas;
    private int units;

    public Input(int units) {
        this.units = units + 1;
    }

    // X already includes bias column
    public void createInput(SimpleMatrix X) {
        neurons = new SimpleMatrix(X);
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
    public SimpleMatrix computeError(SimpleMatrix Y) {
        return null;
    }

    // z(i) = Q(i) * x(i)
    @Override
    public SimpleMatrix feedforward() {
        SimpleMatrix activation = new SimpleMatrix(thetas.length, 1);

        for(int i = 0; i < thetas.length; ++i) {
            activation.set(i, 0, neurons.mult(thetas[i]).elementSum());
        }

        return activation;
    }

    @Override
    public int getUnits() {
        return units;
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
    public SimpleMatrix[] getThetas() {
        return thetas;
    }

    @Override
    public String toString() {
        return "Input{}";
    }
}
