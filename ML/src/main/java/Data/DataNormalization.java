package Data;

import org.ejml.simple.SimpleMatrix;

import java.util.stream.DoubleStream;

/**
 * Created by GrIfOn on 16.03.2017.
 */
public class DataNormalization {

    public static SimpleMatrix minMaxNormalization(SimpleMatrix A) {
        SimpleMatrix result = new SimpleMatrix(A.numRows(), A.numCols());

        for(int col = 0; col < A.numCols(); ++col) {
            double max = DoubleStream.of(A.extractVector(false, col).getMatrix().getData()).max().getAsDouble();
            double min = DoubleStream.of(A.extractVector(false, col).getMatrix().getData()).min().getAsDouble();

            result.setColumn(col, 0, A.extractVector(false, col).minus(min).divide(max - min).getMatrix().getData());
        }

        return result;
    }

    public static SimpleMatrix minMaxNormalization(SimpleMatrix Train, SimpleMatrix Predict) {
        SimpleMatrix result = new SimpleMatrix(Predict.numRows(), Predict.numCols());

        for(int col = 0; col < Train.numCols(); ++col) {
            double max = DoubleStream.of(Train.extractVector(false, col).getMatrix().getData()).max().getAsDouble();
            double min = DoubleStream.of(Train.extractVector(false, col).getMatrix().getData()).min().getAsDouble();

            result.setColumn(col, 0, Predict.extractVector(false, col).minus(min).divide(max - min).getMatrix().getData());
        }

        return result;
    }
}
