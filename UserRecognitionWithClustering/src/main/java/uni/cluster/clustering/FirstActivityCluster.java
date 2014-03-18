package uni.cluster.clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 * @author ITE
 */
public class FirstActivityCluster {

    private int userID;
    private int[] userCluster;

    public FirstActivityCluster() {
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
     * @return the userVector
     */
    public int[] getUserCluster() {
        return userCluster;
    }

    /**
     * @param userVector the userVector to set
     */
    public void setUserCluster(int[] userCluster) {
        this.userCluster = userCluster;
    }

    /**
     * This function checks if the divided user (user:A, user:B) has met the
     * criteria and lies on the same time frame, and returns only those user
     * attributes such as (id) and (time vector which gives the information
     * about the time frame which has met the criteria for that user) as a
     * List<FirstActivityCluster>
     *
     * @param List<User>
     * @return List<FirstActivityCluster>
     */
    public List<FirstActivityCluster> getUserInSameClusterForFirstActivityClusterAsFACObject(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        List<FirstActivityCluster> returnList = new ArrayList();
        FirstActivityCluster fac;
        int[] matchedTimeFrame;
        while (!userList.isEmpty()) {
            User firstUser = (User) userList.get(0);
            for (int i = 0; i < userList.size(); i++) {
                User secondUser = (User) userList.get(i);
                if ((secondUser.getId() == firstUser.getId()) && (!secondUser.getType().equals(firstUser.getType()))) {
                    matchedTimeFrame = cc.getMatchedTimeFrame(firstUser.getFirstActivityVector(), secondUser.getFirstActivityVector());
                    if (cc.checkIfUserHasCommonTimeFrame(matchedTimeFrame)) {
                        fac = new FirstActivityCluster();
                        fac.setUserCluster(matchedTimeFrame);
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

    /**
     * @Desc This function populates the "firstActivityVector" variable of the
     * com.post.parser.model.User class. It checks if the users post in each
     * time frame is greater than the minimum criteria. If it meets the criteria
     * than the value in int[] of particular time frame is set to 1 or else it
     * is set 0. The criteria is 20% of his total messages.
     * @param List<User>
     * @return List<User>
     */
    public List<FirstActivityCluster> generateUserFirstActivityCluster(List<User> userList) {
        List<FirstActivityCluster> returnList = new ArrayList();
        FirstActivityCluster fac;
        for (User user : userList) {
            List postList = user.getUserPost();
            fac = new FirstActivityCluster();
            int requiredPostValue = (int) (0.2 * postList.size());
            int[] firstActivityCluster = user.getFirstActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for (int i = 0; i < firstActivityCluster.length; i++) {
                if (tempClassifiedTimeVector[i] > requiredPostValue) {
                    firstActivityCluster[i] = 1;
                }
            }
            fac.setUserCluster(firstActivityCluster);
            fac.setUserID(user.getId());
            returnList.add(fac);
        }
        return returnList;
    }

    public void writeFirstActivityCluster(List<FirstActivityCluster> firstActivityCluster) throws IOException {
        IOReadWrite ioRW = new IOReadWrite();
        ioRW.writeFirstActivityClusterData(firstActivityCluster);
    }

    public List<FirstActivityCluster> readFirstActivityCluster() throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        String fileName = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.FIRST_ACTIVITY_FOLDER_NAME + "\\"
                + IOProperties.FIRST_ACTIVITY_FILE_NAME + IOProperties.FIRST_ACTIVITY_FILE_EXTENSION;
        List<FirstActivityCluster> firstActivityCluster = ioRW.readFirstActivityClusterData(fileName);

        return firstActivityCluster;
    }

    /**
     * @Desc This function populates the "firstActivityVector" variable of the
     * com.post.parser.model.User class. It checks if the users post in each
     * time frame is greater than the minimum criteria. If it meets the criteria
     * than the value in int[] of particular time frame is set to 1 or else it
     * is set 0. The criteria is 20% of his total messages.
     * @param List<User>
     * @return List<User>
     */
    public List<FirstActivityCluster> generateUserFirstActivityClusterMaxPost(List<User> userList) {
        List<FirstActivityCluster> returnList = new ArrayList();
        FirstActivityCluster fac;
        for (User user : userList) {
            //List postList = user.getUserPost();
            fac = new FirstActivityCluster();
            //int requiredPostValue = (int) (0.2 * postList.size());
            int[] firstActivityCluster = user.getFirstActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();

            // for (int i = 0; i < firstActivityCluster.length; i++) {
            int maxValue = tempClassifiedTimeVector[0];
            int tempClusterNo = 0;
            for (int j = 0; j < tempClassifiedTimeVector.length; j++) {

                if (tempClassifiedTimeVector[j] > maxValue) {
                    maxValue = tempClassifiedTimeVector[j];
                    tempClusterNo = j;
                }
//                    if (tempClassifiedTimeVector[i] > requiredPostValue) {
//                        firstActivityCluster[i] = 1;
//                    }

            }
            firstActivityCluster[tempClusterNo] = 1;
            fac.setUserCluster(firstActivityCluster);
            fac.setUserID(user.getId());
            returnList.add(fac);
            //  }
        }
        return returnList;
    }
}