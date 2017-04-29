package Core.NeuralNetwork.Initialization;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 29.04.2017.
 */
public class ZerosInit implements Initialization {

    public ZerosInit() {
    }

    @Override
    public SimpleMatrix init(int units, int bindings) {
        SimpleMatrix weights = new SimpleMatrix(units, bindings);

        for(int row = 0; row < weights.numRows(); ++row) {
            for(int col = 0; col < weights.numCols(); ++col) {
                weights.set(row, col, 0);
            }
        }

        return weights;
    }
}
