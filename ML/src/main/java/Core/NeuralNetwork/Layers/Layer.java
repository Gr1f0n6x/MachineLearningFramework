package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import Core.NeuralNetwork.Optimizers.Optimizer;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public abstract class Layer {
    protected SimpleMatrix output;
    protected SimpleMatrix input;
    protected SimpleMatrix error;
    protected SimpleMatrix thetas;
    protected Activation activation;
    protected SimpleMatrix momentum;
    protected int units;

    public Layer() {
    }

    public void connect(int units) {
        Initialization init = new RandomInit(-1.0, 1.0);
        thetas = init.init(units, this.units);
    }

    public int getUnits() {
        return units + 1;
    }

    public abstract SimpleMatrix feedforward(SimpleMatrix A);

    public abstract SimpleMatrix computeError(SimpleMatrix Y);

    public void updateWeights(Optimizer optimizer) {
        SimpleMatrix delta = input.transpose().mult(error);
        thetas = optimizer.updateWeights(delta, thetas, momentum);

        this.momentum = delta;
    }

    public SimpleMatrix getWeights() {
        return thetas;
    }
}
