package Data;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 16.03.2017.
 */
public class DataSetUtilities {

    public static SimpleMatrix getTrainingSet(SimpleMatrix matrix, int startColumn, int endColumn) {

        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: startColumn && endColumn should be less than matrix.numCols");
        }

        return matrix.extractMatrix(0, matrix.numRows(), startColumn, endColumn);
    }

    public static SimpleMatrix getAnswersSet(SimpleMatrix matrix, int column) {

        if(column > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnet: column should be less than matrix.numCols");
        }

        return matrix.extractMatrix(0, matrix.numRows(), column, column + 1);
    }

    public static SimpleMatrix addColumns(SimpleMatrix A, SimpleMatrix B) {
        return A.combine(0, A.numCols(), B);
    }

    public static SimpleMatrix addColumnOfOnes(SimpleMatrix A) {
        SimpleMatrix ones = new SimpleMatrix(A.numRows(), 1);
        ones.set(1);
        return ones.combine(0, ones.numCols(), A);
    }

    public SimpleMatrix removeColumn(SimpleMatrix matrix, int column) {

        if(column > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnet: column should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, column);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), column + 1, matrix.numCols());

        matrix.print();

        return A.combine(0, A.numCols(), B);
    }

    public SimpleMatrix removeColumns(SimpleMatrix matrix, int startColumn, int endColumn) {

        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: startColumn && endColumn should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, startColumn);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), endColumn + 1, matrix.numCols());

        matrix.print();

        return A.combine(0, A.numCols(), B);
    }

    public static double[][] toArray(SimpleMatrix matrix) {

        double array[][] = new double[matrix.numRows()][matrix.numCols()];
        for (int i = 0; i < matrix.numRows(); ++i) {
            for (int j = 0; j < matrix.numCols(); ++j) {
                array[i][j] = matrix.get(i,j);
            }
        }

        return array;
    }

    public static double[][] toArray(SimpleMatrix matrix, int XColumn, int YColumn) {

        if(XColumn > matrix.numCols() || YColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: XColumn && YColumn should be less than matrix.numCols");
        }

        double X[] = new double[matrix.numRows()];
        double Y[] = new double[matrix.numRows()];

        for (int i = 0; i < matrix.numRows(); ++i) {
            X[i] = matrix.get(i, XColumn);
            Y[i] = matrix.get(i, YColumn);
        }

        double[][] array = new double[][] {X, Y};

        return array;
    }
}
