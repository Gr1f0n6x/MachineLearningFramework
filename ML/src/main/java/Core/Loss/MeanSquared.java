package Core.Loss;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class MeanSquared implements Loss {
    /**
     * J(Q) = 1/2m * sum(h(x) - y)^2
     * @param predicted
     * @param expected
     * @return
     */
    @Override
    public double computeCost(SimpleMatrix predicted, SimpleMatrix expected) {
        int m = expected.numRows();
        return 1 / (2*m) * predicted.minus(expected).elementPower(2).elementSum();
    }

    /**
     * J(Q) = 1/2m * [sum(h(x) - y)^2 + lambda * sum(thetas^2)]
     * @param predicted
     * @param expected
     * @param thetas
     * @param lambda
     * @return
     */
    @Override
    public double computeCost(SimpleMatrix predicted, SimpleMatrix expected, SimpleMatrix thetas, double lambda) {
        int m = expected.numRows();
        return 1 / (2 * m) * (predicted.minus(expected).elementPower(2).elementSum() + lambda * thetas.extractMatrix(1, thetas.numRows(), 0, 1).elementSum());
    }
}
