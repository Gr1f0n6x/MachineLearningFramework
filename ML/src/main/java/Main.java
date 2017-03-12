import DataSet.DataSet;
import org.ejml.data.Matrix64F;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            DataSet dataSet = new DataSet("D:\\WORK_Java\\MachineLearningFramework\\ML\\src\\main\\resources\\titanic.csv");
            //dataSet.getRowDataSet().stream().forEach((x) -> System.out.println(Arrays.toString(x)));

            //dataSet.replaceAllEmtyValues("NULL");
            //dataSet.getRowDataSet().stream().forEach((x) -> System.out.println(Arrays.toString(x)));

            Matrix64F matrix64F = dataSet.getMatrix();
            matrix64F.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
