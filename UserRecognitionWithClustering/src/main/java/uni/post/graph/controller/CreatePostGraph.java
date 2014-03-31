/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.graph.controller;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.UserDivision;
import uni.cluster.parser.model.Alias;
import uni.cluster.parser.model.Posts;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class CreatePostGraph {

    List<String> firstUserTime;
    List<String> secondUserTime;
    String firstUser, secondUser;

    public JPanel createTimegraphPanel(int clickedUser) {
        try {

            FileOutputStream fos = new FileOutputStream("C:/image.PNG");
            List<String> userPostTime = getPost(clickedUser);
            //dividePost(clickedUser);
//            JFreeChart jfreechart = createTimeChart(createTimeDataset(clickedUser, userPostTime));
            JFreeChart jfreechart = createTimeChart(createTimeDataset(clickedUser, userPostTime));
//            JFreeChart jfreechart = createTimeChart(createTimeDataset(clickedUser));

//            StackedBarRenderer renderer = new StackedBarRenderer(false);
//            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//            renderer.setBaseItemLabelsVisible(true);
//            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
//            jfreechart.getCategoryPlot().setRenderer(renderer);


            ChartUtilities.writeScaledChartAsPNG(fos, jfreechart, 700, 260, 5, 5);
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(700, 350));
            return chartpanel;
        } catch (IOException | SQLException eee) {
            return null;
        }
    }

    private JFreeChart createTimeChart(CategoryDataset xydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart("User Posts", "Hour", "Posts", xydataset,
                PlotOrientation.VERTICAL, true, true, false);
        return jfreechart;
    }

    private CategoryDataset createTimeDataset(int clickedUser) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        HashMap<String, double[]> map = new HashMap<>();
        IOReadWrite io = new IOReadWrite();
        User tempClicketUser = io.getUsersAsObject(clickedUser);
        List mainUserPost = tempClicketUser.getUserPost();
        int postSize = mainUserPost.size();

        double[] firstUserVector = Alias.getTimeVectorArray(firstUserTime);
        map.put(firstUser, firstUserVector);
        double[] secondUserVector = Alias.getTimeVectorArray(secondUserTime);
        map.put(secondUser, secondUserVector);

        Iterator<String> kitr = map.keySet().iterator();
        while (kitr.hasNext()) {
            String k = kitr.next();
            double[] post_count = map.get(k);
            int totalPostCount = post_count.length;

            for (Integer i = 0; i < totalPostCount; ++i) {
                double value = (double) post_count[i] * 0.1 / postSize;
                defaultcategorydataset.addValue(value, k, i);
            }
        }
        return defaultcategorydataset;
    }

    /**
     * create graph for individual user
     * @param clickedUser
     * @param postTime
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private CategoryDataset createTimeDataset(int clickedUser, List<String> postTime) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        HashMap<Integer, double[]> map = new HashMap<>();
        double[] UserVector = Alias.getTimeVectorArray(postTime);
        map.put(clickedUser, UserVector);

        Iterator<Integer> kitr = map.keySet().iterator();
        while (kitr.hasNext()) {
            Integer k = kitr.next();
            double[] post_count = map.get(k);
            for (Integer i = 0; i < post_count.length; ++i) {

                int value;
                value = (int) post_count[i];
                System.out.print(value + "\t");
                defaultcategorydataset.addValue(value, k, i);
            }
        }
        return defaultcategorydataset;
    }

    private void dividePost(int clickedUser) throws FileNotFoundException, IOException {
        IOReadWrite io = new IOReadWrite();
        UserDivision du = new UserDivision();
        User tempClicketUser = io.getUsersAsObject(clickedUser);
        List<User> userList = du.divideUser(tempClicketUser);

        firstUserTime = new ArrayList<>();
        secondUserTime = new ArrayList<>();

        User firstUserObj = userList.get(0);
        User secondUserObj = userList.get(1);

        firstUser = String.valueOf(firstUserObj.getId()) + "_A";
        secondUser = String.valueOf(secondUserObj.getId()) + "_B";
        List<Posts> firstUserPost = firstUserObj.getUserPost();
        List<Posts> secondUserPost = secondUserObj.getUserPost();

        for (int i = 0; i < firstUserPost.size(); i++) {
            Posts firstUserPostTime = firstUserPost.get(i);
            String time = firstUserPostTime.getTime();
            firstUserTime.add(time);
        }
        for (int i = 0; i < secondUserPost.size(); i++) {
            Posts secondUserPostTime = secondUserPost.get(i);
            String time = secondUserPostTime.getTime();
            secondUserTime.add(time);
        }
    }

    public List<String> getPost(int userID) throws FileNotFoundException, IOException {
        IOReadWrite io = new IOReadWrite();
        List<String> userPostTime = new ArrayList();
        User tempClicketUser = io.getUsersAsObject(userID);
        List<Posts> allUserPost = tempClicketUser.getUserPost();

        for (int i = 0; i < allUserPost.size(); i++) {
            Posts userPost = allUserPost.get(i);
            String time = userPost.getTime();
            userPostTime.add(time);
        }
        return userPostTime;
    }

    /**
     * divides the post of user into 6 groups and creates graph of it
     * @param clickedUser
     * @param postTime
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private CategoryDataset createTimeClusterDataset(int clickedUser, List<String> postTime) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        HashMap<Integer, double[]> map = new HashMap<>();
        double[] UserVector = Alias.getTimeVectorArray(postTime);
        double[] tempValue = new double[6];

        int index = 0;
        int j = 0;
        while (index < UserVector.length) {
            double sum = 0.0;
            int k = 0;
            while (k < 4) {
                sum = sum + UserVector[index];
                k++;
                index++;
            }
            tempValue[j] = sum;
            j++;
        }
        map.put(clickedUser, tempValue);

        Iterator<Integer> kitr = map.keySet().iterator();
        while (kitr.hasNext()) {
            Integer k = kitr.next();
            double[] post_count = map.get(k);
            for (Integer i = 0; i < post_count.length; ++i) {
                double value = (double) post_count[i];
                defaultcategorydataset.addValue(value, k, i);
            }
        }
        return defaultcategorydataset;
    }
}
