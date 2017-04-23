package Core;

import Utilities.DataSetUtilities;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

import java.util.stream.DoubleStream;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression {
    private SimpleMatrix[] thetas;
    private SimpleMatrix lossHistory;
    private SimpleMatrix lossCvHistory;
    private double[] classList;
    private double[] classListCv;
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
    public SimpleMatrix getCostHistory() {
        return lossHistory;
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

    /**
     * cost = 1/m * sum[-Y .* log(H) - (1 - Y) .* log(1 - H)] + lambda / (2 * m) * Q^2
     * @param H_predict
     * @param Y
     * @param classNumber
     * @return
     */
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y, SimpleMatrix[] thetas, double lambda, int classNumber) {
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

    /**
     * sigmoid
     *  1 ./ (1 + exp(- X * Thetas))
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

    /**
     * G(Q, X) = Q - (alpha / m * [ X' * (H(X) - Y) ] + lambda * alpha / m * Q)
     * @param X
     * @param Y
     * @param H
     * @param theta
     * @return
     */
    private SimpleMatrix gradient(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H, SimpleMatrix theta, double lambda) {
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
        SimpleMatrix oneVsAll = Y.copy();

        for(int row = 0; row < Y.numRows(); ++row) {
            for(int col = 0; col < Y.numCols(); ++col) {
                if(oneVsAll.get(row, col) != classId) {
                    oneVsAll.set(row, col, 0);
                } else {
                    oneVsAll.set(row, col, 1);
                }
            }
        }

        return oneVsAll;
    }

    /**
     *
     * @param Train
     * @param Y_train
     * @param epochNum
     */
    private void multiClassClassification(SimpleMatrix Train, SimpleMatrix Y_train, SimpleMatrix[] thetas, int epochNum) {

        for(int i = 0; i < classList.length; ++i) {

            thetas[i] = new SimpleMatrix(Train.numCols(), 1);
            SimpleMatrix oneVsAll = createOneVsALlAnswerMatrix(Y_train, classList[i]);

            for (int epoch = 0; epoch < epochNum; ++epoch) {
                SimpleMatrix H_predict = predictionFunction(Train, thetas[i]);
                double cost = costFunction(H_predict, oneVsAll, thetas, lambda, i);

                lossHistory.set(epoch, 0, epoch);
                lossHistory.set(epoch, 1, lossHistory.get(epoch, 1) + cost);

                thetas[i] = gradient(Train, oneVsAll, H_predict, thetas[i], lambda);
            }


        }
    }

    private void multiClassClassification(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix X_cv, SimpleMatrix Y_cv, SimpleMatrix[] thetas, int epochNum) {

        for(int i = 0; i < classList.length; ++i) {

            thetas[i] = new SimpleMatrix(X_train.numCols(), 1);
            SimpleMatrix oneVsAllTrain = createOneVsALlAnswerMatrix(Y_train, classList[i]);
            SimpleMatrix oneVsAllCv = createOneVsALlAnswerMatrix(Y_cv, classListCv[i % classListCv.length]);

            for (int epoch = 0; epoch < epochNum; ++epoch) {
                SimpleMatrix H_predict_train = predictionFunction(X_train, thetas[0]);
                double costTrain = costFunction(H_predict_train, oneVsAllTrain, thetas, lambda, 0);

                lossHistory.set(epoch, 0, epoch);
                lossHistory.set(epoch, 1, lossHistory.get(epoch, 1) + costTrain);

                SimpleMatrix H_predict_cv = predictionFunction(X_cv, thetas[0]);
                double costCv = costFunction(H_predict_cv, oneVsAllCv, thetas, 0, 0);

                lossCvHistory.set(epoch, 0, epoch);
                lossCvHistory.set(epoch, 1, lossCvHistory.get(epoch, 1) + costCv);

                thetas[i] = gradient(X_train, oneVsAllTrain, H_predict_train, thetas[0], lambda);
            }


        }
    }

    /**
     *
     * @param X_train
     * @param Y_train
     * @param epochNum
     */
    private void binaryClassClassification(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix[] thetas, int epochNum) {
        thetas[0] = new SimpleMatrix(X_train.numCols(), 1);

        for (int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_predict = predictionFunction(X_train, thetas[0]);
            double cost = costFunction(H_predict, Y_train, thetas, lambda, 0);

            lossHistory.set(epoch, 0, epoch);
            lossHistory.set(epoch, 1, lossHistory.get(epoch, 1) + cost);

            thetas[0] = gradient(X_train, Y_train, H_predict, thetas[0], lambda);
        }
    }

    private void binaryClassClassification(SimpleMatrix X_train, SimpleMatrix Y_train, SimpleMatrix X_cv, SimpleMatrix Y_cv, SimpleMatrix[] thetas, int epochNum) {
        thetas[0] = new SimpleMatrix(X_train.numCols(), 1);

        for (int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix H_predict_train = predictionFunction(X_train, thetas[0]);
            double costTrain = costFunction(H_predict_train, Y_train, thetas, lambda, 0);

            lossHistory.set(epoch, 0, epoch);
            lossHistory.set(epoch, 1, lossHistory.get(epoch, 1) + costTrain);

            SimpleMatrix H_predict_cv = predictionFunction(X_cv, thetas[0]);
            double costCv = costFunction(H_predict_cv, Y_cv, thetas, 0, 0);

            lossCvHistory.set(epoch, 0, epoch);
            lossCvHistory.set(epoch, 1, lossCvHistory.get(epoch, 1) + costCv);

            thetas[0] = gradient(X_train, Y_train, H_predict_train, thetas[0], lambda);
        }
    }

    /**
     *
     * @param X_train
     * @param Y_train
     * @param epochNum
     */
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum) {
        SimpleMatrix Train = DataSetUtilities.addColumnOfOnes(X_train);
        classList = getClassList(Y_train);
        SimpleMatrix[] thetas = new SimpleMatrix[classList.length > 2 ? classList.length : 1];
        lossHistory = new SimpleMatrix(epochNum, 2);

        if(classList.length > 2) {
            multiClassClassification(Train, Y_train, thetas, epochNum);
        } else {
            binaryClassClassification(Train, Y_train, thetas, epochNum);
        }

        this.thetas = thetas;
    }

    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum, double crossValidationPart, boolean shuffle) {
        SimpleMatrix[] sets = DataSetUtilities.getCrossValidationAndTrainSets(X.copy(), Y.copy(), crossValidationPart, shuffle);

        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(sets[0]);
        SimpleMatrix Y_train = sets[1];
        SimpleMatrix X_cv = DataSetUtilities.addColumnOfOnes(sets[2]);
        SimpleMatrix Y_cv = sets[3];

        classList = getClassList(Y_train);
        classListCv = getClassList(Y_cv);
        SimpleMatrix[] thetas = new SimpleMatrix[classList.length > 2 ? classList.length : 1];
        lossHistory = new SimpleMatrix(epochNum, 2);
        lossCvHistory = new SimpleMatrix(epochNum, 2);

        if(classList.length > 2) {
            multiClassClassification(X_train, Y_train, X_cv, Y_cv, thetas, epochNum);
        } else {
            binaryClassClassification(X_train, Y_train, X_cv, Y_cv, thetas, epochNum);
        }

        this.thetas = thetas;
    }

    public double test(SimpleMatrix X, SimpleMatrix Y) {
        SimpleMatrix answers = predict(X);
        DataSetUtilities.addColumns(answers, Y).print();
        double correct = 0;

        for(int row = 0; row < X.numRows(); ++row) {
            if(answers.get(row) - Y.get(row) == 0) {
                correct++;
            }
        }

        return correct / Y.numRows();
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
            int classNum = 0;
            for(int i = 0; i < thetas.length; ++i) {
                if(prediction[i].get(row, 0) > max) {
                    max = prediction[i].get(row, 0);
                    classNum = i;
                }
            }

            if(thetas.length > 1) {
                answer.set(row, 0, Math.ceil(max + classNum) - 1);
            } else {
                answer.set(row, 0, Math.round(max));
            }
        }

        return answer;
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