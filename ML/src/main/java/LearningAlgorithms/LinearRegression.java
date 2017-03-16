package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.LineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LinearRegression implements Model<SimpleMatrix> {

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
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y_train) {
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
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X_train);
        thetas = new SimpleMatrix(Train.numCols(), 1);
        J_history = new SimpleMatrix(epochNum, 2);

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_theta = predictionFunction(Train);

            double cost = costFunction(H_theta, Y_train);

            J_history.set(epoch, 0, cost);
            J_history.set(epoch, 1, epoch);

            SimpleMatrix newThetas = new SimpleMatrix(Train.numCols(), 1);

            for(int feature = 0; feature < Train.numCols(); ++feature) {
                double theta = gradientDescent(Train, Y_train, H_theta, feature);
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
        LineChart lineChart = new LineChart("CostFunction", DataSetUtilities.toArray(J_history, 0, 1));
        lineChart.plot();
    }
}