package Data;

import Utilities.DataSetUtilities;
import com.opencsv.CSVReader;
import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.net.URI;
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

    public DataSet(URI uri) throws IOException {
        this(Files.newBufferedReader(Paths.get(uri)));
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

    /**
     *
     * @param value
     */
    public void replaceAllEmtyValues(String value) {
        rowDataSet = rowDataSet.stream().
                map((stringArray) -> Arrays.stream(stringArray).map((x) -> "".equals(x) ? value : x).toArray(String[]::new)).
                collect(Collectors.toList());
    }

    /**
     *
     * @param oldValue
     * @param newValue
     */
    public void replaceByValue(String oldValue, String newValue) {
        rowDataSet = rowDataSet.stream().
                map((stringArray) -> Arrays.stream(stringArray).map((x) -> oldValue.equals(x) ? newValue : x).toArray(String[]::new)).
                collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param matrix
     */
    public void setMatrix(SimpleMatrix matrix) {
        if(matrix != null) {
            this.matrix = matrix;
        }
    }

    /**
     *
     * @param startColumn
     * @param endColumn
     * @return
     */
    public SimpleMatrix getTrainingSet(int startColumn, int endColumn) {
        if(matrix == null) {
            getMatrix();
        }

        return DataSetUtilities.getTrainingSet(matrix, startColumn, endColumn);
    }

    /**
     *
     * @param column
     * @return
     */
    public SimpleMatrix getTrainingSet(int column) {
        if(matrix == null) {
            getMatrix();
        }

        return DataSetUtilities.getTrainingSet(matrix, column, column + 1);
    }

    /**
     *
     * @param column
     * @return
     */
    public SimpleMatrix getAnswersSet(int column) {
        if(matrix == null) {
            getMatrix();
        }

        return DataSetUtilities.getAnswersSet(matrix, column);
    }

    /**
     *
     * @param column
     */
    public void removeColumn(int column) {
        if(matrix == null) {
            getMatrix();
        }

        matrix = DataSetUtilities.removeColumn(matrix, column);
    }

    /**
     *
     * @param startColumn
     * @param endColumn
     */
    public void removeColumns(int startColumn, int endColumn) {
        if(matrix == null) {
            getMatrix();
        }

        matrix = DataSetUtilities.removeColumns(matrix, startColumn, endColumn);
    }

    /**
     *
     * @param a
     */
    public void addColumns(SimpleMatrix a) {
        if(matrix == null) {
            getMatrix();
        }

        matrix = DataSetUtilities.addColumns(matrix, a);
    }

    /**
     *
     * @return
     */
    public double[][] toArray() {
        if(matrix == null) {
            getMatrix();
        }

        return DataSetUtilities.toArray(matrix);
    }

    /**
     *
     * @param XColumn
     * @param YColumn
     * @return
     */
    public double[][] toArray(int XColumn, int YColumn) {
        if(matrix == null) {
            getMatrix();
        }

        return DataSetUtilities.toArray(matrix, XColumn, YColumn);
    }
}
