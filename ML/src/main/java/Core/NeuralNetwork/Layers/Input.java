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

    @Override
    public SimpleMatrix computeGradient(SimpleMatrix A) {
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
        thetas = thetas.plus(delta.transpose());
    }

    @Override
    public String toString() {
        return "Input{}";
    }
}
