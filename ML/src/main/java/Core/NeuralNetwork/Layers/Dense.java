package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
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
        this.activation = new Sigmoid();

        output = new SimpleMatrix(units, 1);
    }

    public Dense(Activation activation, int units) {
        this.activation = activation;
        this.units = units;
    }

    // delta
    @Override
    public SimpleMatrix computeError(SimpleMatrix delta) {
//        activation.derivative(input.mult(thetas)).print();
//        delta.print();
//        error = delta.elementMult(DataSetUtilities.addColumnOfOnes(activation.derivative(input.mult(thetas))).transpose());
//
//        return DataSetUtilities.addColumnOfOnes(thetas).mult(error);
        error = delta.mult(activation.derivative(input.mult(thetas)));

        return delta;
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
    public void updateWeights() {
//        SimpleMatrix delta = error.extractMatrix(0, error.numRows() - 1, 0 ,error.numCols()).mult(input);
//
//        thetas = thetas.plus(delta.transpose());

        SimpleMatrix delta = error.transpose().mult(input).transpose();

        thetas = thetas.plus(delta);

        if(momentum != null) {
            thetas = thetas.plus(momentum);
        }

        momentum = delta;
    }

    @Override
    public void connect(int units) {
        Initialization init = new RandomInit(-1., 1.);
        thetas = init.init(units, this.units);
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
