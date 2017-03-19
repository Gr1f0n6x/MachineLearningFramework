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

    // J = 1/2m * (sum[ (H(Xi) - Yi)^2 ] + lambda * sum[Q^2])
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y_train) {
        if(lambda > 0) {
            SimpleMatrix regularizationThetas = thetas.extractMatrix(1, thetas.numRows(), 0, thetas.numCols());
            double regularization = regularizationThetas.elementMult(regularizationThetas).elementSum() * lambda;

            return (H_predict.minus(Y_train).transpose().mult(H_predict.minus(Y_train)).elementSum() + regularization) / (2 * Y_train.numRows());

        } else {
            return H_predict.minus(Y_train).transpose().mult(H_predict.minus(Y_train)).elementSum() / (2 * Y_train.numRows());
        }
    }

    // H(X) = Q0*X0 + ... + Qn*Xn
    private SimpleMatrix predictionFunction(SimpleMatrix X_train) {
        return X_train.mult(thetas);
    }

    // G(Q, X) = Qj - alpha * 1/m * sum[ (H(X) - Y). * Xj ]
    private double gradientDescent(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix H_predict, int feature) {
        return thetas.get(feature, 0) - alpha * H_predict.minus(Y_train).elementMult(X_train.extractVector(false, feature)).elementSum() / Y_train.numRows();
    }

    // G(Q, X) = Qj - alpha * (1/m * sum[ (H(X) - Y). * Xj ] + lambda/m * Qj)
    private double gradientDescentWithRegularization(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix H_predict, int feature) {
        return thetas.get(feature, 0) - alpha * (H_predict.minus(Y_train).elementMult(X_train.extractVector(false, feature)).elementSum() / Y_train.numRows() + lambda * thetas.get(feature, 0)/ Y_train.numRows());
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X_train);
        thetas = new SimpleMatrix(Train.numCols(), 1);
        J_history = new SimpleMatrix(epochNum, 2);

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_theta = predictionFunction(Train);

            double cost = costFunction(H_theta, Y_train);

            J_history.set(epoch, 0, cost);
            J_history.set(epoch, 1, epoch);

            SimpleMatrix newThetas = new SimpleMatrix(Train.numCols(), 1);

            double theta = gradientDescent(Train, Y_train, H_theta, 0);
            newThetas.set(0, 0, theta);

            for(int feature = 0; feature < Train.numCols(); ++feature) {
                theta = gradientDescentWithRegularization(Train, Y_train, H_theta, feature);
                newThetas.set(feature, 0, theta);
            }

            thetas = newThetas;
        }
    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        return DataSetUtilities.addColumnOfOnes(X).mult(thetas);
    }

    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", DataSetUtilities.toArray(J_history, 0, 1));
        XYLineChart.plot();
    }
}