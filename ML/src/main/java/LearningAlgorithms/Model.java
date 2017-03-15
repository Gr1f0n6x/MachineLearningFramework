package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public interface Model {
    void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize);
    void predict(SimpleMatrix X);
}
