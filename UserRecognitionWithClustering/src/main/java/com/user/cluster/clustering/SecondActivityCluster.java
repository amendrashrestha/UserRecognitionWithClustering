package com.user.cluster.clustering;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.parser.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */

public class SecondActivityCluster {
      
    private int userID;
    private int[] postTimeVector;

    public SecondActivityCluster(){
        this.postTimeVector = new int[]{0,0,0,0,0,0};
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
     * @return the postTimeVector
     */
    public int[] getPostTimeVector() {
        return postTimeVector;
    }

    /**
     * @param postTimeVector the postTimeVector to set
     */
    public void setPostTimeVector(int[] postTimeVector) {
        this.postTimeVector = postTimeVector;
    }
   
    /**
     * This function checks if the divided user (user:A, user:B) has met the criteria and lies on the same 
     * time frame, and returns only those user attributes such as (id) and (time vector which gives the information
     * about the time frame which has met the criteria for that user) as a List<SecondActivityCluster>
     * @param List<User>
     * @return List<SecondActivityCluster>
     */
    public List<SecondActivityCluster> getUserInSameClusterForSecondActivityCluster(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<SecondActivityCluster> returnList = new ArrayList();
        SecondActivityCluster sacObj;
        int[] matchedTimeFrame;
        while(!userList.isEmpty()){
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getSecondActivityVector(), secondUser.getSecondActivityVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        sacObj = new SecondActivityCluster();
                        sacObj.setPostTimeVector(matchedTimeFrame);
                        sacObj.setUserID(firstUser.getId());
                        returnList.add(sacObj);
                    }
                    userList.remove(secondUser);
                    break;
                }
            }
            userList.remove(firstUser);
        }
        return returnList;
    }
    
   public List<SecondActivityCluster> getIndiviudalClusterUser(List<FirstActivityCluster> facList, List<SleepingCluster> scList, int sctimeFrame, int scFrame, List<User> allUserUnDividedUserList){
        List<SecondActivityCluster> returnList = new ArrayList<SecondActivityCluster>();
        List<SleepingCluster> individualscUser = new ArrayList<SleepingCluster>();
        for(SleepingCluster scObj: scList){
            if (scObj.getPostTimeVector()[scFrame] == 1) {
                individualscUser.add(scObj);
            }
        }
        User userObj = new User();
        UserDivision userDivision = new UserDivision();
        List<User> unDividedUserList = userObj.getInitialUserForSecondActivityCluster(facList, individualscUser, allUserUnDividedUserList);
        List dividedUserList = userDivision.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSecondActivityCluster(dividedUserList);
        returnList = getUserInSameClusterForSecondActivityCluster(dividedUserList);
        return returnList;
    }
   
   public void writeSecondActivityCluster(List<List<SecondActivityCluster>> sacList, int sacFrame) throws IOException{
       IOReadWrite ioReadWrite = new IOReadWrite();
       ioReadWrite.writeSecondActivityClusterData(sacList, sacFrame);
   }
    
}