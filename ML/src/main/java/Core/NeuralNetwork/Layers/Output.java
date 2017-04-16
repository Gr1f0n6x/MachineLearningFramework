package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Sigmoid;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output implements Layer {
    private SimpleMatrix output;
    private Activation activation;
    private int units;

    private SimpleMatrix error;

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

    public void setoutput(SimpleMatrix output) {
        this.output = output;
    }

    @Override
    public SimpleMatrix getOutput() {
        return output;
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
        // (H(X) - Y)
        // e = a - y
        error = output.minus(Y);
        error = activation.derivative(error);
        return error;
    }

    // a = g(Z)
    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        output = activation.activation(Z);

        return output;
    }

    @Override
    public void updateWeights(SimpleMatrix delta) {
    }

    @Override
    public void connect(int units) {
        throw new UnsupportedOperationException("Incorrect call");
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
