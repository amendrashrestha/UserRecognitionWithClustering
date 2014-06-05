package uni.EISIC14.analysis;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class AnalyzePostTimeWithTimeFeatureVector {

    public List<User> aliases; // The aliases we are interested in to compare
    List tempDisplayInfo;
    List<Integer> rank = new ArrayList<>();
    int rankArray[] = {0, 0, 0, 0};
    int top1, top3, other;
    HashSet matchedUserSet = new HashSet();
    static HashMap<Integer, HashSet<Integer>> userResult = new HashMap<>();
    Set userBin = new HashSet();

    void executePostCompare(List<User> aliasList) throws SQLException, ParseException {
        this.aliases = aliasList;
        compareAllPairsOfAliases();
        displayUserRank();
    }

    public void compareAllPairsOfAliases() throws SQLException, ParseException {
        tempDisplayInfo = new ArrayList<>();

        for (int i = 1; i < aliases.size(); i++) {
            List tempList = new ArrayList();
            User mainUser = aliases.get(0);
            User otherUsers = aliases.get(i);
            int user1 = mainUser.getId();
            int user2 = otherUsers.getId();

            /**
             * calculating time vector for alias
             */
            double timeMatch;

            mainUser.setCategorizedTimeToUser(mainUser); //Number of messages in 6 hour interval of a day
            mainUser.setCategorizedDayToUser(mainUser); //Number of messages in 7 days of week
            mainUser.setCategorizedMonthToUser(mainUser); //Number of messages in 12 months of year

            int[] mainUsertimeOfInterval = mainUser.getClassifiedTimeVector();
            int[] mainUsermonthOfYear = mainUser.getClassifiedMonthVector();
            int[] mainUserdayOfWeek = mainUser.getClassifiedDayVector();

            otherUsers.setCategorizedTimeToUser(otherUsers); //Number of messages in 6 hour interval of a day
            otherUsers.setCategorizedDayToUser(otherUsers); //Number of messages in 7 days of week
            otherUsers.setCategorizedMonthToUser(otherUsers); //Number of messages in 12 months of year

            int[] otherUsertimeOfInterval = otherUsers.getClassifiedTimeVector();
            int[] otherUsermonthOfYear = otherUsers.getClassifiedMonthVector();
            int[] otherUserdayOfWeek = otherUsers.getClassifiedDayVector();

            int mainUserTotalPost = returnTotalSum(mainUsermonthOfYear);
            int otherUserTotalPost = returnTotalSum(otherUsermonthOfYear);

            double[] normMainUsertimeOfInterval = returnNormalizedTimeVector(mainUsertimeOfInterval, mainUserTotalPost);
            double[] normMainUsermonthOfYear = returnNormalizedTimeVector(mainUsermonthOfYear, mainUserTotalPost);
            double[] normMainUserdayOfWeek = returnNormalizedTimeVector(mainUserdayOfWeek, mainUserTotalPost);

            double[] normOtherUsertimeOfInterval = returnNormalizedTimeVector(otherUsertimeOfInterval, otherUserTotalPost);
            double[] normOtherUsermonthOfYear = returnNormalizedTimeVector(otherUsermonthOfYear, otherUserTotalPost);
             double[] normOtherUserdayOfWeek = returnNormalizedTimeVector(otherUserdayOfWeek, otherUserTotalPost);

            double[] mainUsercombined1 = ArrayUtils.addAll(normMainUsertimeOfInterval, normMainUsermonthOfYear);
              double[] mainUsercombined2 = ArrayUtils.addAll(mainUsercombined1, normMainUserdayOfWeek);

            double[] normUsercombined1 = ArrayUtils.addAll(normOtherUsertimeOfInterval, normOtherUsermonthOfYear);
             double[] normUsercombined2 = ArrayUtils.addAll(normUsercombined1, normOtherUserdayOfWeek);
//             System.out.println("Second User: " + Arrays.toString(normUsercombined2));
//             System.out.println("Main User: " + Arrays.toString(mainUsercombined2));

//            timeMatch = calculateManhattanDistance(mainUsercombined1, normUsercombined1);
            timeMatch = calculateManhattanDistance(mainUsercombined2, normUsercombined2);

            tempList.add(user1);
            tempList.add(user2);
            tempList.add(timeMatch);
            tempDisplayInfo.add(tempList);
        }
        getsortedTime(tempDisplayInfo);
        tempDisplayInfo.clear();
    }

    /**
     * return total count of posts
     *
     * @param timeVector
     * @return
     */
    private int returnTotalSum(int[] timeVector) {
        int sum = 0;

        for (int index = 0; index < timeVector.length; index++) {
            sum = sum + timeVector[index];
        }
        return sum;
    }

    /**
     * return normalized time vector with respect to total number of post
     *
     * @param timeVector
     * @param sum
     * @return
     */
    public double[] returnNormalizedTimeVector(int[] timeVector, int sum) {

        int vectorSize = timeVector.length;
        double[] normalizedTimeVector = new double[vectorSize];

        for (int index = 0; index < vectorSize; index++) {
            double temp = (double) timeVector[index] / sum;
            normalizedTimeVector[index] = temp;
        }
        return normalizedTimeVector;
    }

    /**
     * Calculate Manhattan distance between two users
     *
     * @param sequence1
     * @param sequence2
     * @return
     */
    public double calculateManhattanDistance(double[] sequence1, double[] sequence2) {
        double manhattanDistance = 0.0;
        for (int i = 0; i < sequence1.length; i++) {
            double firstElementsequence1 = sequence1[i];
            System.out.println("First " + firstElementsequence1);
            double firstElementsequence2 = sequence2[i];
            System.out.println("Second " + firstElementsequence2);
            manhattanDistance = manhattanDistance + Math.abs(firstElementsequence2 - firstElementsequence1);
            System.out.println("Manha: " + manhattanDistance);
            System.out.println("---------");
        }
        return manhattanDistance;
    }

    /**
     * sort list of time
     *
     * @param Timeinfo
     * @return
     */
    public void getsortedTime(List Timeinfo) {
        List tempTimeinfo = new ArrayList();
        tempTimeinfo.addAll(Timeinfo);
        //System.out.println("Time list: " + tempTimeinfo);

        Collections.sort(tempTimeinfo, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                Double firstNumber = Double.parseDouble(o1.get(2).toString());
                Double secondNumber = Double.parseDouble(o2.get(2).toString());
                return firstNumber.compareTo(secondNumber);
            }
        });
        //System.out.println("After sorting: " + tempTimeinfo);
        createRank(tempTimeinfo);
    }

    /**
     * find similar user within the list
     *
     * @param tempdisplayInfo
     */
    public void createRank(List tempdisplayInfo) {
        int infoSize = tempdisplayInfo.size();
        int index = 0;

        for (int i = 0; i < infoSize; i++) {
            String row = tempdisplayInfo.get(i).toString();
            String[] strArray = row.split(",");
            String strfirstUser = strArray[0].substring(1, strArray[0].length()).trim();
            String strsecondUser = strArray[1].trim();
            System.out.println("Similar Users: " + strfirstUser + " with " + strsecondUser);
            int user1 = Integer.parseInt(strfirstUser);
            int user2 = Integer.parseInt(strsecondUser);

            if (user1 == user2) {
                rank.add(i + 1);
                index = i + 1;
                System.out.println("Matched At: " + (i + 1));
                addIndexUser(index, user1);
                break;
            }
        }
    }

    /**
     * add user and their rank in hash map
     *
     * @param key
     * @param value
     */
    private void addIndexUser(int key, int value) {
        HashSet tempList = null;
        if (userResult.containsKey(key)) {
            tempList = userResult.get(key);

            if (tempList == null) {
                tempList = new HashSet();
            }
            tempList.add(value);
        } else {
            tempList = new HashSet();
            tempList.add(value);
        }
        userResult.put(key, tempList);
    }

    private void displayUserRank() {
        System.out.println("\nResult: " + userResult);
        int top1Size = 0;
        for (Map.Entry<Integer, HashSet<Integer>> MapEntry : userResult.entrySet()) {
            Integer tempRank = MapEntry.getKey();
            if (tempRank.equals(1)) {
                HashSet top1Users = MapEntry.getValue();
                top1Size = top1Users.size();
                userBin.addAll(top1Users);
            } else if (tempRank.equals(2)) {
                HashSet top2Users = MapEntry.getValue();
                userBin.addAll(top2Users);
            } else if (tempRank.equals(3)) {
                HashSet top3Users = MapEntry.getValue();
                userBin.addAll(top3Users);
            }
        }
        System.out.println("Top 1: " + top1Size);
        System.out.println("Top 3: " + userBin.size());
        System.out.println("*********************");
    }
}
