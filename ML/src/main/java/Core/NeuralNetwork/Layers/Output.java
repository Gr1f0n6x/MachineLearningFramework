package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output implements Layer {
    private SimpleMatrix output;
    private SimpleMatrix input;
    private SimpleMatrix error;
    private SimpleMatrix thetas;
    private Activation activation;
    private int units;

    private SimpleMatrix momentum;

    public Output(int units) {
        this.units =  units;
        this.activation = new Sigmoid();

        output = new SimpleMatrix(units, 1);
    }

    public Output(Activation activation, int units) {
        this.activation = activation;
        this.units = units;

        output = new SimpleMatrix(units, 1);
    }

    // delta
    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
        error = Y.minus(output).elementMult(activation.derivative(input.mult(thetas)));

        return thetas.mult(error);
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
    public void updateWeights(double rate) {
        SimpleMatrix delta = input.transpose().mult(error);

        thetas = thetas.plus(delta.scale(rate));

        if(momentum != null) {
            thetas = thetas.plus(momentum);
        }

        momentum = delta;
    }

    @Override
    public void connect(int units) {
        Initialization init = new RandomInit(0.0, 1.0);
        thetas = init.init(units, this.units);
    }

    @Override
    public int getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "Output{}";
    }
}
