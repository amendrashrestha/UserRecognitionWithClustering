package com.user.cluster.clustering;

import com.user.cluster.IOHandler.IOProperties;
import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.parser.model.Posts;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ITE
 */

public class FirstActivityCluster {
    private int userID;
    private int[] postsTimeVector;
    
    public FirstActivityCluster() {
        this.postsTimeVector = new int[]{0,0,0,0,0,0};
    }
    
    /**
     * @return the userID
     */
    
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the userVector
     */
    public int[] getPostTimeVector() {
        return postsTimeVector;
    }

    /**
     * @param userVector the userVector to set
     */
    public void setPostTimeVector(int[] postsTimeVector) {
        this.postsTimeVector = postsTimeVector;
    }
    
    
    /**
     * This function checks if the divided user (user:A, user:B) has met the criteria and lies on the same 
     * time frame, and returns only those user attributes such as (id) and (time vector which gives the information
     * about the time frame which has met the criteria for that user) as a List<FirstActivityCluster>
     * @param List<User>
     * @return List<FirstActivityCluster>
     */
    public List<FirstActivityCluster> getUserInSameClusterForFirstActivityClusterAsFACObject(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<FirstActivityCluster> returnList = new ArrayList();
        FirstActivityCluster fac;
        int[] matchedTimeFrame;
        while(!userList.isEmpty()){
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getFirstActivityVector(), secondUser.getFirstActivityVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        fac = new FirstActivityCluster();
                        fac.setPostTimeVector(matchedTimeFrame);
                        fac.setUserID(firstUser.getId());
                        returnList.add(fac);
                    }
                    userList.remove(secondUser);
                    break;
                }
            }
            userList.remove(firstUser);
        }
        return returnList;
    }
    
    public void writeFirstActivityCluster(List<FirstActivityCluster> firstActivityCluster) throws IOException{
        IOReadWrite ioRW = new IOReadWrite();
        ioRW.writeFirstActivityClusterData(firstActivityCluster);
    }
            
    public List<FirstActivityCluster> readFirstActivityCluster() throws FileNotFoundException, IOException{
        IOReadWrite ioRW = new IOReadWrite();
        String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.FIRST_ACTIVITY_FOLDER_NAME + "\\" + 
                IOProperties.FIRST_ACTIVITY_FILE_NAME + IOProperties.FIRST_ACTIVITY_FILE_EXTENSION;
        List<FirstActivityCluster> firstActivityCluster = ioRW.readFirstActivityClusterData(fileName);
        
        return firstActivityCluster;
    }
}