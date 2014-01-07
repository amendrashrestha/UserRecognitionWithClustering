package com.user.cluster.clustering;

import com.user.cluster.IOHandler.IOProperties;
import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */
public class SleepingCluster {
   
    private int userID;
    private int [] UserCluster;

    public SleepingCluster(){
        this.UserCluster = new int[]{0,0,0,0,0,0};
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
     * @return the postsTimeVector
     */
    public int[] getUserCluster() {
        return UserCluster;
    }

    /**
     * @param postsTimeVector the postsTimeVector to set
     */
    public void setUserCluster(int[] UserCluster) {
        this.UserCluster = UserCluster;
    }
    
    public List<SleepingCluster> getUserInSameClusterForSleepingCluster(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<SleepingCluster> returnList = new ArrayList();
        SleepingCluster sc;
        int[] matchedTimeFrame;
        while(!userList.isEmpty()){
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getSleepingClusterVector(), secondUser.getSleepingClusterVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        sc = new SleepingCluster();
                        sc.setUserCluster(matchedTimeFrame);
                        sc.setUserID(firstUser.getId());
                        returnList.add(sc);
                    }
                    userList.remove(secondUser);
                    break;
                }
            }
            userList.remove(firstUser);
        }
        return returnList;
        
    }
 
    public List<SleepingCluster> getIndiviudalClusterUser(List<FirstActivityCluster> facList, int timeFrame, List<User> allUserUnDividedUserList){
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        List<FirstActivityCluster> individualFacUser = new ArrayList();
        for(FirstActivityCluster facObj: facList){
            if (facObj.getUserCluster()[timeFrame] == 1) {
                individualFacUser.add(facObj);
            }
        }
        User userObj = new User();
        UserDivision userDivisionObj = new UserDivision();
        List<User> unDividedUserList = userObj.getInitialUserForSleepingCluster(individualFacUser, allUserUnDividedUserList);
        List dividedUserList = userDivisionObj.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSleepingCluster(dividedUserList);
        scList = getUserInSameClusterForSleepingCluster(dividedUserList);
        return scList;
    }

    public void writeSleepingCluster(List<List<SleepingCluster>> allSleepingCluster) throws IOException{
        IOReadWrite ioRW = new IOReadWrite();
        ioRW.CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.SLEEPING_FOLDER_NAME);
        for(int i=0; i<allSleepingCluster.size(); i++){
            List<SleepingCluster> sleepingCluster = allSleepingCluster.get(i);
            ioRW.writeSleepingClusterData(sleepingCluster, String.valueOf(i+1));
        }
    }
            
    public List<SleepingCluster> readSleepingCluster(String givenFileName) throws FileNotFoundException, IOException{
        IOReadWrite ioRW = new IOReadWrite();
        String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME + "\\" + 
                givenFileName + IOProperties.SLEEPING_FILE_EXTENSION;
        List<SleepingCluster> sleepingCluster = ioRW.readSleepingClusterData(fileName);
        return sleepingCluster;
    }
    
    public List<String> getAllFilesForSleepingCluster(){
         String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME ;
         IOReadWrite ioReadWrite = new IOReadWrite();
         return ioReadWrite.getAllFilesInADirectory(fileName);
    }
}
