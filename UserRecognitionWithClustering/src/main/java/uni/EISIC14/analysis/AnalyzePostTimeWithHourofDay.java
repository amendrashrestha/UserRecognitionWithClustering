package uni.EISIC14.analysis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class AnalyzePostTimeWithHourofDay {

    public List<User> aliases; // The aliases we are interested in to compare
    List tempDisplayInfo;
    List<Integer> rank = new ArrayList<>();
    int rankArray[] = {0, 0, 0, 0};
    int top1, top3, other;
    HashSet matchedUserSet = new HashSet();
    static HashMap<Integer, HashSet<Integer>> userResult = new HashMap<>();
    Set userBin = new HashSet();

    void executePostCompare(List<User> aliasList) throws SQLException {
        this.aliases = aliasList;
        compareAllPairsOfAliases();
        displayUserRank();
    }

    public void compareAllPairsOfAliases() throws SQLException {
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
            double timeMatch = 0.0;
            mainUser.setCategorizedHourOfDayToUser(mainUser);
            otherUsers.setCategorizedHourOfDayToUser(otherUsers);

            int[] user1timeVector = mainUser.getClassifiedHourOfDayVector();
            int[] user2timeVector = otherUsers.getClassifiedHourOfDayVector();

            double[] normUser1timeVector = returnNormalizedTimeVector(user1timeVector);
            double[] normUser2timeVector = returnNormalizedTimeVector(user2timeVector);

            timeMatch = calculateManhattanTimeVector(normUser1timeVector, normUser2timeVector);
            float time = (float) timeMatch;

            tempList.add(user1);
            tempList.add(user2);
            tempList.add(time);
            tempDisplayInfo.add(tempList);
        }
        getsortedTime(tempDisplayInfo);
        tempDisplayInfo.clear();
    }

    /**
     * return normalized time vector with respect to total number of post
     *
     * @param timeVector
     * @return
     */
    public double[] returnNormalizedTimeVector(int[] timeVector) {
        double sumB = 0.0;
        double[] normalizedTimeVector = new double[24];
        for (int index = 0; index < timeVector.length; index++) {
            sumB = sumB + timeVector[index];
        }

        for (int index = 0; index < timeVector.length; index++) {
            double temp = timeVector[index] / sumB;
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
    public static double calculateManhattanTimeVector(double[] sequence1, double[] sequence2) {
        double manhattanDistance = 0.0;
        for (int i = 0; i < sequence1.length; i++) {
            double firstElementsequence1 = sequence1[i];
            double firstElementsequence2 = sequence2[i];
            manhattanDistance = manhattanDistance + Math.abs(firstElementsequence2 - firstElementsequence1);
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
