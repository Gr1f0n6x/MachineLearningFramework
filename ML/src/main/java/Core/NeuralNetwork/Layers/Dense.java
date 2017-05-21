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
public class Dense extends Layer {
    public Dense(int units) {
        this.units = units;
        this.activation = new Identity();

        output = new SimpleMatrix(units, 1);
    }

    public Dense(Activation activation, int units) {
        this.activation = activation;
        this.units = units;
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix delta) {
        error = activation.derivative(input.mult(thetas)).scale(delta.elementSum());

        return thetas.mult(error.transpose());
    }

    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        input = Z;

        output = activation.activation(input.mult(thetas));
        output = DataSetUtilities.addColumnOfOnes(output);

        return output;
    }

    @Override
    public String toString() {
        return "Dense{}";
    }
}
