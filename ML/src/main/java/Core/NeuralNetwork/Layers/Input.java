package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Optimizers.Optimizer;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Input extends Layer {
    public Input(int units) {
        this.units = units;
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public SimpleMatrix feedforward(SimpleMatrix A) {
        output = A;
        return output;
    }

    @Override
    public void connect(int units) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public void updateWeights(Optimizer optimizer) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public SimpleMatrix getWeights() {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public String toString() {
        return "Input{}";
    }
}
