/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.graph.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;

/**
 *
 * @author ITE
 */
public class CreateClusterGraph {

    Set<String> secondActivityUsers = new HashSet<>();

    public JPanel createTimegraphPanel() {
        try {
            String folderPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.CLUSTER_FOLDER_NAME;
            HashMap<Integer, Integer> clusterMap = new HashMap<>();
            clusterMap = createClusterMap(folderPath);
            printClusterMap(clusterMap);
            JFreeChart jfreechart = createTimeChart(createClusterDataset(clusterMap));
            StackedBarRenderer renderer = new StackedBarRenderer(false);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
            jfreechart.getCategoryPlot().setRenderer(renderer);

            System.out.println("Second Activity Users: " + secondActivityUsers.size());
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(600, 1200));
            return chartpanel;
        } catch (IOException | SQLException eee) {
            return null;
        }
    }

    private JFreeChart createTimeChart(CategoryDataset xydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart(" User Pattern in Graph", "Cluster", "Number of Users", xydataset,
                PlotOrientation.VERTICAL, true, true, false);
        return jfreechart;
    }

    private CategoryDataset createClusterDataset(HashMap<Integer, Integer> clusterMap) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        Iterator<Integer> kitr = clusterMap.keySet().iterator();

        while (kitr.hasNext()) {
            Integer k = kitr.next();
            double value = (double) clusterMap.get(k);
            defaultcategorydataset.addValue(value, k, k);
        }
        return defaultcategorydataset;
    }

    private HashMap<Integer, Integer> createClusterMap(String folderPath) throws FileNotFoundException, IOException {
        HashMap<Integer, Integer> dataset = new HashMap<>();
        IOReadWrite ioRW = new IOReadWrite();
        List allsacFiles = new ArrayList();
        allsacFiles = ioRW.getAllFilesInADirectory(folderPath);

        for (int j = 0; j < allsacFiles.size(); j++) {
            List<String> clusterUserID = new ArrayList<>();
            Integer fileName = Integer.valueOf(allsacFiles.get(j).toString());
            clusterUserID = ioRW.readClusterData(folderPath + "\\" + fileName + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION);
            secondActivityUsers.addAll(clusterUserID);

            int noOfUsers = clusterUserID.size();
            dataset.put(fileName, noOfUsers);
        }
        return dataset;
    }

    private int returnUsersInFile(File file) throws FileNotFoundException, IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        int line = lnr.getLineNumber();

        return line;
    }

    private void printClusterMap(HashMap<Integer, Integer> clusterMap) {
        Iterator<Integer> cluster = clusterMap.keySet().iterator();
        int total = 0;
        while (cluster.hasNext()) {
            Integer k = cluster.next();
            Integer value = clusterMap.get(k);
            total = total + value;
            System.out.println(k + "\t" + value);
        }
        System.out.println("Total: " + total);
    }
}
