package Core.NeuralNetwork.Layers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Output implements Layer {
    private SimpleMatrix neurons;

    public Output(int units) {
        neurons = new SimpleMatrix(units, 1);
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
    public SimpleMatrix getNeurons() {
        return neurons;
    }

    @Override
    public String toString() {
        return "Output{}";
    }
}
