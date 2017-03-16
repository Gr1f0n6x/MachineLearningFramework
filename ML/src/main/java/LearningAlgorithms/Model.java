package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public interface Model<T> {
    void fit(SimpleMatrix X_train, SimpleMatrix Y_train, int epochNum, int batchSize);
    T predict(SimpleMatrix X);
}
