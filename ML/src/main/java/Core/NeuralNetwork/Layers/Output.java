package Core.NeuralNetwork.Layers;

import Core.NeuralNetwork.Activation.Activation;
import Core.NeuralNetwork.Activation.Binary;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output implements Layer {
    private SimpleMatrix output;
    private SimpleMatrix input;
    private SimpleMatrix error;
    private Activation activation;
    private int units;

    public Output(int units) {
        this.units =  units;
        this.activation = new Binary();

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
    public SimpleMatrix computeError(SimpleMatrix Y) {
        error = Y.minus(output);
        return error;
    }

    @Override
    public SimpleMatrix computeGradient(SimpleMatrix A) {
        SimpleMatrix gradient = activation.derivative(A).scale(error.elementSum());
        return gradient;
    }

    // a = g(Z)
    @Override
    public SimpleMatrix feedforward(SimpleMatrix Z) {
        input = Z;
        output = activation.activation(input);

        return output;
    }

    @Override
    public void updateWeights(SimpleMatrix delta) {
        throw new UnsupportedOperationException("Incorrect call");
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
