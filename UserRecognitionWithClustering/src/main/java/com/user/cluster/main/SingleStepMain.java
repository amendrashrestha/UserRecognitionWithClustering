/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.user.cluster.IOHandler.IOProperties;
import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.Cluster;
import com.user.cluster.clustering.FirstActivityCluster;
import com.user.cluster.clustering.SleepingCluster;
import com.user.cluster.clustering.UserDivision;
import com.user.cluster.parser.model.Posts;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ITE
 */
public class SingleStepMain {
    /*
     class Document{
     public Date;
     public text;
     } */

//     public void ClusterDocument(List<Document> documents) throws FileNotFoundException, IOException {
//         
//     }
    
    public void ClusterDocument(List<String> document, String seperator) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        Cluster cluster = new Cluster();
        List<User> userList = ioRW.getAllDocumentObj(document, seperator);
        HashMap<Integer, int[]> userInfo = cluster.getAllClusterizedUser(userList);

        int i = 1;
        for (Integer user : userInfo.keySet()) {
            Integer ID = user;
            int[] clusterInfo = userInfo.get(ID);
            List tempCluster = new ArrayList();
            for (int singleCluster : clusterInfo) {
                tempCluster.add(singleCluster);
            }
            System.out.println(i + ") " + ID + "-->" + tempCluster);
            i++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        SingleStepMain init = new SingleStepMain();
        List<String> post = new ArrayList();
        String seperator = "testSeperator";
        post.add("18:41:00 Yes its test testSeperator 20:41:00 Yes its again easy testSeperator 08:41:00 Yes its test testSeperator");
        post.add("18:41:00 Yes its test testSeperator");
        post.add("18:41:00 Yes its test testSeperator 20:41:00 Yes its again test "
                + "testSeperator 08:41:00 Yes its test testSeperator 08:41:00 Yes its test testSeperator");

        init.ClusterDocument(post, seperator);
        /*Cluster cluster = new Cluster();
         IOReadWrite ioRW = new IOReadWrite();
         List<User> unDividedUserList = ioRW.getAllUsersAsObject(IOProperties.INDIVIDUAL_USER_FILE_PATH);
         HashMap<Integer, int[]> userInfo = cluster.getAllClusterizedUser(unDividedUserList);

         int i = 1;
         for (Integer user : userInfo.keySet()) {
         Integer ID = user;
         int[] clusterInfo = userInfo.get(ID);
         List tempCluster = new ArrayList();
         for (int singleCluster : clusterInfo) {
         tempCluster.add(singleCluster);
         }
            
         System.out.println(i + ") " + ID + "-->" + tempCluster);
         i++;
         }

         /*List<FirstActivityCluster> ab = new ArrayList();
         ab = cluster.getFirstActivityCluster(unDividedUserList);     
         List sdfd = new SleepingCluster().getAllFilesForSleepingCluster();
         */
    }
}
