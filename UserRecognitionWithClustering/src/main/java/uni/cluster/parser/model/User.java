package uni.cluster.parser.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import uni.cluster.clustering.ClusterCommons;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.SleepingCluster;

/**
 * @author ITE
 */
public class User {

    private int id;
    private List<Posts> userPost;
    private String type;
    /*
     * This variable contains the total number of posts, posted in an individual time frame,
     * The information about the avaiable time frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedTimeVector;
    /*
     * This variable contains the boolean 0/1 values in int[] array.
     * If the particular time frame has more posts than 20% of the total post posted by the user the value is updated as 1.
     * The information about the avaiable time frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] firstActivityVector;
    /*
     * This variable contains the boolean 0/1 values in int[] array.
     * If the particular time frame has 0 posts in a specified time frame than the value of specific time frame is updated as 1.
     * The information about the avaiable time frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] sleepingClusterVector;
    /*
     * Don't know yet.
     */
    private int[] secondActivityVector;

    /**
     * @return the userPost
     */
    public User() {
        this.type = UserType.UNDEFINED;
        this.classifiedTimeVector = new int[]{0, 0, 0, 0, 0, 0};
        this.firstActivityVector = new int[]{0, 0, 0, 0, 0, 0};
        this.sleepingClusterVector = new int[]{0, 0, 0, 0, 0, 0};
        this.secondActivityVector = new int[]{0, 0, 0, 0, 0, 0};
    }

    public List<Posts> getUserPost() {
        return userPost;
    }

    /**
     * @param userPost the userPost to set
     */
    public void setUserPost(List<Posts> userPost) {
        this.userPost = userPost;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the classifiedTimeVector
     */
    public int[] getClassifiedTimeVector() {
        return classifiedTimeVector;
    }

    /**
     * @param classifiedTimeVector the classifiedTimeVector to set
     */
    public void setClassifiedTimeVector(int[] classifiedTimeVector) {
        this.classifiedTimeVector = classifiedTimeVector;
    }

    /**
     * @return the firstActivityVector
     */
    public int[] getFirstActivityVector() {
        return firstActivityVector;
    }

    /**
     * @param firstActivityVector the firstActivityVector to set
     */
    public void setFirstActivityVector(int[] firstActivityVector) {
        this.firstActivityVector = firstActivityVector;
    }

    /**
     * @return the sleepingClusterVector
     */
    public int[] getSleepingClusterVector() {
        return sleepingClusterVector;
    }

    /**
     * @param sleepingClusterVector the sleepingClusterVector to set
     */
    public void setSleepingClusterVector(int[] sleepingClusterVector) {
        this.sleepingClusterVector = sleepingClusterVector;
    }

    /**
     * @return the secondActivityVector
     */
    public int[] getSecondActivityVector() {
        return secondActivityVector;
    }

    /**
     * @param secondActivityVector the secondActivityVector to set
     */
    public void setSecondActivityVector(int[] secondActivityVector) {
        this.secondActivityVector = secondActivityVector;
    }

    /**
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     * @param List<User>
     * @return List<User>
     */
    public List<User> setCategorizedTimeToUser(List<User> userList) {
        ClusterCommons cc = new ClusterCommons();
        for (User user : userList) {
            int[] userTimeVector = user.getClassifiedTimeVector();
            for (Posts posts : user.getUserPost()) {
                Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
                int timeCategory = cc.getTimeCategory(ts.getHours());
                if (timeCategory < 6) {
                    userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
                }
            }
            user.setClassifiedTimeVector(userTimeVector);
        }
        return userList;
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
    public List<User> generateUserFirstActivityCluster(List<User> userList) {
        for (User user : userList) {
            List postList = user.getUserPost();
            int requiredPostValue = (int) (0.2 * postList.size());
            int[] firstActivityCluster = user.getFirstActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for (int i = 0; i < firstActivityCluster.length; i++) {
                if (tempClassifiedTimeVector[i] > requiredPostValue) {
                    firstActivityCluster[i] = 1;
                }
            }
            user.setFirstActivityVector(firstActivityCluster);
        }
        return userList;
    }

    /*
     * Generates first activity user cluster by taking the maximum number
     * of posts posted by user
     */
    public List<User> generateUserMostActiveCluster(List<User> userList) {
        for (User user : userList) {
            int[] firstActivityCluster = user.getFirstActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            int max = 0;
            int maxIndex = 0;
            for (int i = 0; i < tempClassifiedTimeVector.length; i++) {
                if (tempClassifiedTimeVector[i] > max) {
                    max = tempClassifiedTimeVector[i];
                    maxIndex = i;
                }
            }
            firstActivityCluster[maxIndex] = 1;
            user.setFirstActivityVector(firstActivityCluster);
        }
        return userList;
    }

    public HashMap<Integer, Set> generateUserCluster(List<User> userList) {
        HashMap<Integer, Set> userCluster;
        userCluster = new HashMap<>();
        Set<Integer> firstClusterList = new TreeSet<>();
        Set<Integer> secondClusterList = new TreeSet<>();
        Set<Integer> thirdClusterList = new TreeSet<>();
        Set<Integer> fourthClusterList = new TreeSet<>();
        Set<Integer> fifthClusterList = new TreeSet<>();
        Set<Integer> sixthClusterList = new TreeSet<>();
        for (User user : userList) {
            // int[] firstActivityCluster = user.getFirstActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            int max = 0;
            int maxIndex = 0;
            for (int i = 0; i < tempClassifiedTimeVector.length; i++) {
                if (tempClassifiedTimeVector[i] > max) {
                    max = tempClassifiedTimeVector[i];
                    maxIndex = i;
                }
            }
            if (maxIndex == 0) {
                Integer tempuserID = user.getId();
                firstClusterList.add(tempuserID);
                userCluster.put(1, firstClusterList);
            } else if (maxIndex == 1) {
                int tempuserID = user.getId();
                secondClusterList.add(tempuserID);
                userCluster.put(2, secondClusterList);
            } else if (maxIndex == 2) {
                int tempuserID = user.getId();
                thirdClusterList.add(tempuserID);
                userCluster.put(3, thirdClusterList);
            } else if (maxIndex == 3) {
                int tempuserID = user.getId();
                fourthClusterList.add(tempuserID);
                userCluster.put(4, fourthClusterList);
            } else if (maxIndex == 4) {
                int tempuserID = user.getId();
                fifthClusterList.add(tempuserID);
                userCluster.put(5, fifthClusterList);
            } else if (maxIndex == 5) {
                int tempuserID = user.getId();
                sixthClusterList.add(tempuserID);
                userCluster.put(6, sixthClusterList);
            }
            //firstActivityCluster[maxIndex] = 1;
        }
        return userCluster;
    }

    /**
     * if user has posted less than or equal to 8 percent of total message then
     * it will be consider as his sleeping time
     *
     * @param userList
     * @return
     */
    public List<User> generateUserSleepingCluster(List<User> userList) {
        for (User user : userList) {
            int[] sleepingCluster = user.getSleepingClusterVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            List postList = user.getUserPost();
            int requiredSleepingPostValue = (int) (0.08 * postList.size());
//            int requiredSleepingPostValue = 5;
            for (int i = 0; i < sleepingCluster.length; i++) {
                if (tempClassifiedTimeVector[i] <= requiredSleepingPostValue) {
                    sleepingCluster[i] = 1;
                }
            }
            user.setSleepingClusterVector(sleepingCluster);
        }
        return userList;
    }

    public List<User> generateUserSecondActivityCluster(List<User> userList) {
        for (User user : userList) {
            List postList = user.getUserPost();
            int requiredPostValue = (int) (0.25 * postList.size());
            int[] secondActivityCluster = user.getSecondActivityVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for (int i = 0; i < secondActivityCluster.length; i++) {
                if (tempClassifiedTimeVector[i] > requiredPostValue) {
                    secondActivityCluster[i] = 1;
                }
            }
            user.setSecondActivityVector(secondActivityCluster);
        }
        return userList;
    }

    public List<User> getInitialUserForSleepingCluster(List<FirstActivityCluster> facList, List<User> allUserList) {
        List<User> returnUserList = new ArrayList<>();
        for (FirstActivityCluster fac : facList) {
            for (User user : allUserList) {
                if (fac.getUserID() == user.getId()) {
                    returnUserList.add(user);
                    break;
                }
            }
        }
        return returnUserList;
    }

    /**
     * This function removes the post of the user which are included in the
     * First Activity Cluster, and gives only those user which which lies on the
     * sleeping cluster
     *
     * @param facList
     * @param scList
     * @param allUserList
     * @return
     */
    public List<User> getInitialUserForSecondActivityCluster(List<FirstActivityCluster> facList,
            List<SleepingCluster> scList, List<User> allUserList) {
        List<User> returnUserList = new ArrayList<>();
        for (SleepingCluster sc : scList) {
            for (User user : allUserList) {
                if (sc.getUserID() == user.getId()) {
                    returnUserList.add(user);
                    break;
                }
            }
        }

        ClusterCommons cc = new ClusterCommons();
        for (User user : returnUserList) {
            List<Posts> postList = user.getUserPost();
            System.out.println("Before Deleting " + user.getId() + "-->" + user.getUserPost().size());
            for (FirstActivityCluster facObj : facList) {
                if (facObj.getUserID() == user.getId()) {
                    int[] facTimeVector = facObj.getUserCluster();
                    List<Posts> toSetList = new ArrayList<>();
                    for (Posts post : postList) {
                        Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(post.getTime()));
                        int timeCategory = cc.getTimeCategory(ts.getHours());
                        if (facTimeVector[timeCategory] == 0) {
                            toSetList.add(post);
                        }
                    }
                    user.setUserPost(toSetList);
                    System.out.println("After Deleting " + user.getId() + "-->" + user.getUserPost().size());
                    System.out.println("---------------");
                    break;
                }
            }
        }
        return returnUserList;
    }
}
