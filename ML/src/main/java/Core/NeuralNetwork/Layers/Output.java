package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output implements Layer {
    private SimpleMatrix neurons;
    private int units;

    public Output(int units) {
        this.units =  units;
        //neurons = new SimpleMatrix(units, 1);
    }

    @Override
    public void setNeurons(SimpleMatrix neurons) {
        this.neurons = neurons;
    }

    @Override
    public void setThetas(SimpleMatrix[] thetas) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public SimpleMatrix computeError(SimpleMatrix Y) {
        return neurons.minus(Y);
    }

    @Override
    public SimpleMatrix feedforward() {
        return neurons;
    }

    @Override
    public void connect(int units) {
        throw new UnsupportedOperationException("Incorrect call");
    }

    @Override
    public SimpleMatrix[] getThetas() {
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
