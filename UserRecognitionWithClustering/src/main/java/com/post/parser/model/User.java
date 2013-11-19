package com.post.parser.model;

import com.post.parser.clustering.ClusterCommons;
import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.SleepingCluster;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Batman
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
    // Do you need this variable?? what it has??
    // I calucates the total number of posts in an indiviudal time frame
    // Like got it...rakhi rakha na...b
    // but you will have to calcualte this value inorder to populate this variable
    // You cannot get this from a file
    //pardaina na gara teso vaye..wait i will use the computer now...i will show you something...
    // we have already extract the post and post time of each individual rser....
    // aba k garne ho
    // we can use the same thing
    // tyo kura chai tara Stylometric clas bhitra calculate garne...
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
    
    public User(){
        this.type = UserType.UNDEFINED;
        this.classifiedTimeVector = new int[]{0,0,0,0,0,0};
        this.firstActivityVector = new int[]{0,0,0,0,0,0};
        this.sleepingClusterVector = new int[]{0,0,0,0,0,0};
        this.secondActivityVector = new int[]{0,0,0,0,0,0};
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
     * com.post.parser.model.User class. It gives the total number of posts in the specified time
     * range which has been described in getTimeCategory(int hours) in this
     * class.
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
     * com.post.parser.model.User class. It checks if the users post in each time frame is 
     * greater than the minimum criteria. If it meets the criteria than the value in int[]
     * of particular time frame is set to 1 or else it is set 0.
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
    
     public List<User> generateUserSleepingCluster(List<User> userList){
        for(User user: userList){
            int[] sleepingCluster = user.getSleepingClusterVector();
            int[] tempClassifiedTimeVector = user.getClassifiedTimeVector();
            for(int i=0; i< sleepingCluster.length; i++){
                if(tempClassifiedTimeVector[i] == 0){
                    sleepingCluster[i] = 1;
                }
            }
            user.setSleepingClusterVector(sleepingCluster);
        }
        return userList;
    }
     
    public List<User> generateUserSecondActivityCluster(List<User> userList){
        for(User user: userList){
            List postList = user.getUserPost();
            int requiredPostValue = (int) (0.2 * postList.size());
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
    
    public List<User> getInitialUserForSleepingCluster(List<FirstActivityCluster> facList, List<User> allUserList){
        List<User> returnUserList = new ArrayList<User>();
        for(FirstActivityCluster fac: facList){
            for(User user: allUserList){
                if(fac.getUserID() == user.getId()){
                    returnUserList.add(user);
                    break;
                }
            }
        }
        return returnUserList;
    }

    // This function removes the post of the user which are included in the first activty cluster, and gives only those user which 
    // which lies on the sleeping cluster
    public List<User> getInitialUserForSecondActivityCluster(List<FirstActivityCluster> facList, List<SleepingCluster> scList, List<User> allUserList){
        List<User> returnUserList = new ArrayList<User>();
        for(SleepingCluster sc: scList){
            for(User user: allUserList){
                if(sc.getUserID() == user.getId()){
                    returnUserList.add(user);
                    break;
                }
            }
        }
        
        ClusterCommons cc = new ClusterCommons();
        for(User user: returnUserList){
            for(FirstActivityCluster facObj: facList){
                if(facObj.getUserID() == user.getId()){
                    int[] facTimeVector = facObj.getPostTimeVector();
                    List<Posts> postList = user.getUserPost();
                    List<Posts> toSetList = new ArrayList<Posts>();
                    for(Posts post: postList){
                        Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(post.getTime()));
                        int timeCategory = cc.getTimeCategory(ts.getHours());
                        if(facTimeVector[timeCategory] == 0){
                            toSetList.add(post);
                        }
                    }
                    user.setUserPost(toSetList);
                    break;
                }
            }
        }
        return returnUserList;
    }
}
