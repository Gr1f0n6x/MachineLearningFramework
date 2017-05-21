package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Identity;
import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import Utilities.DataSetUtilities;
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

    private SimpleMatrix momentum;

    public Dense(int units) {
        this.units = units;
        this.activation = new Identity();

        output = new SimpleMatrix(units, 1);
    }

    public Dense(Activation activation, int units) {
        this.activation = activation;
        this.units = units;
    }

    // delta
    @Override
    public SimpleMatrix computeError(SimpleMatrix delta) {
// OK
//        error = delta.mult(activation.derivative(input.mult(thetas)));
//
//        return delta;

        error = activation.derivative(input.mult(thetas)).scale(delta.elementSum());

        return thetas.mult(error.transpose());
    }

    // a = g(Z)
    // Z = input * Q
    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        input = Z;

        output = activation.activation(input.mult(thetas));
        output = DataSetUtilities.addColumnOfOnes(output);

        return output;
    }

    @Override
    public void updateWeights(double rate) {
        SimpleMatrix delta = input.transpose().mult(error);

        thetas = thetas.plus(delta.scale(rate));

//        if(momentum != null) {
//            thetas = thetas.plus(momentum);
//        }
//
//        momentum = delta;
    }

    @Override
    public void connect(int units) {
        Initialization init = new RandomInit(-1.0, 1.0);
        thetas = init.init(units, this.units);
    }

    @Override
    public SimpleMatrix getWeights() {
        return thetas;
    }

    @Override
    public int getUnits() {
        return units + 1;
    }

    @Override
    public String toString() {
        return "Dense{}";
    }
}
