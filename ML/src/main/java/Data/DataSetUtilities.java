package Data;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 16.03.2017.
 */
public class DataSetUtilities {

    public static void addColumns(SimpleMatrix A, SimpleMatrix B) {
        A = A.combine(0, A.numCols(), B);
    }

    public static void addColumnOfOnes(SimpleMatrix A) {
        SimpleMatrix ones = new SimpleMatrix(A.numRows(), 1);
        ones.set(1);
        A = ones.combine(0, A.numCols(), A);
    }

    public void removeColumn(SimpleMatrix matrix, int column) {

        if(column > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnet: column should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, column);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), column + 1, matrix.numCols());

        matrix = A.combine(0, A.numCols(), B);

        matrix.print();
    }

    public void removeColumns(SimpleMatrix matrix, int startColumn, int endColumn) {

        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: startColumn && endColumn should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, startColumn);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), endColumn + 1, matrix.numCols());

        matrix = A.combine(0, A.numCols(), B);

        matrix.print();
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
