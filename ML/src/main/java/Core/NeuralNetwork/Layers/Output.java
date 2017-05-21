package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Identity;
import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Initialization.Initialization;
import Core.NeuralNetwork.Initialization.RandomInit;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output extends Layer {
    public Output(int units) {
        this.units =  units;
        this.activation = new Identity();

        output = new SimpleMatrix(units, 1);
    }

    public Output(Activation activation, int units) {
        this.activation = activation;
        this.units = units;

        output = new SimpleMatrix(units, 1);
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix delta) {
        error = delta.elementMult(activation.derivative(input.mult(thetas)));

        return thetas.mult(error.transpose());
    }

    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        input = Z;
        output = activation.activation(input.mult(thetas));

        return output;
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
