package Core;

import Data.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LinearRegression {

    private double alpha;
    private double lambda;
    private SimpleMatrix thetas;
    private SimpleMatrix lossHistory;
    private SimpleMatrix lossCvHistory;

    /**
     *
     */
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
    public SimpleMatrix getThetas() {
        return thetas;
    }

    /**
     *
     * @return
     */
    public SimpleMatrix getCostHistory() {
        return lossHistory;
    }

    /**
     * H(X) = Q0*X0 + ... + Qn*Xn
     * @param X_train
     * @return
     */
    private SimpleMatrix predictionFunction(SimpleMatrix X_train, SimpleMatrix thetas) {
        return X_train.mult(thetas);
    }

    /**
     * J = 1/2m * (sum[ (H(Xi) - Yi)^2 ] + lambda * sum[Q^2])
     * @param H_predict
     * @param Y_train
     * @return
     */
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y_train, SimpleMatrix thetas, double lambda) {
        if(lambda > 0) {
            return H_predict.minus(Y_train).elementPower(2).elementSum() / (2 * Y_train.numRows()) + lambda / (2 * Y_train.numRows()) * thetas.extractMatrix(1, thetas.numRows(), 0, 1).elementPower(2).elementSum();
        } else {
            return H_predict.minus(Y_train).elementPower(2).elementSum() / (2 * Y_train.numRows());
        }
    }

    /**
     * G(Q, X) = Q - [alpha / m * [ X' * (H(X) - Y) ] + lambda * alpha * Q / m]
     * @param X
     * @param Y
     * @param H
     * @return
     */
    private SimpleMatrix gradient(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H, SimpleMatrix thetas) {
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

    /**
     *
     * @param X
     * @param Y
     * @param epochNum
     */
    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum) {
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix thetas = new SimpleMatrix(Train.numCols(), 1);
        lossHistory = new SimpleMatrix(epochNum, 2);

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_predict = predictionFunction(Train, thetas);

            double cost = costFunction(H_predict, Y, thetas, lambda);

            lossHistory.set(epoch, 0, epoch);
            lossHistory.set(epoch, 1, cost);

            thetas = gradient(Train, Y, H_predict, thetas);
        }

        this.thetas = thetas;
    }

    /**
     *
     * @param X
     * @param Y
     * @param epochNum
     * @param crossValidationPart
     * @param shuffle
     */
    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum, double crossValidationPart, boolean shuffle) {
        SimpleMatrix[] sets = DataSetUtilities.getCrossValidationAndTrainSets(X.copy(), Y.copy(), crossValidationPart, shuffle);

        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(sets[0]);
        SimpleMatrix Y_train = sets[1];
        SimpleMatrix X_cv = DataSetUtilities.addColumnOfOnes(sets[2]);
        SimpleMatrix Y_cv = sets[3];

        SimpleMatrix thetas = new SimpleMatrix(X_train.numCols(), 1);
        lossHistory = new SimpleMatrix(epochNum, 2);
        lossCvHistory = new SimpleMatrix(epochNum, 2);

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_predict_train = predictionFunction(X_train, thetas);
            double costTrain = costFunction(H_predict_train, Y_train, thetas, lambda);

            lossHistory.set(epoch, 0, epoch);
            lossHistory.set(epoch, 1, costTrain);

            SimpleMatrix H_predict_cv = predictionFunction(X_cv, thetas);
            double costCv = costFunction(H_predict_cv, Y_cv, thetas, 0);

            lossCvHistory.set(epoch, 0, epoch);
            lossCvHistory.set(epoch, 1, costCv);

            thetas = gradient(X_train, Y_train, H_predict_train ,thetas);
        }

        this.thetas = thetas;
    }

    /**
     *
     * @param X
     * @return
     */
    public SimpleMatrix predict(SimpleMatrix X) {
        return predictionFunction(DataSetUtilities.addColumnOfOnes(X), thetas);
    }

    /**
     *
     */
    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = null;

        if(lossCvHistory != null) {
            XYLineChart = new XYLineChart("CostFunction", new SimpleMatrix[] {lossHistory, lossCvHistory}, true);
        } else {
            XYLineChart = new XYLineChart("CostFunction", DataSetUtilities.toArray(lossHistory, 0, 1));
        }
        XYLineChart.plot();
    }
}