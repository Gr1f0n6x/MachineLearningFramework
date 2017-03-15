package Data;

import com.opencsv.CSVReader;
import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class DataSet {

    private List<String[]> rowDataSet;
    private SimpleMatrix matrix;

    public DataSet(String fileName) throws IOException {
        this(Paths.get(fileName));
    }

    public DataSet(Path file) throws IOException {
        this(Files.newBufferedReader(file));
    }

    public DataSet(File file) throws IOException {
        this(new BufferedReader(new FileReader(file)));
    }

    public DataSet(Reader file) throws IOException {
        CSVReader reader = new CSVReader(file);
        rowDataSet = reader.readAll();
    }

    public DataSet(SimpleMatrix matrix) {
        this.matrix = matrix;
    }

    public DataSet(List<String[]> rowDataSet) {
        this.rowDataSet = rowDataSet;
    }

    public List<String[]> getRowDataSet() {
        return rowDataSet;
    }

    public void print() {
        rowDataSet.stream().forEach((x) -> System.out.println(Arrays.toString(x)));
    }

    public void printMatrix() {
        matrix.print();
    }

    public void replaceAllEmtyValues(String value) {
        rowDataSet = rowDataSet.stream().
                map((stringArray) -> Arrays.stream(stringArray).map((x) -> "".equals(x) ? value : x).toArray(String[]::new)).
                collect(Collectors.toList());
    }

    public void replaceByValue(String oldValue, String newValue) {
        rowDataSet = rowDataSet.stream().
                map((stringArray) -> Arrays.stream(stringArray).map((x) -> oldValue.equals(x) ? newValue : x).toArray(String[]::new)).
                collect(Collectors.toList());
    }

    public SimpleMatrix getMatrix() {
        if(matrix == null) {
            replaceAllEmtyValues("NULL");

            int rows = rowDataSet.size();
            int columns = rowDataSet.get(0).length;

            matrix = new SimpleMatrix(rows, columns);

            for(int row = 0; row < rows; ++row) {
                for(int column = 0; column < columns; ++column) {
                    String element = rowDataSet.get(row)[column];

                    double value;
                    try {
                        value = Double.parseDouble(element);
                    } catch (Exception e) {
                        value = 0;
                    }

                    matrix.set(row, column, value);
                }
            }
        }

        return matrix;
    }

    public void setMatrix(SimpleMatrix matrix) {
        if(matrix != null) {
            this.matrix = matrix;
        }
    }

    public SimpleMatrix getTrainingSet(int startColumn, int endColumn) {
        if(matrix == null) {
            getMatrix();
        }

        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: startColumn && endColumn should be less than matrix.numCols");
        }

        return matrix.extractMatrix(0, matrix.numRows(), startColumn, endColumn);
    }

    public SimpleMatrix getAnswersSet(int column) {
        if(matrix == null) {
            getMatrix();
        }

        if(column > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnet: column should be less than matrix.numCols");
        }

        return matrix.extractMatrix(0, matrix.numRows(), column, column + 1);
    }

    public void removeColumn(int column) {
        if(matrix == null) {
            getMatrix();
        }

        if(column > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnet: column should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, column);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), column + 1, matrix.numCols());

        matrix = A.combine(0, A.numCols(), B);

        printMatrix();
    }

    public void removeColumns(int startColumn, int endColumn) {
        if(matrix == null) {
            getMatrix();
        }

        if(startColumn > matrix.numCols() || endColumn > matrix.numCols()) {
            throw new IllegalArgumentException("Argumnets: startColumn && endColumn should be less than matrix.numCols");
        }

        SimpleMatrix A = matrix.extractMatrix(0, matrix.numRows(), 0, startColumn);
        SimpleMatrix B = matrix.extractMatrix(0, matrix.numRows(), endColumn + 1, matrix.numCols());

        matrix = A.combine(0, A.numCols(), B);

        printMatrix();
    }

    public double[][] toArray() {
        if(matrix == null) {
            getMatrix();
        }

        double array[][] = new double[matrix.numRows()][matrix.numCols()];
        for (int i = 0; i < matrix.numRows(); ++i) {
            for (int j = 0; j < matrix.numCols(); ++j) {
                array[i][j] = matrix.get(i,j);
            }
        }

        return array;
    }

    public double[][] toArray(int XColumn, int YColumn) {
        if(matrix == null) {
            getMatrix();
        }

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
