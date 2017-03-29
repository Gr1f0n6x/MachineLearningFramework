package LearningAlgorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class KNN {
    private SimpleMatrix distances;
    private int neighbors;

    public KNN(int neighbors) {
        this.neighbors = neighbors;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }

    public SimpleMatrix getDistances() {
        return distances;
    }

    public void fit(SimpleMatrix X_train, SimpleMatrix Y_train) {
        // 1 - distance ; 2 - class
        distances = new SimpleMatrix(Y_train.numRows(), 2);



        for(int row = 0; row < Y_train.numRows(); ++row) {
            for(int col = 0; col < X_train.numCols(); ++col) {
                distances.set(row, 0, X_train.extractVector(true, row).elementSum());
                distances.set(row, 1, Y_train.get(row, 0));
            }
        }

    }

    public SimpleMatrix predict(SimpleMatrix X) {
        return null;
    }
}
