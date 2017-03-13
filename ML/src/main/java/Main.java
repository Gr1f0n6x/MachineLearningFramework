import DataSet.DataSet;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {
//        try {
//            DataSet dataSet = new DataSet("D:\\WORK_Java\\MachineLearningFramework\\ML\\src\\main\\resources\\titanic.csv");
//            SimpleMatrix matrix = dataSet.getMatrix();
//            matrix.print();
//            dataSet.getTrainingSet(11, 12).print();
//            dataSet.removeColumn(1);
//            dataSet.removeColumns(1, 2);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        double[][] data = new double[][] {
                {1, 2, 3},
                {4, 5 ,6},
                {7, 8, 9}
        };

        SimpleMatrix thetas = new SimpleMatrix(3, 1);
        SimpleMatrix dataSet = new SimpleMatrix(data);
        thetas.print();
        dataSet.print();

        thetas.transpose().mult(dataSet.transpose()).print();
    }
}
