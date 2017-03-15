package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.LineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LinearRegression implements Model {

    private double alpha;
    private SimpleMatrix thetas;
    private SimpleMatrix J_history;

    public LinearRegression() {
        this(1);
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

    public SimpleMatrix getThetas() {
        return thetas;
    }

    public SimpleMatrix getCostHistory() {
        return J_history;
    }

    // J = 1/2m * sum[ (H(Xi) - Yi)^2 ]
    private double costFunction(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix H_predict) {
        return H_predict.minus(Y_train).transpose().mult(H_predict.minus(Y_train)).elementSum() / (2 * Y_train.numRows());
    }

    // H(X) = Q0*X0 + ... + Qn*Xn
    private SimpleMatrix predictionFunction(SimpleMatrix X_train) {
        return X_train.mult(thetas);
    }

    // G(Q, X) = Qj - alpha/m * sum[ (H(X) - Y). * Xj ]
    private double gradientDescent(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix H_predict, int feature) {
        return thetas.get(feature, 0) - alpha * H_predict.minus(Y_train).elementMult(X_train.extractVector(false, feature)).elementSum() / Y_train.numRows();
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {
        thetas = new SimpleMatrix(X_train.numCols(), 1);

        for(int i = 0; i < epochNum; ++i) {

        }
    }

    @Override
    public void predict(SimpleMatrix X) {

    }

    public void plotCostFunctionHistory() {
        LineChart lineChart = new LineChart("CostFunction", DataSetUtilities.toArray(J_history));
        lineChart.plot();
    }
}


/*        for(int i = 0; i < 200; ++i) {
            //h(theta) current
            SimpleMatrix h_theta = X_train.mult(thetas);

            //J(theta)
            double cost = h_theta.minus(Y_train).transpose().mult(h_theta.minus(Y_train)).elementSum()/10;

            J_history.set(i, 0, cost);
            J_history.set(i, 1, i);

            //gradient descent
            SimpleMatrix newThetas = new SimpleMatrix(new double[][] {
                    {0},
                    {0}
            });

            for(int j = 0; j < X_train.numCols(); ++j) {
                //System.out.println( h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5);
                double Q = thetas.get(j, 0) - 0.01 * h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5;
                newThetas.set(j, 0, Q);
            }

            thetas = newThetas;
            thetas.print();
        }
* */