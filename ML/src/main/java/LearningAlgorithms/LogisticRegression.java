package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class LogisticRegression implements Model<SimpleMatrix> {

    @Override
    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize) {

    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        return null;
    }
}
