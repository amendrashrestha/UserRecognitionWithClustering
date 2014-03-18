package uni.cluster.clustering;

import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;
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
    private int[] SleepingCluster;

    public SleepingCluster() {
        this.SleepingCluster = new int[]{0, 0, 0, 0, 0, 0};
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
     * @return the SleepingCluster
     */
    public int[] getSleepingCluster() {
        return SleepingCluster;
    }

    /**
     * @param SleepingCluster the SleepingCluster to set
     */
    public void setSleepingCluster(int[] SleepingCluster) {
        this.SleepingCluster = SleepingCluster;
    }

    public List<SleepingCluster> getUserInSameClusterForSleepingCluster(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<SleepingCluster> returnList = new ArrayList();
        SleepingCluster sc;
        int[] matchedTimeFrame;
        while (!userList.isEmpty()) {
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getSleepingClusterVector(), secondUser.getSleepingClusterVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        sc = new SleepingCluster();
                        sc.setSleepingCluster(matchedTimeFrame);
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

    public List<SleepingCluster> getIndiviudalClusterUserWithSplit(List<FirstActivityCluster> facList, int timeFrame, List<User> allUserUnDividedUserList) {
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        List<FirstActivityCluster> individualFacUser = new ArrayList();
        for (FirstActivityCluster facObj : facList) {
            if (facObj.getUserCluster()[timeFrame] == 1) {
                individualFacUser.add(facObj);
            }
        }
        User userObj = new User();
        UserDivision userDivisionObj = new UserDivision();
        List<User> unDividedUserList = userObj.getInitialUserForSleepingCluster(individualFacUser, allUserUnDividedUserList);
        //Split user into 2 users
        List dividedUserList = userDivisionObj.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSleepingCluster(dividedUserList);
        scList = getUserInSameClusterForSleepingCluster(dividedUserList);
        return scList;
    }

    public List<SleepingCluster> getIndiviudalClusterUserWOSplit(List<FirstActivityCluster> facList, int timeFrame, List<User> allUserUnDividedUserList) {
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        List<FirstActivityCluster> individualFacUser = new ArrayList();
        for (FirstActivityCluster facObj : facList) {
                if (facObj.getUserCluster()[timeFrame] == 1) {
                    individualFacUser.add(facObj);
            }
        }
        User userObj = new User();
        List<User> unDividedUserList = userObj.getInitialUserForSleepingCluster(individualFacUser, allUserUnDividedUserList);
        List SAUserList = userObj.setCategorizedTimeToUser(unDividedUserList);
        scList = generateUserSleepingClusterWOSplit(SAUserList);
        return scList;
    }

    public List<SleepingCluster> getIndiviudalClusterUser(List<User> allUserUnDividedUserList) {
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        User userObj = new User();
        UserDivision userDivisionObj = new UserDivision();

        List dividedUserList = userDivisionObj.divideAllUser(allUserUnDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSleepingCluster(dividedUserList);
        scList = getUserInSameClusterForSleepingCluster(dividedUserList);
        return scList;
    }

    public void writeSleepingCluster(List<List<SleepingCluster>> allSleepingCluster) throws IOException {
        IOReadWrite ioRW = new IOReadWrite();
        ioRW.CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.SLEEPING_FOLDER_NAME);
        for (int i = 0; i < allSleepingCluster.size(); i++) {
            List<SleepingCluster> sleepingCluster = allSleepingCluster.get(i);
            ioRW.writeSleepingClusterData(sleepingCluster, String.valueOf(i + 1));
        }
    }

    public void writeSingleSleepingCluster(SleepingCluster allSleepingCluster, int FACcluster, int SleepingCluster) throws IOException {
        IOReadWrite ioRW = new IOReadWrite();
        ioRW.CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.SLEEPING_FOLDER_NAME);
        ioRW.writeSingleSleepingClusterData(allSleepingCluster, FACcluster, SleepingCluster);
    }

    public List<SleepingCluster> readSleepingCluster(String givenFileName) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME + "\\"
                + givenFileName + IOProperties.SLEEPING_FILE_EXTENSION;
        List<SleepingCluster> sleepingCluster = ioRW.readSleepingClusterData(fileName);
        return sleepingCluster;
    }

    public List<String> getAllFilesForSleepingCluster() {
        String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME;
        IOReadWrite ioReadWrite = new IOReadWrite();
        return ioReadWrite.getAllFilesInADirectory(fileName);
    }

    public List<SleepingCluster> generateUserSleepingClusterWOSplit(List<User> userList) {
        List<SleepingCluster> returnList = new ArrayList();
        SleepingCluster scu;
        for (User user : userList) {
            scu = new SleepingCluster();
            int[] sleepingCluster = user.getSleepingClusterVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for (int i = 0; i < sleepingCluster.length; i++) {
                if (tempClassifiedTimeVector[i] <= 5) {
                    sleepingCluster[i] = 1;
                }
            }
            scu.setSleepingCluster(sleepingCluster);
            scu.setUserID(user.getId());
            returnList.add(scu);
        }
        return returnList;
    }
}
