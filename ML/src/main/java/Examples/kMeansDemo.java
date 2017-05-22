package Examples;

import Core.KMeans;
import Data.DataSet;
import Plot.ClassScatterMultipleDimensions;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 22.05.2017.
 */
public class kMeansDemo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        DataSet data = new DataSet(kMeansDemo.class.getResource("/iris.csv").toURI());

        data.replaceByValue("Iris-setosa", "0");
        data.replaceByValue("Iris-versicolor", "1");
        data.replaceByValue("Iris-virginica", "2");

        SimpleMatrix dataSet = data.getMatrix();

        KMeans kMeans = new KMeans(3);
        kMeans.fit(DataSetUtilities.extractMatrix(dataSet, 0, 3), 100);
        SimpleMatrix clustered = kMeans.markSamples(DataSetUtilities.extractMatrix(dataSet, 0, 3));
        kMeans.getCentroids().print();

        ClassScatterMultipleDimensions plotter = new ClassScatterMultipleDimensions("Iris", clustered, kMeans.getCentroids(), new String[] {
                "sepal length",
                "sepal width",
                "petal length",
                "petal width",
        });

        plotter.plot();
    }
}
