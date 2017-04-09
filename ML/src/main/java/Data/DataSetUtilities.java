package Data;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 16.03.2017.
 */
public class DataSetUtilities {

    /**
     *
     * @param matrix - original data set
     * @param startColumn - number of first column for training set
     * @param endColumn - number of last column for training set
     * @return - copied training set
     */
    public static SimpleMatrix getTrainingSet(SimpleMatrix matrix, int startColumn, int endColumn) {
        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()
                || startColumn < 0 || endColumn < 0) {
            throw new IllegalArgumentException("Arguments: startColumn && endColumn should be less than matrix.numCols and > 0");
        }

        return matrix.extractMatrix(0, matrix.numRows(), startColumn, endColumn + 1);
    }

    /**
     *
     * @param matrix - original data set
     * @param column - number of column for training set
     * @return - copied training set
     */
    public static SimpleMatrix getTrainingSet(SimpleMatrix matrix, int column) {
        if(column > matrix.numCols() || column < 0) {
            throw new IllegalArgumentException("Argument: column should be less than matrix.numCols and > 0");
        }

        return matrix.extractMatrix(0, matrix.numRows(), column, column + 1);
    }

    /**
     *
     * @param matrix - original data set
     * @param column - number of column for answering set
     * @return - copied training set
     */
    public static SimpleMatrix getAnswersSet(SimpleMatrix matrix, int column) {
        if(column > matrix.numCols() || column < 0) {
            throw new IllegalArgumentException("Argument: column should be less than matrix.numCols and > 0");
        }

        return matrix.extractMatrix(0, matrix.numRows(), column, column + 1);
    }

    /**
     *
     * @param A - matrix A
     * @param B - matrix B
     * @return - new matrix consists of matrix A and B
     */
    public static SimpleMatrix addColumns(SimpleMatrix A, SimpleMatrix B) {
        return A.combine(0, A.numCols(), B);
    }

    /**
     *
     * @param A - matrix A
     * @return - new matrix with first column of ones + columns from A
     */
    public static SimpleMatrix addColumnOfOnes(SimpleMatrix A) {
        SimpleMatrix ones = new SimpleMatrix(A.numRows(), 1);
        ones.set(1);
        return ones.combine(0, ones.numCols(), A);
    }

    /**
     *
     * @param matrix - initial matrix
     * @param column - the removing column number
     * @return - copy of initial matrix without removed column
     */
    public static SimpleMatrix removeColumn(SimpleMatrix matrix, int column) {
        if(column > matrix.numCols() || column < 0) {
            throw new IllegalArgumentException("Argument: column should be less than matrix.numCols and > 0");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, column);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), column + 1, matrix.numCols());

        matrix.print();

        return A.combine(0, A.numCols(), B);
    }

    /**
     *
     * @param matrix - initial matrix
     * @param startColumn - the first removing column number
     * @param endColumn - the last removing column number
     * @return - copy of initial matrix without removed columns
     */
    public static SimpleMatrix removeColumns(SimpleMatrix matrix, int startColumn, int endColumn) {
        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()
                || startColumn < 0 || endColumn < 0) {
            throw new IllegalArgumentException("Arguments: startColumn && endColumn should be less than matrix.numCols and > 0");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, startColumn);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), endColumn + 1, matrix.numCols());

        matrix.print();

        return A.combine(0, A.numCols(), B);
    }

    /**
     * This method is needed for getting data for plotting
     * Use this only for getting double[][] consists of X and Y columns
     * @param matrix - initial matrix
     * @return - double[][] array
     */
    public static double[][] toArray(SimpleMatrix matrix) {
        double array[][] = new double[matrix.numRows()][matrix.numCols()];

        for (int i = 0; i < matrix.numRows(); ++i) {
            for (int j = 0; j < matrix.numCols(); ++j) {
                array[i][j] = matrix.get(i,j);
            }
        }

        return array;
    }

    /**
     * This method is needed for getting data for plotting
     * Use this only for getting double[][] consists of X and Y columns
     * @param matrix - initial matrix
     * @param XColumn - the number column for X
     * @param YColumn - the number column for Y
     * @return - double[][] array
     */
    public static double[][] toArray(SimpleMatrix matrix, int XColumn, int YColumn) {
        if(XColumn > matrix.numCols() || YColumn > matrix.numCols()
                || XColumn < 0 || YColumn < 0) {
            throw new IllegalArgumentException("Arguments: XColumn && YColumn should be less than matrix.numCols and > 0");
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

    /**
     * Fisher-Yates shuffle implementation
     * @param data - original dataSet
     */
    public static void shuffle(SimpleMatrix data) {
        int rows = data.numRows();

        for (int i = 0; i < rows; i++) {
            int random = i + (int) (Math.random() * (rows - i));

            double[] randomElement = data.extractVector(true, random).getMatrix().getData();
            data.setRow(random, 0, data.extractVector(true, i).getMatrix().getData());
            data.setRow(i, 0, randomElement);
        }
    }

    /**
     * Fisher-Yates shuffle implementation
     * @param X
     * @param Y
     */
    public static void shuffle(SimpleMatrix X, SimpleMatrix Y) {
        int rows = X.numRows();

        for (int i = 0; i < rows; i++) {
            int random = i + (int) (Math.random() * (rows - i));

            double[] randomElement = X.extractVector(true, random).getMatrix().getData();
            X.setRow(random, 0, X.extractVector(true, i).getMatrix().getData());
            X.setRow(i, 0, randomElement);

            randomElement = Y.extractVector(true, random).getMatrix().getData();
            Y.setRow(random, 0, Y.extractVector(true, i).getMatrix().getData());
            Y.setRow(i, 0, randomElement);
        }
    }

    /**
     * Return matrix of sets: [X_train, Y_train, X_CV, Y_CV]
     * @param xTrain
     * @param yTrain
     * @param part
     * @param shuffle
     * @return - matrix of sets
     */
    public static SimpleMatrix[] getCrossValidationAndTrainSets(SimpleMatrix xTrain, SimpleMatrix yTrain, double part, boolean shuffle) {
        if(part < 0 || part > 0.5) {
            throw new IllegalArgumentException("Argument: part should be > 0 and < 0.5");
        }

        if(shuffle) {
            shuffle(xTrain, yTrain);
        }

        int rows = xTrain.numRows();
        int cvPart = (int) Math.round(rows * part);

        SimpleMatrix[] result = new SimpleMatrix[] {
                xTrain.extractMatrix(0, rows - cvPart, 0, xTrain.numCols()),
                yTrain.extractMatrix(0, rows - cvPart, 0, yTrain.numCols()),
                xTrain.extractMatrix(rows - cvPart, rows, 0, xTrain.numCols()),
                yTrain.extractMatrix(rows - cvPart, rows, 0, yTrain.numCols())
        };

        return result;
    }

    /**
     *
     * @param data
     * @param part
     * @param shuffle
     * @return
     */
    public static SimpleMatrix[] getCrossValidationAndTrainSets(SimpleMatrix data, double part, boolean shuffle) {
        if(part < 0 || part > 0.5) {
            throw new IllegalArgumentException("Argument: part should be > 0 and < 0.5");
        }

        if(shuffle) {
            shuffle(data);
        }

        int rows = data.numRows();
        int cvPart = (int) Math.round(rows * part);

        SimpleMatrix[] result = new SimpleMatrix[] {
                data.extractMatrix(0, rows - cvPart, 0, data.numCols()),
                data.extractMatrix(rows - cvPart, rows, 0, data.numCols())
        };

        return result;
    }
}
