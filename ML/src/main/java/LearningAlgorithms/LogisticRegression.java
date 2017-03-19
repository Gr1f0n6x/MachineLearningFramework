package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression implements Model<SimpleMatrix> {

    // cost = 1/m * sum[-Y' .* log(H) - (1 - Y)' .* log(1 - H)]
    private double costFunction(SimpleMatrix H_predict, SimpleMatrix Y) {
        // -Y'
        SimpleMatrix Y1 = Y.negative().transpose();
        //(1 - Y)'
        //SimpleMatrix Y2 = Y.negative()
        return 0;
    }

    // sigmoid
    // 1 ./ (1 + exp(- X * Thetas))
    private SimpleMatrix predictionFunction(SimpleMatrix X) {
        return null;
    }

    // Qj = Qj - alpha / m * sum [(H - Y) .* Xj]
    private double gradientDescent(SimpleMatrix X, SimpleMatrix Y, SimpleMatrix H_predict, int feature) {
        return 0;
    }

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {

    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        return null;
    }
}
