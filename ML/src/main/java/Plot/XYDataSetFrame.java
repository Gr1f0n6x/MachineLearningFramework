package Plot;

import Data.DataSet;
import Data.DataSetUtilities;
import Plot.Interfaces.Plotter;
import org.ejml.simple.SimpleMatrix;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

/**
 * Created by GrIfOn on 15.03.2017.
 */
public abstract class XYDataSetFrame extends ApplicationFrame implements Plotter {
    protected DefaultXYDataset xyDataset;

    public XYDataSetFrame(String title) {
        super(title);
    }

    public DefaultXYDataset getXyDataset() {
        return xyDataset;
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(DataSet dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet.toArray());
    }

    /**
     *
     * @param dataSet
     * @param xColumn
     * @param yColumn
     */
    protected void setXyDataset(DataSet dataSet, int xColumn, int yColumn) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet.toArray(xColumn, yColumn));
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(double[][] dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet);
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(SimpleMatrix dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", DataSetUtilities.toArray(dataSet));
    }

    /**
     *
     * @param dataSet
     * @param xColumn
     * @param yColumn
     */
    protected void setXyDataset(SimpleMatrix dataSet, int xColumn, int yColumn) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", DataSetUtilities.toArray(dataSet, xColumn, yColumn));
    }

    /**
     *
     * @param X
     * @param Y
     */
    protected void setXyDataset(SimpleMatrix X, SimpleMatrix Y) {
        xyDataset = new DefaultXYDataset();

        double[][] arrayOfXY = new double[][] {X.getMatrix().getData(), Y.getMatrix().getData()};
        xyDataset.addSeries("Data", arrayOfXY);
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(SimpleMatrix[] dataSet) {
        xyDataset = new DefaultXYDataset();

        for(int i = 0; i < dataSet.length; ++i) {
            xyDataset.addSeries(String.valueOf(i), DataSetUtilities.toArray(dataSet[i]));
        }

    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(DataSet[] dataSet) {
        xyDataset = new DefaultXYDataset();

        for(int i = 0; i < dataSet.length; ++i) {
            xyDataset.addSeries(String.valueOf(i), dataSet[i].toArray());
        }

    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(double[][][] dataSet) {
        xyDataset = new DefaultXYDataset();

        for(int i = 0; i < dataSet.length; ++i) {
            xyDataset.addSeries(String.valueOf(i), dataSet[i]);
        }

    }

    protected abstract JPanel getChartPanel(String title);

    @Override
    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
