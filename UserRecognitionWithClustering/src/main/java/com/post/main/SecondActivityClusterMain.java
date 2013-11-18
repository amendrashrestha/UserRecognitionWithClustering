/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.main;

import com.post.parser.IOHandler.IOProperties;
import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.SecondActivityCluster;
import com.post.parser.clustering.SleepingCluster;
import com.post.parser.clustering.UserDivision;
import com.post.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Batman
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
          allscList.add(scObj.getIndiviudalClusterUser(facList, i, allUserUnDividedUserList));
        }
       
        List<List<SecondActivityCluster>> scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        for (int j=0; j<allscList.size(); j++){
            scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
            scList = allscList.get(j);
            for(int i=0; i<6; i++){
                scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i, allUserUnDividedUserList));
            }
            sacObj.writeSecondActivityCluster(scClusterSACList, j+1);
        }
        
        String sacFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.SECOND_ACTIVITY_FOLDER_NAME;
        //int sacUserSize = ioReadWrite.returnSecondActivityClusterUserSize(sacFolderPath);
        //System.out.println("Final User Size: " + sacUserSize);
        
        ioReadWrite.readSecondActivityClusterData(sacFolderPath);
         
    /**
     * Need to do this myself
     */    
        
        
        // First get up to the path Second Activity
        // IOProperties.All_ACTIVITY_BASE_PATH + SECOND_ACTIVITY_FOLDER_NAME;
        // Read all the name of the folder from the above path in a list
        // List<String> sacFolders = new ArrayList<String>();
        // Get inside each of the individual folder 
        // for(int i=0; i<sacFolders.size(); i++){
        // Get a list of all the fileName in that folder
        // List<String> fileName = new ArrayList<String>();
        // for(int j=0; j<fileName.size(); j++){
        // List<SecondActivityCluster> sacObjList =new ArrayList<SecondActivityCluster>();
        // sacObjList = SecondActivityCluster.readSecondActivityClusterData(send the file name from here);
        // (allUserUnDividedUserList == sacObjList) compare the id
        // List<User> userObj
        // if the id mathces get that user and all its content
        
        // for(user size){
     //   if(user.fourthIndex == 1){
      //      styloUsers.add(user);
     //}
      //divive user
     // Inorder to divide first user into two parts and divide rest of the user into half - user UserDivision.divideFirstUser(pass user to divide);
        //styloCalculate (styloUsers);
        
        // Start stylo mertics from here.
        
        
     }
}
