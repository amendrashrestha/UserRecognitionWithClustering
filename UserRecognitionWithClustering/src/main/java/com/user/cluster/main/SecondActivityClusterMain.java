/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.user.cluster.IOHandler.IOProperties;
import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.FirstActivityCluster;
import com.user.cluster.clustering.SecondActivityCluster;
import com.user.cluster.clustering.SleepingCluster;
import com.user.cluster.clustering.UserDivision;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */

public class SecondActivityClusterMain {
     public static void main(String[] args) throws FileNotFoundException, IOException{
        IOReadWrite ioReadWrite = new IOReadWrite();
        FirstActivityCluster facObj = new FirstActivityCluster();
        SleepingCluster scObj = new SleepingCluster();
        SecondActivityCluster sacObj = new SecondActivityCluster();
        
        List<User> allUserUnDividedUserList = ioReadWrite.getAllUsersAsObject();
        List<FirstActivityCluster> facList = facObj.readFirstActivityCluster();
        
       // This gives us the sleeping cluster data
        List<List<SleepingCluster>> allscList = new ArrayList<List<SleepingCluster>>();
        //int notInFirstclusterUserSize = 0;
       
        for (int i = 0; i < 6; i++) {
//          allscList.add(scObj.getIndiviudalClusterUser(facList, i));
          allscList.add(scObj.getIndiviudalClusterUser(facList, i, allUserUnDividedUserList));
        }
       
        List<List<SecondActivityCluster>> scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        for (int j=0; j<allscList.size(); j++){
            scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
            scList = allscList.get(j);
            for(int i=0; i<6; i++){
                scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i, allUserUnDividedUserList));
//                scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i));
            }
            sacObj.writeSecondActivityCluster(scClusterSACList, j+1);
        }
        
        String sacFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.SECOND_ACTIVITY_FOLDER_NAME;
        //int sacUserSize = ioReadWrite.returnSecondActivityClusterUserSize(sacFolderPath);
        //System.out.println("Final User Size: " + sacUserSize);
        
        ioReadWrite.readSecondActivityClusterData(sacFolderPath);      
     }
}
