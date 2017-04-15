package Core.NeuralNetwork.Loss;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class MeanSquaredLogarithmic implements Loss {
    /**
     * J(Q) = 1/m * sum[-Y .* log(H) - (1 - Y) .* log(1 - H)]
     * @param predicted
     * @param expected
     * @param thetas
     * @return
     */
    @Override
    public double computeCost(SimpleMatrix predicted, SimpleMatrix expected, SimpleMatrix thetas) {
        int m = expected.numRows();
        return 1 / m *
                expected.negative().elementMult(predicted.elementLog()). // -Y .* log(H)
                        minus(expected.minus(1).negative().elementMult(predicted.minus(1).negative().elementLog())). // -(1 - Y) .* log(1 - H)
                        elementSum();
    }

    /**
     * J(Q) = 1/m * sum[-Y .* log(H) - (1 - Y) .* log(1 - H)] + lambda / (2 * m) * Q^2
     * @param predicted
     * @param expected
     * @param thetas
     * @param lambda
     * @return
     */
    @Override
    public double computeCost(SimpleMatrix predicted, SimpleMatrix expected, SimpleMatrix thetas, double lambda) {
        int m = expected.numRows();
        return 1 / m *
                expected.negative().elementMult(predicted.elementLog()). // -Y .* log(H)
                        minus(expected.minus(1).negative().elementMult(predicted.minus(1).negative().elementLog())). // -(1 - Y) .* log(1 - H)
                        elementSum() + lambda / (2 * m) * thetas.extractMatrix(1, thetas.numRows(), 0 , 1).elementPower(2).elementSum(); // lambda * Q^2 / 2m
    }
}
