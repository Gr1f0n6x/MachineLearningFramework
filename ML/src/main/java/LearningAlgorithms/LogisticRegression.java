package LearningAlgorithms;

import Data.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

import java.util.stream.DoubleStream;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression {
    private SimpleMatrix[] thetas;
    private SimpleMatrix[] J_history;
    private double[] classList;
    private double alpha;
    private double lambda;

    /**
     *
     */
    public LogisticRegression() {
        this(1);
    }

    /**
     *
     * @param alpha
     */
    public LogisticRegression(double alpha) {
        this.alpha = alpha > 0 ? alpha : 1;
        this.lambda = 0;
    }

    /**
     *
     * @param alpha
     * @param lambda
     */
    public LogisticRegression(double alpha, double lambda) {
        this.alpha = alpha > 0 ? alpha : 1;
        this.lambda = lambda > 0 ? lambda : 0;
    }

    /**
     *
     * @return
     */
    public SimpleMatrix[] getThetas() {
        return thetas;
    }

    /**
     *
     * @return
     */
    public SimpleMatrix getTheta() {
        return thetas[0];
    }

    /**
     *
     * @return
     */
    public SimpleMatrix[] getCostHistory() {
        return J_history;
    }

    /**
     *
     * @return
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     *
     * @param alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     *
     * @return
     */
    public double getLambda() {
        return lambda;
    }

    /**
     *
     * @param lambda
     */
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    // cost = 1/m * sum[-Y .* log(H) - (1 - Y) .* log(1 - H)] + lambda / (2 * m) * Q^2

    /**
     *
     * @param H_predict
     * @param Y
     * @param classNumber
     * @return
     */
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y, int classNumber) {
        if(lambda > 0) {
            // -Y .* log(H)
            SimpleMatrix first = Y.negative().elementMult(H_predict.elementLog());

            //(1 - Y) .* log(1 - H)
            SimpleMatrix second = Y.minus(1).negative().elementMult(H_predict.minus(1).negative().elementLog());

            //lambda / (2 * m) * Q^2
            SimpleMatrix third = thetas[classNumber].extractMatrix(1, thetas[classNumber].numRows(), 0, 1).elementPower(2);

            return first.minus(second).elementSum() / Y.numRows() + third.elementSum() * lambda / (2 * Y.numRows());
        } else {
            // -Y .* log(H)
            SimpleMatrix first = Y.negative().elementMult(H_predict.elementLog());

            //(1 - Y) .* log(1 - H)
            SimpleMatrix second = Y.minus(1).negative().elementMult(H_predict.minus(1).negative().elementLog());

            return first.minus(second).elementSum() / Y.numRows();
        }
    }

    // sigmoid
    // 1 ./ (1 + exp(- X * Thetas))

    /**
     *
     * @param X
     * @param theta
     * @return
     */
    private SimpleMatrix predictionFunction(SimpleMatrix X, SimpleMatrix theta) {
        //exp(- X * Thetas)
        SimpleMatrix A = X.mult(theta).negative().elementExp();
        SimpleMatrix ones = new SimpleMatrix(X.numRows(), 1);
        ones.set(1);

        return ones.elementDiv(A.plus(1));
    }

    //G(Q, X) = Q - (alpha / m * [ X' * (H(X) - Y) ] + lambda * alpha / m * Q)

    /**
     *
     * @param X
     * @param Y
     * @param H
     * @param theta
     * @return
     */
    private SimpleMatrix gradient(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H, SimpleMatrix theta) {
        if(lambda > 0) {
            SimpleMatrix newTheta = new SimpleMatrix(theta);
            newTheta.set(0, 0, 0);
            // (X' * [H - Y]) * alpha / m
            SimpleMatrix A = X.transpose().mult(H.minus(Y)).scale(alpha / Y.numRows());
            // tempThetas * lambda * alpha / m

            SimpleMatrix B = newTheta.scale(lambda * alpha / Y.numRows());
            SimpleMatrix C = A.plus(B);

            return theta.minus(C);
        } else {
            SimpleMatrix A = X.transpose().mult(H.minus(Y)).scale(alpha / Y.numRows());
            return theta.minus(A);
        }
    }

    /**
     *
     * @param Y
     * @return
     */
    private double[] getClassList(SimpleMatrix Y) {
        return DoubleStream.of(Y.getMatrix().getData()).distinct().toArray();
    }

    /**
     *
     * @param Y
     * @param classId
     * @return
     */
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

    /**
     *
     * @param Train
     * @param Y_train
     * @param epochNum
     */
    private void multiClassClassification(SimpleMatrix Train, SimpleMatrix Y_train, int epochNum) {

        for(int i = 0; i < classList.length; ++i) {

            thetas[i] = new SimpleMatrix(Train.numCols(), 1);
            J_history[i] = new SimpleMatrix(epochNum, 2);
            SimpleMatrix oneVsAll = createOneVsALlAnswerMatrix(Y_train, classList[i]);

            for (int epoch = 0; epoch < epochNum; ++epoch) {
                SimpleMatrix H_theta = predictionFunction(Train, thetas[i]);
                double cost = costFunction(H_theta, oneVsAll, i);

                J_history[i].set(epoch, 0, epoch);
                J_history[i].set(epoch, 1, cost);

                thetas[i] = gradient(Train, oneVsAll, H_theta, thetas[i]);
            }


        }
    }

    /**
     *
     * @param Train
     * @param Y_train
     * @param epochNum
     */
    private void binaryClassClassification(SimpleMatrix Train, SimpleMatrix Y_train, int epochNum) {
        thetas[0] = new SimpleMatrix(Train.numCols(), 1);
        J_history[0] = new SimpleMatrix(epochNum, 2);

        for (int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_theta = predictionFunction(Train, thetas[0]);
            double cost = costFunction(H_theta, Y_train, 0);

            J_history[0].set(epoch, 0, epoch);
            J_history[0].set(epoch, 1, cost);

            thetas[0] = gradient(Train, Y_train, H_theta, thetas[0]);
        }
    }

    /**
     *
     * @param X_train
     * @param Y_train
     * @param epochNum
     * @param batchSize
     */
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

    /**
     *
     * @param X
     * @return
     */
    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix Test = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix[] prediction = new SimpleMatrix[thetas.length];

        for(int i = 0; i < thetas.length; ++i) {
            prediction[i] = predictionFunction(Test, thetas[i]);
        }

        SimpleMatrix answer = new SimpleMatrix(prediction[0].numRows(), 1);

        for(int row = 0; row < prediction[0].numRows(); ++row) {
            double max = 0;
            for(int i = 0; i < thetas.length; ++ i) {
                if(prediction[i].get(row, 0) + i > max + i) {
                    max = prediction[i].get(row, 0) + i;
                }
            }

            answer.set(row, 0, Math.ceil(max) - 1);
        }

        return answer;
    }

    /**
     *
     */
    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", J_history, true);
        XYLineChart.plot();
    }
}