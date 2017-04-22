import Core.kNN.KNN;
import Data.DataSet;
import Utilities.DataSetUtilities;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
//        double[][] data = new double[][] {
//                {1, 1},
//                {2, 4},
//                {3, 9},
//                {4, 16},
//                {5, 25},
//        };
//
//        SimpleMatrix dataSet = new SimpleMatrix(data);
//
//        Sequential sequential = new Sequential();
//        sequential.addLayer(new Input(1));
//        sequential.addLayer(new Output(new Identity(), 1));
//
//        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0), DataSetUtilities.getAnswersSet(dataSet, 1), 1);
//
//        sequential.predict(new SimpleMatrix(new double[][] {
//                {6}
//        })).print();
//        sequential.predict(new SimpleMatrix(new double[][] {
//                {7}
//        })).print();

        DataSet dataSet = new DataSet(Main.class.getResource("/iris.csv").toURI());

        dataSet.replaceByValue("Iris-setosa", "0");
        dataSet.replaceByValue("Iris-versicolor", "1");
        dataSet.replaceByValue("Iris-virginica", "2");

        KNN knn = new KNN(3);
        int optimalK = knn.LOO(DataSetUtilities.getTrainingSet(dataSet.getMatrix(), 0, 3), DataSetUtilities.getAnswersSet(dataSet.getMatrix(), 4), 100, 0);
        knn.setNeighbors(optimalK);

        dataSet.print();



        }
    }