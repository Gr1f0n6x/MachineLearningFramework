package Plot;

import Data.DataSet;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;

/**
 * Created by GrIfOn on 19.03.2017.
 */
public class XYClassScatterPlot extends XYClassDataSetFrame {

    public XYClassScatterPlot(String title, DataSet dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, double[][] dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, DataSet dataSet, int x1Columnm, int x2Column, int yColumn) {
        super(title);

        setXyDataset(dataSet, x1Columnm, x2Column, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, SimpleMatrix dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, SimpleMatrix dataSet, int x1Columnm, int x2Column, int yColumn) {
        super(title);

        setXyDataset(dataSet, x1Columnm, x2Column, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    @Override
    protected JPanel getChartPanel(String title) {
        JFreeChart chart = ChartFactory.createScatterPlot(title, "X", "Y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }
}
