package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Input implements Layer {
    private SimpleMatrix output;
    private SimpleMatrix input;
    private SimpleMatrix thetas;
    private int units;

    public Input(int units) {
        this.units = units + 1;
    }

    @Override
    public SimpleMatrix getOutput() {
        return output;
    }

    public void setoutput(SimpleMatrix output) {
        this.output = output;
    }

    public void setThetas(SimpleMatrix thetas) {
        this.thetas = thetas;
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    // z(i) = Q(i) * x(i)
    @Override
    public SimpleMatrix feedforward(SimpleMatrix A) {
        input = A;
        output = input.mult(thetas);

        return output;
    }

    @Override
    public int getUnits() {
        return units;
    }

    // with another layer
    @Override
    public void connect(int units) {
        thetas = new SimpleMatrix(this.units, units);
    }

    @Override
    public void updateWeights(SimpleMatrix delta) {
        SimpleMatrix gradient = input.scale(delta.elementSum()).transpose();
        // alpha = 0.1 (learning rate)
        thetas = thetas.plus(gradient.scale(0.1));
    }

    @Override
    public String toString() {
        return "Input{}";
    }
}
