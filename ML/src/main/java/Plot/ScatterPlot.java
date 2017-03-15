package Plot;

import DataSet.DataSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

/**
 * Created by GrIfOn on 15.03.2017.
 */
public class ScatterPlot extends ApplicationFrame {
    private DefaultXYDataset xyDataset;

    public ScatterPlot(String title, DataSet dataSet) {
        super(title);

        setXyDataset(dataSet);

        this.setContentPane(getChartPanel());

    }

    public DefaultXYDataset getXyDataset() {
        return xyDataset;
    }

    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private void setXyDataset(DataSet dataSet) {
        xyDataset = new DefaultXYDataset();
        xyDataset.addSeries("Data", dataSet.toArray(0, 1));
    }

    private JPanel getChartPanel() {
        JFreeChart chart = ChartFactory.createScatterPlot("DataSet", "X", "Y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }
}
