package uni.cluster.clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class SecondActivityCluster {

    private int userID;
    private int[] userCluster;

    public SecondActivityCluster() {
        this.userCluster = new int[]{0, 0, 0, 0, 0, 0};
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
     * @return the userCluster
     */
    public int[] getUserCluster() {
        return userCluster;
    }

    /**
     * @param userCluster the userCluster to set
     */
    public void setUserCluster(int[] userCluster) {
        this.userCluster = userCluster;
    }

    /**
     * This function checks if the divided user (user:A, user:B) has met the
     * criteria and lies on the same time frame, and returns only those user
     * attributes such as (id) and (time vector which gives the information
     * about the time frame which has met the criteria for that user) as a
     * List<SecondActivityCluster>
     *
     * @param userList
     * @return List<SecondActivityCluster>
     */
    public List<SecondActivityCluster> getUserInSameClusterForSecondActivityCluster(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<SecondActivityCluster> returnList = new ArrayList();
        SecondActivityCluster sacObj;
        int[] matchedTimeFrame;
        while (!userList.isEmpty()) {
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getSecondActivityVector(), secondUser.getSecondActivityVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        sacObj = new SecondActivityCluster();
                        sacObj.setUserCluster(matchedTimeFrame);
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
  
    public List<SecondActivityCluster> generateSecondActivityCluster(List<User> userList) {
        List<SecondActivityCluster> sacList = new ArrayList<>();
        SecondActivityCluster sac;
        for (User user : userList) {
            sac = new SecondActivityCluster();
            List postList = user.getUserPost();
            int requiredPostValue = (int) (0.20 * postList.size());
            int[] secondActivityCluster = user.getSecondActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for (int i = 0; i < secondActivityCluster.length; i++) {
                if (tempClassifiedTimeVector[i] > requiredPostValue) {
                    secondActivityCluster[i] = 1;
                }
            }
            sac.setUserCluster(secondActivityCluster);
            sac.setUserID(user.getId());
            sacList.add(sac);
        }
        return sacList;
    }

    public List<SecondActivityCluster> getIndiviudalSACUserWOSPlit(List<FirstActivityCluster> facList, List<SleepingCluster> scList, int scFrame, List<User> allUserUnDividedUserList) {
        List<SecondActivityCluster> SACUserList;
        User userObj = new User();
        List<User> unDividedUserList = userObj.getInitialUserForSecondActivityCluster(facList, scList, allUserUnDividedUserList);

        unDividedUserList = userObj.setCategorizedTimeToUser(unDividedUserList);
        SACUserList = generateSecondActivityCluster(unDividedUserList);
        return SACUserList;
    }

    public List<SecondActivityCluster> getIndiviudalSACUserWithSplit(List<FirstActivityCluster> facList, List<SleepingCluster> scList, int scFrame, List<User> allUserUnDividedUserList) {
        List<SecondActivityCluster> SACUserList;
        User userObj = new User();
        UserDivision userDivisionObj = new UserDivision();
        SecondActivityCluster sacObj = new SecondActivityCluster();
        
        List<User> unDividedUserList = userObj.getInitialUserForSecondActivityCluster(facList, scList, allUserUnDividedUserList);
        List dividedUserList = userDivisionObj.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSecondActivityCluster(dividedUserList);
        SACUserList = sacObj.getUserInSameClusterForSecondActivityCluster(dividedUserList);

        return SACUserList;
    }

    public void writeSecondActivityCluster(SecondActivityCluster sacList, String FacSc, int sacFrame) throws IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        ioReadWrite.writeSecondActivityClusterData(sacList, FacSc, sacFrame);
    }
}