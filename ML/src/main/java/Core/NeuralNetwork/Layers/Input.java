package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Input implements Layer {
    private SimpleMatrix output;
    private int units;

    public Input(int units) {
        this.units = units + 1;
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
    public int getUnits() {
        return units;
    }

    // with another layer
    @Override
    public void connect(int units) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public void updateWeights(double rate) {
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
