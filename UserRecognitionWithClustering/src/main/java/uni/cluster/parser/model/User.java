package uni.cluster.parser.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
     * This variable contains the total number of posts, posted in an individual day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedDayVector;
    /*
     * This variable contains the total number of posts, posted in an individual month,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedMonthVector;
    /*
     * This variable contains the total number of posts, posted in an individual month,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedDayOfMonthVector;

    /*
     * This variable contains the total number of posts posted in an each hour of a day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedHourOfDayVector;

    /*
     * This variable contains the total number of posts posted in an each hour of a day,
     * The information about the avaiable day frame can be viewed on method "timeCategoryDefinition()" 
     * defined in class "com.post.parser.clustering.FirstActivityCluster"
     */
    private int[] classifiedTypeOfWeekVector;
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
     */
    public User() {
        this.type = UserType.UNDEFINED;
        this.firstActivityVector = new int[]{0, 0, 0, 0, 0, 0};
        this.sleepingClusterVector = new int[]{0, 0, 0, 0, 0, 0};
        this.secondActivityVector = new int[]{0, 0, 0, 0, 0, 0};
        this.classifiedTimeVector = new int[]{0, 0, 0, 0, 0, 0};
        this.classifiedDayVector = new int[]{0, 0, 0, 0, 0, 0, 0};
        this.classifiedMonthVector = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.classifiedHourOfDayVector = new int[24];
        this.classifiedDayOfMonthVector = new int[31];
        this.classifiedTypeOfWeekVector = new int[2];
    }
    
    /*public User(int[] classifiedTimeVector, int[] classifiedDayVector, int[] classifiedMonthVector, 
            int[] classifiedHourOfDayVector, int[] classifiedDayOfMonthVector, int[] classifiedTypeOfWeekVector){
        
    }*/

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
     * @return the classifiedDayVector
     */
    public int[] getClassifiedDayVector() {
        return classifiedDayVector;
    }

    /**
     * @param classifiedDayVector the classifiedDayVector to set
     */
    public void setClassifiedDayVector(int[] classifiedDayVector) {
        this.classifiedDayVector = classifiedDayVector;
    }

    /**
     * @return the classifiedMonthVector
     */
    public int[] getClassifiedMonthVector() {
        return classifiedMonthVector;
    }

    /**
     * @param classifiedMonthVector the classifiedMonthVector to set
     */
    public void setClassifiedMonthVector(int[] classifiedMonthVector) {
        this.classifiedMonthVector = classifiedMonthVector;
    }

    /**
     * @return the classifiedDayOfMonthVector
     */
    public int[] getClassifiedDayOfMonthVector() {
        return classifiedDayOfMonthVector;
    }

    /**
     * @param classifiedDayOfMonthVector the classifiedDayOfMonthVector to set
     */
    public void setClassifiedDayOfMonthVector(int[] classifiedDayOfMonthVector) {
        this.classifiedDayOfMonthVector = classifiedDayOfMonthVector;
    }

    /**
     * @return the classifiedHourOfDayVector
     */
    public int[] getClassifiedHourOfDayVector() {
        return classifiedHourOfDayVector;
    }

    /**
     * @param classifiedHourOfDayVector the classifiedHourOfDayVector to set
     */
    public void setClassifiedHourOfDayVector(int[] classifiedHourOfDayVector) {
        this.classifiedHourOfDayVector = classifiedHourOfDayVector;
    }

    /**
     * @return the classifiedTypeOfWeekVector
     */
    public int[] getClassifiedTypeOfWeekVector() {
        return classifiedTypeOfWeekVector;
    }

    /**
     * @param classifiedTypeOfWeekVector the classifiedTypeOfWeekVector to set
     */
    public void setClassifiedTypeOfWeekVector(int[] classifiedTypeOfWeekVector) {
        this.classifiedTypeOfWeekVector = classifiedTypeOfWeekVector;
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
     * @param user
     * @return user
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     */
    public User setCategorizedTypeOfWeekToUser(User user) throws ParseException {
        int[] userTimeVector = user.getClassifiedTypeOfWeekVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int dayOfWeek = getDayOfWeek(date);
            int typeOfWeek = getTypeOfWeek(dayOfWeek);

            userTimeVector[typeOfWeek] = userTimeVector[typeOfWeek] + 1;
        }
        user.setClassifiedTypeOfWeekVector(userTimeVector);

        return user;
    }

    /**
     * @param user
     * @return user
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     */
    public User setCategorizedHourOfDayToUser(User user) {
        int[] userTimeVector = user.getClassifiedHourOfDayVector();
        for (Posts posts : user.getUserPost()) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
            int timeCategory = ts.getHours();
            userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
        }
        user.setClassifiedHourOfDayVector(userTimeVector);

        return user;
    }

    /**
     * @param userList
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
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
     * @param user
     * @Desc This function populates the "classifiedTimeVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified time range which has been described in getTimeCategory(int
     * hours) in this class.
     * @return List<User>
     */
    public User setCategorizedTimeToUser(User user) {
        ClusterCommons cc = new ClusterCommons();
        int[] userTimeVector = user.getClassifiedTimeVector();
        for (Posts posts : user.getUserPost()) {
            Timestamp ts = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(new Date()).concat(posts.getTime()));
            int timeCategory = cc.getTimeCategory(ts.getHours());
            if (timeCategory < 6) {
                userTimeVector[timeCategory] = userTimeVector[timeCategory] + 1;
            }
        }
        user.setClassifiedTimeVector(userTimeVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedDayToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userDayVector = user.getClassifiedDayVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int DayOfWeek = getDayOfWeek(date) - 1;
//                System.out.println("Day Of Week " + DayOfWeek);
                userDayVector[DayOfWeek] = userDayVector[DayOfWeek] + 1;
            }
            user.setClassifiedDayVector(userDayVector);
        }
        return userList;
    }

    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedDayToUser(User user) throws ParseException {
        int[] userDayVector = user.getClassifiedDayVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int DayOfWeek = getDayOfWeek(date) - 1;
//                System.out.println("Day Of Week " + DayOfWeek);
            userDayVector[DayOfWeek] = userDayVector[DayOfWeek] + 1;
        }
        user.setClassifiedDayVector(userDayVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedMonthToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userMonthVector = user.getClassifiedMonthVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int MonthOfYear = getMonthOfYear(date);
//                System.out.println("Day Of Week " + DayOfWeek);
                userMonthVector[MonthOfYear] = userMonthVector[MonthOfYear] + 1;
            }
            user.setClassifiedMonthVector(userMonthVector);
        }
        return userList;
    }

    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedMonthToUser(User user) throws ParseException {
        int[] userMonthVector = user.getClassifiedMonthVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int MonthOfYear = getMonthOfYear(date);
//                System.out.println("Day Of Week " + DayOfWeek);
            userMonthVector[MonthOfYear] = userMonthVector[MonthOfYear] + 1;
        }
        user.setClassifiedMonthVector(userMonthVector);

        return user;
    }

    /**
     * @param userList
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public List<User> setCategorizedDayOfMonthToUser(List<User> userList) throws ParseException {
        for (User user : userList) {
            int[] userDayOfMonthVector = user.getClassifiedDayOfMonthVector();
            for (Posts posts : user.getUserPost()) {
                String date = posts.getDate();
                int DayOfMonth = getDayOfMonth(date) - 1;
                System.out.println("Day Of Week " + DayOfMonth);
                userDayOfMonthVector[DayOfMonth] = userDayOfMonthVector[DayOfMonth] + 1;
            }
            user.setClassifiedDayOfMonthVector(userDayOfMonthVector);
        }
        return userList;
    }

    /**
     * @param user
     * @return
     * @throws java.text.ParseException
     * @Desc This function populates the "classifiedDayVector" variable of the
     * com.post.parser.model.User class. It gives the total number of posts in
     * the specified day range which has been described in getDayCategory(int
     * DayOfWeek) in this class.
     */
    public User setCategorizedDayOfMonthToUser(User user) throws ParseException {
        int[] userDayOfMonthVector = user.getClassifiedDayOfMonthVector();
        for (Posts posts : user.getUserPost()) {
            String date = posts.getDate();
            int DayOfMonth = getDayOfMonth(date) - 1;
            userDayOfMonthVector[DayOfMonth] = userDayOfMonthVector[DayOfMonth] + 1;
        }
        user.setClassifiedDayOfMonthVector(userDayOfMonthVector);

        return user;
    }

    /**
     * @param userList
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
            int requiredPostValue = (int) (0.20 * postList.size());
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

    private int getDayOfWeek(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    private int getMonthOfYear(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int monthOfYear = c.get(Calendar.MONTH);
        return monthOfYear;
    }

    private int getDayOfMonth(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = format1.parse(date);
        c.setTime(dt1);
        int monthOfYear = c.get(Calendar.DAY_OF_MONTH);
        return monthOfYear;
    }

    private int getTypeOfWeek(int day) throws ParseException {

        if (day >= 2 && day <= 6) {
            return 0;
        } else 
            return 1;
    }
    
}
