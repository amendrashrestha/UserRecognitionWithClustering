/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.FirstActivityCluster;
import com.user.cluster.clustering.SleepingCluster;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ITE
 */
public class SleepingClusterMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
//        User userObj = new User();
//        UserDivision userDivisionObj = new UserDivision();
        FirstActivityCluster facObj = new FirstActivityCluster();
        SleepingCluster scObj = new SleepingCluster();
//        List<User> allUserUnDividedUserList = ioReadWrite.getAllUsersAsObject();
        List<User> allUserUnDividedUserList = ioReadWrite.getAllUsersAsObject();
        List<FirstActivityCluster> facList = facObj.readFirstActivityCluster();
//        List<FirstActivityCluster> tempfacList = new ArrayList<FirstActivityCluster>();
        List<List<SleepingCluster>> allscList = new ArrayList<List<SleepingCluster>>();
        //int notInFirstclusterUserSize = 0;
        for (int i = 0; i < 6; i++) {
          allscList.add(scObj.getIndiviudalClusterUser(facList, i, allUserUnDividedUserList));
        }
        //System.out.println("Number of users not in 1st Sleeping Cluster: " + notInFirstclusterUserSize);
        //System.out.println("The user for 1st Sleeping Cluster are: " + tempfacList.size());
        
        // This function writes the data to the file
        scObj.writeSleepingCluster(allscList);
        System.out.println(String.valueOf(allscList.size()));
        Set uniqueSleepingUsersList = new HashSet();
        
        for (int i = 0; i < allscList.size(); i++) {
            System.out.println("************************************************");
            List<SleepingCluster> scList = allscList.get(i);
            System.out.println("--------------");
            System.out.println("The number of the user is  " + i + "  cluster is:  " + scList.size());            
            
            for(int j=0; j<scList.size(); j++){
            SleepingCluster scObj1 = (SleepingCluster) scList.get(j);
            System.out.println("The id of the user is:  " + scObj1.getUserID());
            uniqueSleepingUsersList.add(scObj1.getUserID());
            int[] timeVec = scObj1.getPostTimeVector();
            System.out.println("The time vector is:  " + String.valueOf(timeVec[0]) + " " + String.valueOf(timeVec[1]) + " " + String.valueOf(timeVec[2]) + " "
                    + String.valueOf(timeVec[3]) + " " + String.valueOf(timeVec[4]) + " " + String.valueOf(timeVec[5]));
            }
           System.out.println("--------------");        
        }
        System.out.println("The total number of distinct sleeping users is: " + uniqueSleepingUsersList.size());
    }
}
