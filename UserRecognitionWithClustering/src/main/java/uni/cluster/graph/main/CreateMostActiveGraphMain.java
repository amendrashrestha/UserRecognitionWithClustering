/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.graph.main;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.Cluster;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.parser.model.User;
import uni.post.graph.controller.CreateUserMostActiveProfileGraph;

/**
 *
 * @author ITE
 */
public class CreateMostActiveGraphMain {

    public void init() throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        Cluster cluster = new Cluster();
        HashMap<Integer, Set> clusterMap = new HashMap<>();

        List<User> userList = ioReadWrite.getAllUsersAsObject();

        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(userList, 1000);
       
        List<FirstActivityCluster> facList = cluster.getFirstActivityCluster(tempUsers);
        
        tempUsers = user.setCategorizedTimeToUser(tempUsers);
        clusterMap = user.generateUserCluster(tempUsers);
        createFrame(clusterMap);
    }

    public void createFrame(HashMap clusterMap) {
        JFrame frame = new JFrame("Cluster Graph");
        final JPanel gui = new JPanel(new BorderLayout());
        CreateUserMostActiveProfileGraph init = new CreateUserMostActiveProfileGraph();
        JPanel graphPanel = new JPanel();
        graphPanel = init.createTimegraphPanel(clusterMap);
        gui.add(graphPanel);
        frame.setContentPane(gui);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        CreateMostActiveGraphMain main = new CreateMostActiveGraphMain();
        main.init();
    }
}
