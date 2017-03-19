package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression implements Model<SimpleMatrix> {
    private SimpleMatrix thetas;
    private SimpleMatrix J_history;
    private double alpha;

    public LogisticRegression() {
        this(1);
    }

    public LogisticRegression(double alpha) {
        this.alpha = alpha;
    }

    public SimpleMatrix getThetas() {
        return thetas;
    }

    public SimpleMatrix getCostHistory() {
        return J_history;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    // cost = 1/m * sum[-Y .* log(H) - (1 - Y) .* log(1 - H)]
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y) {
        // -Y .* log(H)
        SimpleMatrix first = Y.negative().elementMult(H_predict.elementLog());
        //(1 - Y) .* log(1 - H)
        SimpleMatrix second = Y.minus(1).negative().elementMult(H_predict.minus(1).negative().elementLog());
        return first.minus(second).elementSum() / Y.numRows();
    }

    // sigmoid
    // 1 ./ (1 + exp(- X * Thetas))
    private SimpleMatrix predictionFunction(SimpleMatrix X) {
        //exp(- X * Thetas)
        SimpleMatrix A = X.mult(thetas).negative().elementExp();
        SimpleMatrix ones = new SimpleMatrix(X.numRows(), 1);
        ones.set(1);

        return ones.elementDiv(A.plus(1));
    }

    // Qj = Qj - alpha / m * sum [(H - Y) .* Xj]
    private double gradientDescent(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H_predict, int feature) {
        return thetas.get(feature, 0) - alpha * H_predict.minus(Y).elementMult(X.extractVector(false, feature)).elementSum() / Y.numRows();
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

            SimpleMatrix newThetas = new SimpleMatrix(thetas.numRows(), 1);

            for(int feature = 0; feature < Train.numCols(); ++feature) {
                newThetas.set(feature, 0, gradientDescent(Train, Y_train, H_theta, feature));
            }

            thetas = newThetas;
        }

    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix prediction = predictionFunction(DataSetUtilities.addColumnOfOnes(X));

        for(int row = 0; row < prediction.numRows(); ++row) {
            for(int col = 0; col < prediction.numCols(); ++col) {
                prediction.set(row, col, prediction.get(row, col) > 0.5 ? 1. : 0);
            }
        }

        return prediction;
    }

    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", DataSetUtilities.toArray(J_history, 0, 1));
        XYLineChart.plot();
    }
}