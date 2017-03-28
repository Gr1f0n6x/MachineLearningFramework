package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LinearRegression implements Model<SimpleMatrix> {

    private double alpha;
    private double lambda;
    private SimpleMatrix thetas;
    private SimpleMatrix J_history;

    public LinearRegression() {
        this(1);
    }

    /**
     *
     * @param alpha
     * @param lambda
     */
    public LinearRegression(double alpha, double lambda) {
        this.alpha = alpha > 0 ? alpha : 1;
        this.lambda = lambda > 0 ? lambda : 0;
    }

    /**
     *
     * @param alpha
     */
    public LinearRegression(double alpha) {
        this.alpha = alpha > 0 ? alpha : 1;
        this.lambda = 0;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public SimpleMatrix getThetas() {
        return thetas;
    }

    public SimpleMatrix getCostHistory() {
        return J_history;
    }

    // H(X) = Q0*X0 + ... + Qn*Xn
    private SimpleMatrix predictionFunction(SimpleMatrix X_train) {
        return X_train.mult(thetas);
    }

    // J = 1/2m * (sum[ (H(Xi) - Yi)^2 ] + lambda * sum[Q^2])
    /**
     *
     * @param H_predict
     * @param Y_train
     * @return
     */
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y_train) {
        if(lambda > 0) {
            return H_predict.minus(Y_train).elementPower(2).elementSum() / (2 * Y_train.numRows()) + lambda / (2 * Y_train.numRows()) * thetas.extractMatrix(1, thetas.numRows(), 0, 1).elementPower(2).elementSum();
        } else {
            return H_predict.minus(Y_train).elementPower(2).elementSum() / (2 * Y_train.numRows());
        }
    }

    // G(Q, X) = Q - (alpha / m * [ X' * (H(X) - Y) ] + lambda * alpha / m * Q)
    /**
     *
     * @param X
     * @param Y
     * @param H
     * @return
     */
    private SimpleMatrix gradient(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H) {
        if(lambda > 0) {
            SimpleMatrix newTheta = new SimpleMatrix(thetas);
            newTheta.set(0, 0, 0);
            // (X' * [H - Y]) * alpha / m
            SimpleMatrix A = X.transpose().mult(H.minus(Y)).scale(alpha / Y.numRows());
            // tempThetas * lambda * alpha / m
            SimpleMatrix B = newTheta.scale(lambda * alpha / Y.numRows());
            SimpleMatrix C = A.plus(B);

            return thetas.minus(C);
        } else {
            SimpleMatrix A = X.transpose().mult(H.minus(Y)).scale(alpha / Y.numRows());
            return thetas.minus(A);
        }
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X_train);
        thetas = new SimpleMatrix(Train.numCols(), 1);
        J_history = new SimpleMatrix(epochNum, 2);

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_theta = predictionFunction(Train);

            double cost = costFunction(H_theta, Y_train);

            J_history.set(epoch, 0, epoch);
            J_history.set(epoch, 1, cost);

            thetas = gradient(Train, Y_train, H_theta);
        }
    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        return predictionFunction(DataSetUtilities.addColumnOfOnes(X));
    }

    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", DataSetUtilities.toArray(J_history, 0, 1));
        XYLineChart.plot();
    }
}