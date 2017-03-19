package Plot;

import Data.DataSet;
import Data.DataSetUtilities;
import Plot.Interfaces.Plotter;
import org.apache.commons.collections.map.MultiValueMap;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.JFreeChart;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Created by GrIfOn on 17.03.2017.
 */
public abstract class XYClassDataSetFrame extends ApplicationFrame implements Plotter {
    protected XYSeriesCollection xyDataset;
    protected JFreeChart chart;

    public XYClassDataSetFrame(String title) {
        super(title);
    }

    public XYSeriesCollection getXyDataset() {
        return xyDataset;
    }

    public JFreeChart getChart() {
        return chart;
    }

    @Override
    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(DataSet dataSet) {
        setXyDataset(dataSet.getMatrix());
    }

    /**
     *
     * @param dataSet
     * @param x1Column
     * @param x2Column
     * @param yColumn
     */
    protected void setXyDataset(DataSet dataSet, int x1Column, int x2Column, int yColumn) {
        SimpleMatrix X = dataSet.getTrainingSet(x1Column, x2Column);
        SimpleMatrix Y = dataSet.getAnswersSet(yColumn);

        setXyDataset(DataSetUtilities.addColumns(X, Y));
    }

    /**
     *
     * @param dataSet
     */
    protected void setXyDataset(SimpleMatrix dataSet) {
        xyDataset = new XYSeriesCollection();

        extractClassesFromDataSet(dataSet);
    }

    /**
     *
     * @param dataSet
     * @param x1Column
     * @param x2Column
     * @param yColumn
     */
    protected void setXyDataset(SimpleMatrix dataSet, int x1Column, int x2Column, int yColumn) {
        SimpleMatrix X = DataSetUtilities.getTrainingSet(dataSet, x1Column, x2Column);
        SimpleMatrix Y = DataSetUtilities.getAnswersSet(dataSet, yColumn);

        setXyDataset(DataSetUtilities.addColumns(X, Y));
    }

    public void addExtraData(SimpleMatrix A) {
        extractClassesFromDataSet(A);
    }

    public void addExtraData(DataSet A) {
        extractClassesFromDataSet(A.getMatrix());
    }

    protected abstract JPanel getChartPanel(String title);

    private void extractClassesFromDataSet(SimpleMatrix matrix) {
        Map<String, SimpleMatrix> classMap = new MultiValueMap();
        for(int i = 0; i < matrix.numRows(); ++i) {
            classMap.put(String.valueOf(matrix.get(i, matrix.numCols() - 1)),
                    matrix.extractVector(true, i));
        }

        for(Map.Entry<String, SimpleMatrix> entry : classMap.entrySet()) {

            List<SimpleMatrix> values = (List<SimpleMatrix>) entry.getValue();
            XYSeries xySeries;
            try {

                xySeries = xyDataset.getSeries(entry.getKey());
                final XYSeries finalXySeries = xySeries;
                values.stream().forEach((x) -> finalXySeries.add(x.get(0, 0), x.get(0, 1)));

            } catch (UnknownKeyException e) {

                xySeries = new XYSeries(entry.getKey());
                final XYSeries finalXySeries = xySeries;
                values.stream().forEach((x) -> finalXySeries.add(x.get(0, 0), x.get(0, 1)));

                xyDataset.addSeries(xySeries);
            }
        }
    }
}
