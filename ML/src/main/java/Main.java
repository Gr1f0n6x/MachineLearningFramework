import DataSet.DataSet;
import Plot.DataSetPlotter;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.demo.PieChartDemo1;
import org.jfree.ui.RefineryUtilities;

import java.io.IOException;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {
//        try {
//            DataSet dataSet = new DataSet("D:\\WORK_Java\\MachineLearningFramework\\ML\\src\\main\\resources\\titanic.csv");
//            DataSetPlotter dataSetPlotter = new DataSetPlotter("test", dataSet);
//            dataSetPlotter.plot();
//
//            dataSet.toArray(5, 0);
//
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
                {1, 1, 1},
                {2, 4, 4},
                {3, 9, 9},
                {4, 16, 16},
                {5, 25, 25},
        };


        double[][] ones = new double[][] {
                {1},
                {1},
                {1},
                {1},
                {1},
        };

        SimpleMatrix thetas = new SimpleMatrix(3, 1);
        SimpleMatrix dataSet = new SimpleMatrix(data);
        SimpleMatrix one = new SimpleMatrix(ones);

        one = one.combine(0, one.numCols(), dataSet);

        SimpleMatrix X_train = one.extractMatrix(0, one.numRows(), 0, 3);

        SimpleMatrix Y_train = one.extractMatrix(0, one.numRows(), 3, 4);

        SimpleMatrix J_history = new SimpleMatrix(2000, 2);

        for(int i = 0; i < 2000; ++i) {
            //h(theta) current
            SimpleMatrix h_theta = X_train.mult(thetas);

            //J(theta)
            double cost = h_theta.minus(Y_train).transpose().mult(h_theta.minus(Y_train)).elementSum()/10;

            J_history.set(i, 0, cost);
            J_history.set(i, 1, i);

            //gradient descent
            SimpleMatrix newThetas = new SimpleMatrix(3, 1);

            for(int j = 0; j < X_train.numCols(); ++j) {
                //System.out.println( h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5);
                double Q = thetas.get(j, 0) - 0.005 * h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5;
                newThetas.set(j, 0, Q);
            }

            thetas = newThetas;
            thetas.print();
        }

        J_history.print();
        X_train.mult(thetas).print();
        DataSet graph = new DataSet(J_history);
        DataSetPlotter dataSetPlotter = new DataSetPlotter("test", graph);
        dataSetPlotter.plot();

        //predict
        SimpleMatrix prediction = new SimpleMatrix(new double[][] {
                {1, 6, 36}
        });
        prediction.mult(thetas).print();

    }
}
