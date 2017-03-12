package DataSet;

import com.opencsv.CSVReader;
import org.ejml.data.BlockMatrix64F;
import org.ejml.data.Matrix64F;

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
    private BlockMatrix64F matrix64F;

    public DataSet() {
    }

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

    public List<String[]> getRowDataSet() {
        return rowDataSet;
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

    public Matrix64F getMatrix() {
        if(matrix64F == null) {
            replaceAllEmtyValues("NULL");

            int rows = rowDataSet.size();
            int columns = rowDataSet.get(0).length;

            matrix64F = new BlockMatrix64F(rows, columns);

            for(int row = 0; row < rows; ++row) {
                for(int column = 0; column < columns; ++column) {
                    String element = rowDataSet.get(row)[column];

                    double value;
                    try {
                        value = Double.parseDouble(element);
                    } catch (Exception e) {
                        value = 0;
                    }

                    matrix64F.set(row, column, value);
                }
            }
        }

        return matrix64F;
    }
}
