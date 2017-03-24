package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

import java.util.stream.DoubleStream;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression implements Model<SimpleMatrix> {
    private SimpleMatrix[] thetas;
    private SimpleMatrix[] J_history;
    private double[] classList;
    private double alpha;

    public LogisticRegression() {
        this(1);
    }

    public LogisticRegression(double alpha) {
        this.alpha = alpha;
    }

    public SimpleMatrix[] getThetas() {
        return thetas;
    }

    public SimpleMatrix getTheta() {
        return thetas[0];
    }

    public SimpleMatrix[] getCostHistory() {
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
    private SimpleMatrix predictionFunction(SimpleMatrix X, SimpleMatrix theta) {
        //exp(- X * Thetas)
        SimpleMatrix A = X.mult(theta).negative().elementExp();
        SimpleMatrix ones = new SimpleMatrix(X.numRows(), 1);
        ones.set(1);

        return ones.elementDiv(A.plus(1));
    }

    // Qj = Qj - alpha / m * sum [(H - Y) .* Xj]
    private double gradientDescent(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H_predict, SimpleMatrix theta, int feature) {
        return theta.get(feature, 0) - alpha * H_predict.minus(Y).elementMult(X.extractVector(false, feature)).elementSum() / Y.numRows();
    }

    private double[] getClassList(SimpleMatrix Y) {
        return DoubleStream.of(Y.getMatrix().getData()).distinct().toArray();
    }

    private SimpleMatrix createOneVsALlAnswerMatrix(SimpleMatrix Y, double classId) {
        SimpleMatrix oneVsALl = Y.copy();

        for(int row = 0; row < Y.numRows(); ++row) {
            for(int col = 0; col < Y.numCols(); ++col) {
                if(oneVsALl.get(row, col) != classId) {
                    oneVsALl.set(row, col, 0);
                } else {
                    oneVsALl.set(row, col, 1);
                }
            }
        }

        return oneVsALl;
    }

    private void multiClassClassification(SimpleMatrix Train, SimpleMatrix Y_train, int epochNum) {

        for(int i = 0; i < classList.length; ++i) {

            thetas[i] = new SimpleMatrix(Train.numCols(), 1);
            J_history[i] = new SimpleMatrix(epochNum, 2);
            SimpleMatrix oneVsAll = createOneVsALlAnswerMatrix(Y_train, classList[i]);

            for (int epoch = 0; epoch < epochNum; ++epoch) {
                SimpleMatrix H_theta = predictionFunction(Train, thetas[i]);
                double cost = costFunction(H_theta, oneVsAll);

                J_history[i].set(epoch, 0, epoch);
                J_history[i].set(epoch, 1, cost);

                SimpleMatrix newThetas = new SimpleMatrix(thetas[i].numRows(), 1);

                for (int feature = 0; feature < Train.numCols(); ++feature) {
                    newThetas.set(feature, 0, gradientDescent(Train, oneVsAll, H_theta, thetas[i], feature));
                }

                thetas[i] = newThetas;
            }


        }
    }

    private void binaryClassClassification(SimpleMatrix Train, SimpleMatrix Y_train, int epochNum) {
        thetas[0] = new SimpleMatrix(Train.numCols(), 1);
        J_history[0] = new SimpleMatrix(epochNum, 2);

        for (int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_theta = predictionFunction(Train, thetas[0]);
            double cost = costFunction(H_theta, Y_train);

            J_history[0].set(epoch, 0, epoch);
            J_history[0].set(epoch, 1, cost);

            SimpleMatrix newThetas = new SimpleMatrix(thetas[0].numRows(), 1);

            for (int feature = 0; feature < Train.numCols(); ++feature) {
                newThetas.set(feature, 0, gradientDescent(Train, Y_train, H_theta, thetas[0], feature));
            }

            thetas[0] = newThetas;
        }
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X_train);
        classList = getClassList(Y_train);
        thetas = new SimpleMatrix[classList.length > 2 ? classList.length : 1];
        J_history = new SimpleMatrix[classList.length > 2 ? classList.length : 1];

        if(classList.length > 2) {
            multiClassClassification(Train, Y_train, epochNum);
        } else {
            binaryClassClassification(Train, Y_train, epochNum);
        }
    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix Test = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix prediction = predictionFunction(Test, thetas[0]);

        for(int i = 0; i < thetas.length; ++i) {

            SimpleMatrix currentPrediction = predictionFunction(Test, thetas[i]);

            for(int row = 0; row < prediction.numRows(); ++row) {
                for(int col = 0; col < prediction.numCols(); ++col) {
                    prediction.set(row, col, prediction.get(row, col) < currentPrediction.get(row, col) ? currentPrediction.get(row, col) : prediction.get(row, col));
                }
            }
        }

        for(int row = 0; row < prediction.numRows(); ++row) {
            for(int col = 0; col < prediction.numCols(); ++col) {
                prediction.set(row, col, prediction.get(row, col) > 0.5 ? 1. : 0 );
            }
        }

        return prediction;
    }

    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", J_history, true);
        XYLineChart.plot();
    }
}