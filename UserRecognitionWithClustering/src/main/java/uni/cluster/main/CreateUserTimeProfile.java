package uni.cluster.main;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.ArrayUtils;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.UserDivision;
import uni.cluster.parser.model.User;

/**
 *
 * @author amendrashrestha
 *
 * This is the main class for creating users time profile with 51 different
 * attributes. The dataset are used from Year 2003 to 2007. The time feature
 * vectors are normalized and written in .csv file using opencsv writer. At the
 * end, only the users which are available in all 5 years are written in .csv
 * file.
 */
public class CreateUserTimeProfile {

    public void timeProfile() throws IOException, ParseException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        UserDivision divideUser = new UserDivision();

        String fileName = System.getProperty("user.home") + "/Desktop/ExperimentTest/TimeFeatureVectorwithSplitTest1000.arff";
        List<User> UserList = ioReadWrite.getAllUsersAsObject();
        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(UserList, 1);
        List<User> SplittedUsers = divideUser.divideUsers(tempUsers);
        createFile(fileName);

        for (User user1 : SplittedUsers) {
            try (
                    CSVWriter writer = new CSVWriter(new FileWriter(fileName, true), ',')) {

                String[] featureVectorList;

                user1.setCategorizedTimeToUser(user1); //Number of messages in 6 hour interval of a day
                user1.setCategorizedDayToUser(user1); //Number of messages in 7 days of week
                user1.setCategorizedMonthToUser(user1); //Number of messages in 12 months of year
                user1.setCategorizedHourOfDayToUser(user1); //Number of messages in 24 hours of day
                user1.setCategorizedTypeOfWeekToUser(user1); //Number of messages in weekdays and weekends

                int ID = user1.getId();
                int[] userID = new int[1];
                userID[0] = ID;

                int[] hourOfDay = user1.getClassifiedHourOfDayVector();
                int[] timeOfInterval = user1.getClassifiedTimeVector();
                int[] monthOfYear = user1.getClassifiedMonthVector();
                int[] dayOfWeek = user1.getClassifiedDayVector();
                int[] typeOfWeek = user1.getClassifiedTypeOfWeekVector();

                int totalSum = returnTotalSum(typeOfWeek);

                hourOfDay = returnNormalizedVector(hourOfDay, totalSum);
                timeOfInterval = returnNormalizedVector(timeOfInterval, totalSum);
                monthOfYear = returnNormalizedVector(monthOfYear, totalSum);
                dayOfWeek = returnNormalizedVector(dayOfWeek, totalSum);
                typeOfWeek = returnNormalizedVector(typeOfWeek, totalSum);

                int[] combined = ArrayUtils.addAll(userID, hourOfDay);
                int[] combined1 = ArrayUtils.addAll(combined, timeOfInterval);
                int[] combined2 = ArrayUtils.addAll(combined1, monthOfYear);
                int[] combined3 = ArrayUtils.addAll(combined2, dayOfWeek);
                int[] combined4 = ArrayUtils.addAll(combined3, typeOfWeek);

//                featureVectorList = Arrays.toString(combined4).split("[\\[\\]]")[1].split(", ");
                featureVectorList = Arrays.toString(combined4).split("[\\[\\]]")[1].split(", ");

                System.out.println("Features: " + Arrays.toString(featureVectorList));

                //            System.out.println("Hour Of Day: " + Arrays.toString(hourOfDay));
                //            System.out.println("Interal Of Day: " + Arrays.toString(timeOfInterval));
                //            System.out.println("Month Of Year: " + Arrays.toString(monthOfYear));
                //            System.out.println("Days Of Week: " + Arrays.toString(dayOfWeek));
                //            System.out.println("Type Of Week: " + Arrays.toString(typeOfWeek));
                writer.writeNext(featureVectorList);

            }
            System.out.println("*************");

            /*System.out.println("User_" + user1.getId());
             int[] dayOfMonth = user1.getClassifiedDayOfMonthVector();
             System.out.println("Day Of Month");
             for(int i : dayOfMonth){
             System.out.println(i);
             }
             System.out.println("Day Of Week");
             int[] day = user1.getClassifiedDayVector();
             for(int i : day){
             System.out.println(i);
             }
             System.out.println("Month Of Year");
             int[] month = user1.getClassifiedMonthVector();
             for(int i : month){
             System.out.println(i);
             }
             System.out.println("**************");*/
        }
    }

    public static void main(String[] args) throws IOException, ParseException {

        CreateUserTimeProfile init = new CreateUserTimeProfile();
        init.timeProfile();
        //init.readCVSFile();

    }

    /**
     * read CVS file with time feature vector of all 5 years
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readCVSFile() throws FileNotFoundException, IOException {
        String fileName = System.getProperty("user.home") + "/Desktop/TimeFeatureVector.csv";
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        String[] row;
        String UserID;
        List<String> users = new ArrayList();

        while ((row = csvReader.readNext()) != null) {
            UserID = row[0];
            users.add(UserID);
        }
        countUsers(users);
    }

    /**
     * return the normalized/percentage of posts
     *
     * @param timeVector
     * @param sum
     * @return
     */
    private int[] returnNormalizedVector(int[] timeVector, int sum) {

        for (int index = 0; index < timeVector.length; index++) {
            int temp = (int) (((timeVector[index] * 100) / sum) + 0.5);
            timeVector[index] = temp;
        }
        return timeVector;
    }

    /**
     * This file creates a list of users which are available in all 5 years and
     * send that list to createReqUsersFile method.
     *
     * @param users
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void countUsers(List<String> users) throws FileNotFoundException, IOException {
        Set<String> reqUsers = new TreeSet();
        for (String user : users) {
            int occur = Collections.frequency(users, user);
            if (occur >= 4) {
                reqUsers.add(user);
            }
        }
        for (String user : reqUsers) {
            System.out.println("Required Users: " + user);
        }
        System.out.println("Total Users: " + reqUsers.size());
        createReqUsersFile(reqUsers);
    }

    /**
     * This method creates csv file with only required users
     *
     * @param reqUsers
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void createReqUsersFile(Set<String> reqUsers) throws FileNotFoundException, IOException {
        String fileName = System.getProperty("user.home") + "/Desktop/TimeFeatureVector.csv";
        String newFileName = System.getProperty("user.home") + "/Desktop/ReqUsersTimeFeatureVector.csv";
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        CSVWriter writer = new CSVWriter(new FileWriter(newFileName, true), ',');
        String[] row;
        String UserID;

        while ((row = csvReader.readNext()) != null) {
            UserID = row[0];
            if (reqUsers.contains(UserID)) {
                String[] featureVectorList = Arrays.toString(row).split("[\\[\\]]")[1].split(", ");
                writer.writeNext(featureVectorList);
            }
        }
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

    private void createFile(String fileName) throws IOException {
        String content = "@relation usertimeprofile\n"
                + "\n"
                + "@attribute  UserID  numeric\n"
                + "@attribute  Hour1  numeric\n"
                + "@attribute   Hour2  numeric\n"
                + "@attribute   Hour3  numeric\n"
                + "@attribute   Hour4  numeric\n"
                + "@attribute   Hour5  numeric\n"
                + "@attribute   Hour6  numeric\n"
                + "@attribute   Hour7  numeric\n"
                + "@attribute   Hour8  numeric\n"
                + "@attribute   Hour9  numeric\n"
                + "@attribute   Hour10  numeric\n"
                + "@attribute   Hour11  numeric\n"
                + "@attribute   Hour12  numeric\n"
                + "@attribute   Hour13  numeric\n"
                + "@attribute   Hour14  numeric\n"
                + "@attribute   Hour15  numeric\n"
                + "@attribute   Hour16  numeric\n"
                + "@attribute   Hour17  numeric\n"
                + "@attribute  Hour18  numeric\n"
                + "@attribute   Hour19  numeric\n"
                + "@attribute   Hour20  numeric\n"
                + "@attribute   Hour21  numeric\n"
                + "@attribute   Hour22  numeric\n"
                + "@attribute   Hour23  numeric\n"
                + "@attribute   Hour24  numeric\n"
                + "@attribute  MidNight  numeric\n"
                + "@attribute   EarlyMorning  numeric\n"
                + "@attribute   Morning  numeric\n"
                + "@attribute   MidDay  numeric\n"
                + "@attribute   Evening  numeric\n"
                + "@attribute   Night  numeric\n"
                + "@attribute  Jan  numeric\n"
                + "@attribute   Feb  numeric\n"
                + "@attribute   Mar  numeric\n"
                + "@attribute   Apr  numeric\n"
                + "@attribute   May  numeric\n"
                + "@attribute   June  numeric\n"
                + "@attribute   July  numeric\n"
                + "@attribute   Aug  numeric\n"
                + "@attribute   Sep  numeric\n"
                + "@attribute   Oct  numeric\n"
                + "@attribute   Nov  numeric\n"
                + "@attribute   Dec  numeric\n"
                + "@attribute  Sun  numeric\n"
                + "@attribute   Mon  numeric\n"
                + "@attribute   Tue  numeric\n"
                + "@attribute   Wed  numeric\n"
                + "@attribute   Thu  numeric\n"
                + "@attribute   Fri  numeric\n"
                + "@attribute   Sat  numeric\n"
                + "@attribute  WeekDay  numeric\n"
                + "@attribute   WeekEnd  numeric\n"
                + "\n"
                + "@data"
                + "\n";

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        }
    }
}
