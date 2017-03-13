package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LinearRegression implements LearningAlgorithm {

    private double alpha;
    private SimpleMatrix thetas;

    public LinearRegression() {
        this(0);
    }

    public LinearRegression(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {
        thetas = new SimpleMatrix(X_train.numCols(), 1);

        for(int i = 0; i < epochNum; ++i) {

        }
    }
}
