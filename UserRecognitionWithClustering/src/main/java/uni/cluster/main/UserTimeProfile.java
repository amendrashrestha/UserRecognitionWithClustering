package uni.cluster.main;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 *
 * @author amendrashrestha
 */
public class UserTimeProfile {
    
    public static void main(String[] args) throws IOException, ParseException{
        IOReadWrite ioReadWrite = new IOReadWrite();
     
        List<User> UserList = ioReadWrite.getAllUsersAsObject();
        
        
        
        for(User user1 : UserList){
            try (
            CSVWriter writer = new CSVWriter(new FileWriter(System.getProperty("user.home") + "/Desktop/TimeFeatureVector.csv", true), ',')) {
            String[] featureVectorList;
            
            user1.setCategorizedTimeToUser(user1); //Number of messages in 6 hour interval of a day
            user1.setCategorizedDayToUser(user1); //Number of messages in 7 days of week
            user1.setCategorizedMonthToUser(user1); //Number of messages in 12 months of year
            user1.setCategorizedHourOfDayToUser(user1); //Number of messages in 24 hours of day
            user1.setCategorizedTypeOfWeekToUser(user1); //Number of messages in weekdays and weekends
            
            
            int[] hourOfDay = user1.getClassifiedHourOfDayVector();
            int[] timeOfInterval = user1.getClassifiedTimeVector();
            int[] monthOfYear = user1.getClassifiedMonthVector();
            int[] dayOfWeek = user1.getClassifiedDayVector();
            int[] typeOfWeek = user1.getClassifiedTypeOfWeekVector();
            
            int[] combined = ArrayUtils.addAll(hourOfDay, timeOfInterval);
            int[] combined1 = ArrayUtils.addAll(combined, monthOfYear);
            int[] combined2 = ArrayUtils.addAll(combined1, dayOfWeek);
            int[] combined3 = ArrayUtils.addAll(combined2, typeOfWeek);
            
            featureVectorList = Arrays.toString(combined3).split("[\\[\\]]")[1].split(", ");
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
}
