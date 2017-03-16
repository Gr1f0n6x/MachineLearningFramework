package Plot;

import Data.DataSet;
import Data.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.Arrays;

/**
 * Created by GrIfOn on 15.03.2017.
 */
public abstract class DataSetPlotter extends ApplicationFrame implements Plotter {
    protected DefaultXYDataset xyDataset;

    public DataSetPlotter(String title) {
        super(title);
    }

    public DefaultXYDataset getXyDataset() {
        return xyDataset;
    }

    @Override
    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    protected void setXyDataset(DataSet dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet.toArray());
    }

    protected void setXyDataset(DataSet dataSet, int xColumn, int yColumn) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet.toArray(xColumn, yColumn));
    }

    protected void setXyDataset(double[][] dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet);
    }

    protected void setXyDataset(SimpleMatrix dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", DataSetUtilities.toArray(dataSet));
    }

    protected void setXyDataset(SimpleMatrix dataSet, int xColumn, int yColumn) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", DataSetUtilities.toArray(dataSet, xColumn, yColumn));
    }

    protected void setXyDataset(SimpleMatrix X, SimpleMatrix Y) {
        xyDataset = new DefaultXYDataset();

        double[][] arrayOfXY = new double[][] {X.getMatrix().getData(), Y.getMatrix().getData()};
        xyDataset.addSeries("Data", arrayOfXY);
    }

    protected abstract JPanel getChartPanel(String title);
}
