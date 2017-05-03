package Core.NeuralNetwork.Layers;

import Core.Loss.Loss;
import Core.Loss.MeanSquared;
import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Dense implements Layer {
    private SimpleMatrix output;
    private SimpleMatrix input;
    private SimpleMatrix error;
    private SimpleMatrix thetas;
    private Activation activation;
    private int units;

    public Dense(int units) {
        this.units = units + 1;
        this.activation = new Sigmoid();

        output = new SimpleMatrix(units, 1);
    }

    public Dense(Activation activation, int units) {
        this.activation = activation;
        this.units = units + 1;
    }

    // delta
    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
//        Loss loss = new MeanSquared();
//        error = loss.computeCost(output, Y);
        return error;
    }

    // a = g(Z)
    // Z = input * Q
    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        input = Z;
        output = activation.activation(input.mult(thetas));

        return output;
    }

    @Override
    public void updateWeights() {
        // delta = alpha * error * INPUT * f'(OUTPUT)
        SimpleMatrix delta = input.scale(error.elementSum()).transpose().mult(activation.derivative(input));

        thetas = thetas.plus(delta);
    }

    @Override
    public void connect(int units) {
        Initialization init = new RandomInit(-1., 1.);
        thetas = init.init(units, this.units);
    }

    @Override
    public int getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "Dense{}";
    }
}
